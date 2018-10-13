/**
 *
 * @author hernanbiondini
 */
package controller;

import Modelo.Alumno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Persona;
import Modelo.InscripcionesCurso;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.InscripcionesCarrera;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hernanbiondini
 */
public class AlumnoJpaController extends EntityController implements Serializable {

    private static AlumnoJpaController alumnoJpaController;
    
    private AlumnoJpaController(){}
    
    public static AlumnoJpaController getInstance(){
        if(alumnoJpaController ==null){
            alumnoJpaController = new AlumnoJpaController();
        }
        return alumnoJpaController;
    }
    
     protected List executeNamedQuery(String queryName) {
        EntityManager em = this.getEntityManager();
        List resultList = this.executeNamedQuery(queryName, em);
        em.close();
        return resultList;
    }
     
     protected List executeNamedQuery(String queryName, EntityManager em) {
        javax.persistence.Query query = em.createNamedQuery(queryName);
        List resultList = query.getResultList();
        return resultList;
    }
    
    public void create(Alumno alumno) throws PreexistingEntityException, Exception {
        if (alumno.getInscripcionesCursoCollection() == null) {
            alumno.setInscripcionesCursoCollection(new ArrayList<InscripcionesCurso>());
        }
        if (alumno.getInscripcionesCarreraCollection() == null) {
            alumno.setInscripcionesCarreraCollection(new ArrayList<InscripcionesCarrera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona idpersona = alumno.getIdpersona();
            if (idpersona != null) {
                idpersona = em.getReference(idpersona.getClass(), idpersona.getIdentificador());
                alumno.setIdpersona(idpersona);
            }
            Collection<InscripcionesCurso> attachedInscripcionesCursoCollection = new ArrayList<InscripcionesCurso>();
            for (InscripcionesCurso inscripcionesCursoCollectionInscripcionesCursoToAttach : alumno.getInscripcionesCursoCollection()) {
                inscripcionesCursoCollectionInscripcionesCursoToAttach = em.getReference(inscripcionesCursoCollectionInscripcionesCursoToAttach.getClass(), inscripcionesCursoCollectionInscripcionesCursoToAttach.getInscripcionesCursoPK());
                attachedInscripcionesCursoCollection.add(inscripcionesCursoCollectionInscripcionesCursoToAttach);
            }
            alumno.setInscripcionesCursoCollection(attachedInscripcionesCursoCollection);
            Collection<InscripcionesCarrera> attachedInscripcionesCarreraCollection = new ArrayList<InscripcionesCarrera>();
            for (InscripcionesCarrera inscripcionesCarreraCollectionInscripcionesCarreraToAttach : alumno.getInscripcionesCarreraCollection()) {
                inscripcionesCarreraCollectionInscripcionesCarreraToAttach = em.getReference(inscripcionesCarreraCollectionInscripcionesCarreraToAttach.getClass(), inscripcionesCarreraCollectionInscripcionesCarreraToAttach.getInscripcionesCarreraPK());
                attachedInscripcionesCarreraCollection.add(inscripcionesCarreraCollectionInscripcionesCarreraToAttach);
            }
            alumno.setInscripcionesCarreraCollection(attachedInscripcionesCarreraCollection);
            em.persist(alumno);
            if (idpersona != null) {
                Alumno oldAlumnoOfIdpersona = idpersona.getAlumno();
                if (oldAlumnoOfIdpersona != null) {
                    oldAlumnoOfIdpersona.setIdpersona(null);
                    oldAlumnoOfIdpersona = em.merge(oldAlumnoOfIdpersona);
                }
                idpersona.setAlumno(alumno);
                idpersona = em.merge(idpersona);
            }
            for (InscripcionesCurso inscripcionesCursoCollectionInscripcionesCurso : alumno.getInscripcionesCursoCollection()) {
                Alumno oldAlumnoOfInscripcionesCursoCollectionInscripcionesCurso = inscripcionesCursoCollectionInscripcionesCurso.getAlumno();
                inscripcionesCursoCollectionInscripcionesCurso.setAlumno(alumno);
                inscripcionesCursoCollectionInscripcionesCurso = em.merge(inscripcionesCursoCollectionInscripcionesCurso);
                if (oldAlumnoOfInscripcionesCursoCollectionInscripcionesCurso != null) {
                    oldAlumnoOfInscripcionesCursoCollectionInscripcionesCurso.getInscripcionesCursoCollection().remove(inscripcionesCursoCollectionInscripcionesCurso);
                    oldAlumnoOfInscripcionesCursoCollectionInscripcionesCurso = em.merge(oldAlumnoOfInscripcionesCursoCollectionInscripcionesCurso);
                }
            }
            for (InscripcionesCarrera inscripcionesCarreraCollectionInscripcionesCarrera : alumno.getInscripcionesCarreraCollection()) {
                Alumno oldAlumnoOfInscripcionesCarreraCollectionInscripcionesCarrera = inscripcionesCarreraCollectionInscripcionesCarrera.getAlumno();
                inscripcionesCarreraCollectionInscripcionesCarrera.setAlumno(alumno);
                inscripcionesCarreraCollectionInscripcionesCarrera = em.merge(inscripcionesCarreraCollectionInscripcionesCarrera);
                if (oldAlumnoOfInscripcionesCarreraCollectionInscripcionesCarrera != null) {
                    oldAlumnoOfInscripcionesCarreraCollectionInscripcionesCarrera.getInscripcionesCarreraCollection().remove(inscripcionesCarreraCollectionInscripcionesCarrera);
                    oldAlumnoOfInscripcionesCarreraCollectionInscripcionesCarrera = em.merge(oldAlumnoOfInscripcionesCarreraCollectionInscripcionesCarrera);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlumno(alumno.getIdentificador()) != null) {
                throw new PreexistingEntityException("Alumno " + alumno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    public void edit(Alumno alumno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getIdentificador());
            Persona idpersonaOld = persistentAlumno.getIdpersona();
            Persona idpersonaNew = alumno.getIdpersona();
            System.out.println(" idpersonaNew.getApellido() " + idpersonaNew.getApellido());
            Collection<InscripcionesCurso> inscripcionesCursoCollectionOld = persistentAlumno.getInscripcionesCursoCollection();
            Collection<InscripcionesCurso> inscripcionesCursoCollectionNew = alumno.getInscripcionesCursoCollection();
            Collection<InscripcionesCarrera> inscripcionesCarreraCollectionOld = persistentAlumno.getInscripcionesCarreraCollection();
            Collection<InscripcionesCarrera> inscripcionesCarreraCollectionNew = alumno.getInscripcionesCarreraCollection();
            List<String> illegalOrphanMessages = null;
            for (InscripcionesCurso inscripcionesCursoCollectionOldInscripcionesCurso : inscripcionesCursoCollectionOld) {
                if (!inscripcionesCursoCollectionNew.contains(inscripcionesCursoCollectionOldInscripcionesCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripcionesCurso " + inscripcionesCursoCollectionOldInscripcionesCurso + " since its alumno field is not nullable.");
                }
            }
            for (InscripcionesCarrera inscripcionesCarreraCollectionOldInscripcionesCarrera : inscripcionesCarreraCollectionOld) {
                if (!inscripcionesCarreraCollectionNew.contains(inscripcionesCarreraCollectionOldInscripcionesCarrera)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripcionesCarrera " + inscripcionesCarreraCollectionOldInscripcionesCarrera + " since its alumno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idpersonaNew != null) {
                idpersonaNew = em.getReference(idpersonaNew.getClass(), idpersonaNew.getIdentificador());
                alumno.setIdpersona(idpersonaNew);
            }
            Collection<InscripcionesCurso> attachedInscripcionesCursoCollectionNew = new ArrayList<InscripcionesCurso>();
            for (InscripcionesCurso inscripcionesCursoCollectionNewInscripcionesCursoToAttach : inscripcionesCursoCollectionNew) {
                inscripcionesCursoCollectionNewInscripcionesCursoToAttach = em.getReference(inscripcionesCursoCollectionNewInscripcionesCursoToAttach.getClass(), inscripcionesCursoCollectionNewInscripcionesCursoToAttach.getInscripcionesCursoPK());
                attachedInscripcionesCursoCollectionNew.add(inscripcionesCursoCollectionNewInscripcionesCursoToAttach);
            }
            inscripcionesCursoCollectionNew = attachedInscripcionesCursoCollectionNew;
            alumno.setInscripcionesCursoCollection(inscripcionesCursoCollectionNew);
            Collection<InscripcionesCarrera> attachedInscripcionesCarreraCollectionNew = new ArrayList<InscripcionesCarrera>();
            for (InscripcionesCarrera inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach : inscripcionesCarreraCollectionNew) {
                inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach = em.getReference(inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach.getClass(), inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach.getInscripcionesCarreraPK());
                attachedInscripcionesCarreraCollectionNew.add(inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach);
            }
            inscripcionesCarreraCollectionNew = attachedInscripcionesCarreraCollectionNew;
            alumno.setInscripcionesCarreraCollection(inscripcionesCarreraCollectionNew);
            alumno = em.merge(alumno);
            if (idpersonaOld != null && !idpersonaOld.equals(idpersonaNew)) {
                idpersonaOld.setAlumno(null);
                idpersonaOld = em.merge(idpersonaOld);
            }
            if (idpersonaNew != null && !idpersonaNew.equals(idpersonaOld)) {
                Alumno oldAlumnoOfIdpersona = idpersonaNew.getAlumno();
                if (oldAlumnoOfIdpersona != null) {
                    oldAlumnoOfIdpersona.setIdpersona(null);
                    oldAlumnoOfIdpersona = em.merge(oldAlumnoOfIdpersona);
                }
                idpersonaNew.setAlumno(alumno);
                idpersonaNew = em.merge(idpersonaNew);
            }
            for (InscripcionesCurso inscripcionesCursoCollectionNewInscripcionesCurso : inscripcionesCursoCollectionNew) {
                if (!inscripcionesCursoCollectionOld.contains(inscripcionesCursoCollectionNewInscripcionesCurso)) {
                    Alumno oldAlumnoOfInscripcionesCursoCollectionNewInscripcionesCurso = inscripcionesCursoCollectionNewInscripcionesCurso.getAlumno();
                    inscripcionesCursoCollectionNewInscripcionesCurso.setAlumno(alumno);
                    inscripcionesCursoCollectionNewInscripcionesCurso = em.merge(inscripcionesCursoCollectionNewInscripcionesCurso);
                    if (oldAlumnoOfInscripcionesCursoCollectionNewInscripcionesCurso != null && !oldAlumnoOfInscripcionesCursoCollectionNewInscripcionesCurso.equals(alumno)) {
                        oldAlumnoOfInscripcionesCursoCollectionNewInscripcionesCurso.getInscripcionesCursoCollection().remove(inscripcionesCursoCollectionNewInscripcionesCurso);
                        oldAlumnoOfInscripcionesCursoCollectionNewInscripcionesCurso = em.merge(oldAlumnoOfInscripcionesCursoCollectionNewInscripcionesCurso);
                    }
                }
            }
            for (InscripcionesCarrera inscripcionesCarreraCollectionNewInscripcionesCarrera : inscripcionesCarreraCollectionNew) {
                if (!inscripcionesCarreraCollectionOld.contains(inscripcionesCarreraCollectionNewInscripcionesCarrera)) {
                    Alumno oldAlumnoOfInscripcionesCarreraCollectionNewInscripcionesCarrera = inscripcionesCarreraCollectionNewInscripcionesCarrera.getAlumno();
                    inscripcionesCarreraCollectionNewInscripcionesCarrera.setAlumno(alumno);
                    inscripcionesCarreraCollectionNewInscripcionesCarrera = em.merge(inscripcionesCarreraCollectionNewInscripcionesCarrera);
                    if (oldAlumnoOfInscripcionesCarreraCollectionNewInscripcionesCarrera != null && !oldAlumnoOfInscripcionesCarreraCollectionNewInscripcionesCarrera.equals(alumno)) {
                        oldAlumnoOfInscripcionesCarreraCollectionNewInscripcionesCarrera.getInscripcionesCarreraCollection().remove(inscripcionesCarreraCollectionNewInscripcionesCarrera);
                        oldAlumnoOfInscripcionesCarreraCollectionNewInscripcionesCarrera = em.merge(oldAlumnoOfInscripcionesCarreraCollectionNewInscripcionesCarrera);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            System.out.println("Exception: " + ex.getMessage());
            if (msg == null || msg.length() == 0) {
                Integer id = alumno.getIdentificador();
                if (findAlumno(id) == null) {
                    throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno alumno;
            try {
                alumno = em.getReference(Alumno.class, id);
                alumno.getIdentificador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InscripcionesCurso> inscripcionesCursoCollectionOrphanCheck = alumno.getInscripcionesCursoCollection();
            for (InscripcionesCurso inscripcionesCursoCollectionOrphanCheckInscripcionesCurso : inscripcionesCursoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the InscripcionesCurso " + inscripcionesCursoCollectionOrphanCheckInscripcionesCurso + " in its inscripcionesCursoCollection field has a non-nullable alumno field.");
            }
            Collection<InscripcionesCarrera> inscripcionesCarreraCollectionOrphanCheck = alumno.getInscripcionesCarreraCollection();
            for (InscripcionesCarrera inscripcionesCarreraCollectionOrphanCheckInscripcionesCarrera : inscripcionesCarreraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the InscripcionesCarrera " + inscripcionesCarreraCollectionOrphanCheckInscripcionesCarrera + " in its inscripcionesCarreraCollection field has a non-nullable alumno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona idpersona = alumno.getIdpersona();
            if (idpersona != null) {
                idpersona.setAlumno(null);
                idpersona = em.merge(idpersona);
            }
            em.remove(alumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumno> findAlumnoEntities() {
        return findAlumnoEntities(true, -1, -1);
    }

    public List<Alumno> findAlumnoEntities(int maxResults, int firstResult) {
        return findAlumnoEntities(false, maxResults, firstResult);
    }

    private List<Alumno> findAlumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumno.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Alumno findAlumno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumno> rt = cq.from(Alumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public void updateAlumno(Alumno entity) throws Exception {
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            Alumno entityx = em.find(Alumno.class, entity.getIdentificador());
            entityx.setIdpersona(entity.getIdpersona());
            entityx.setLegajo(entity.getLegajo());
            entityx.setInscripcionesCarreraCollection(entity.getInscripcionesCarreraCollection());
            entityx.setInscripcionesCursoCollection(entity.getInscripcionesCursoCollection());
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
     
     public void removeAlumno(Alumno entity) throws Exception {
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            Alumno entityx = em.find(Alumno.class, entity.getIdentificador());
            em.remove(entityx);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
     
     public void guardarAlumno(Alumno a) throws Exception{
        EntityManager em = this.getEntityManager();
        String resultado = "OK";
        em.getTransaction().begin();
        this.create(a);
        em.getTransaction().commit();
        em.close();
    }
}
