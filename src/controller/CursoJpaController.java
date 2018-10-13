/**
 *
 * @author hernanbiondini
 */
package controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Carrera;
import Modelo.Curso;
import Modelo.InscripcionesCurso;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernanbiondini
 */
public class CursoJpaController implements Serializable {

    public CursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) throws PreexistingEntityException, Exception {
        if (curso.getInscripcionesCursoCollection() == null) {
            curso.setInscripcionesCursoCollection(new ArrayList<InscripcionesCurso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera idcarrera = curso.getIdcarrera();
            if (idcarrera != null) {
                idcarrera = em.getReference(idcarrera.getClass(), idcarrera.getIdentificador());
                curso.setIdcarrera(idcarrera);
            }
            Collection<InscripcionesCurso> attachedInscripcionesCursoCollection = new ArrayList<InscripcionesCurso>();
            for (InscripcionesCurso inscripcionesCursoCollectionInscripcionesCursoToAttach : curso.getInscripcionesCursoCollection()) {
                inscripcionesCursoCollectionInscripcionesCursoToAttach = em.getReference(inscripcionesCursoCollectionInscripcionesCursoToAttach.getClass(), inscripcionesCursoCollectionInscripcionesCursoToAttach.getInscripcionesCursoPK());
                attachedInscripcionesCursoCollection.add(inscripcionesCursoCollectionInscripcionesCursoToAttach);
            }
            curso.setInscripcionesCursoCollection(attachedInscripcionesCursoCollection);
            em.persist(curso);
            if (idcarrera != null) {
                idcarrera.getCursoCollection().add(curso);
                idcarrera = em.merge(idcarrera);
            }
            for (InscripcionesCurso inscripcionesCursoCollectionInscripcionesCurso : curso.getInscripcionesCursoCollection()) {
                Curso oldCursoOfInscripcionesCursoCollectionInscripcionesCurso = inscripcionesCursoCollectionInscripcionesCurso.getCurso();
                inscripcionesCursoCollectionInscripcionesCurso.setCurso(curso);
                inscripcionesCursoCollectionInscripcionesCurso = em.merge(inscripcionesCursoCollectionInscripcionesCurso);
                if (oldCursoOfInscripcionesCursoCollectionInscripcionesCurso != null) {
                    oldCursoOfInscripcionesCursoCollectionInscripcionesCurso.getInscripcionesCursoCollection().remove(inscripcionesCursoCollectionInscripcionesCurso);
                    oldCursoOfInscripcionesCursoCollectionInscripcionesCurso = em.merge(oldCursoOfInscripcionesCursoCollectionInscripcionesCurso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCurso(curso.getIdentificador()) != null) {
                throw new PreexistingEntityException("Curso " + curso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getIdentificador());
            Carrera idcarreraOld = persistentCurso.getIdcarrera();
            Carrera idcarreraNew = curso.getIdcarrera();
            Collection<InscripcionesCurso> inscripcionesCursoCollectionOld = persistentCurso.getInscripcionesCursoCollection();
            Collection<InscripcionesCurso> inscripcionesCursoCollectionNew = curso.getInscripcionesCursoCollection();
            List<String> illegalOrphanMessages = null;
            for (InscripcionesCurso inscripcionesCursoCollectionOldInscripcionesCurso : inscripcionesCursoCollectionOld) {
                if (!inscripcionesCursoCollectionNew.contains(inscripcionesCursoCollectionOldInscripcionesCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripcionesCurso " + inscripcionesCursoCollectionOldInscripcionesCurso + " since its curso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idcarreraNew != null) {
                idcarreraNew = em.getReference(idcarreraNew.getClass(), idcarreraNew.getIdentificador());
                curso.setIdcarrera(idcarreraNew);
            }
            Collection<InscripcionesCurso> attachedInscripcionesCursoCollectionNew = new ArrayList<InscripcionesCurso>();
            for (InscripcionesCurso inscripcionesCursoCollectionNewInscripcionesCursoToAttach : inscripcionesCursoCollectionNew) {
                inscripcionesCursoCollectionNewInscripcionesCursoToAttach = em.getReference(inscripcionesCursoCollectionNewInscripcionesCursoToAttach.getClass(), inscripcionesCursoCollectionNewInscripcionesCursoToAttach.getInscripcionesCursoPK());
                attachedInscripcionesCursoCollectionNew.add(inscripcionesCursoCollectionNewInscripcionesCursoToAttach);
            }
            inscripcionesCursoCollectionNew = attachedInscripcionesCursoCollectionNew;
            curso.setInscripcionesCursoCollection(inscripcionesCursoCollectionNew);
            curso = em.merge(curso);
            if (idcarreraOld != null && !idcarreraOld.equals(idcarreraNew)) {
                idcarreraOld.getCursoCollection().remove(curso);
                idcarreraOld = em.merge(idcarreraOld);
            }
            if (idcarreraNew != null && !idcarreraNew.equals(idcarreraOld)) {
                idcarreraNew.getCursoCollection().add(curso);
                idcarreraNew = em.merge(idcarreraNew);
            }
            for (InscripcionesCurso inscripcionesCursoCollectionNewInscripcionesCurso : inscripcionesCursoCollectionNew) {
                if (!inscripcionesCursoCollectionOld.contains(inscripcionesCursoCollectionNewInscripcionesCurso)) {
                    Curso oldCursoOfInscripcionesCursoCollectionNewInscripcionesCurso = inscripcionesCursoCollectionNewInscripcionesCurso.getCurso();
                    inscripcionesCursoCollectionNewInscripcionesCurso.setCurso(curso);
                    inscripcionesCursoCollectionNewInscripcionesCurso = em.merge(inscripcionesCursoCollectionNewInscripcionesCurso);
                    if (oldCursoOfInscripcionesCursoCollectionNewInscripcionesCurso != null && !oldCursoOfInscripcionesCursoCollectionNewInscripcionesCurso.equals(curso)) {
                        oldCursoOfInscripcionesCursoCollectionNewInscripcionesCurso.getInscripcionesCursoCollection().remove(inscripcionesCursoCollectionNewInscripcionesCurso);
                        oldCursoOfInscripcionesCursoCollectionNewInscripcionesCurso = em.merge(oldCursoOfInscripcionesCursoCollectionNewInscripcionesCurso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = curso.getIdentificador();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getIdentificador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InscripcionesCurso> inscripcionesCursoCollectionOrphanCheck = curso.getInscripcionesCursoCollection();
            for (InscripcionesCurso inscripcionesCursoCollectionOrphanCheckInscripcionesCurso : inscripcionesCursoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the InscripcionesCurso " + inscripcionesCursoCollectionOrphanCheckInscripcionesCurso + " in its inscripcionesCursoCollection field has a non-nullable curso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Carrera idcarrera = curso.getIdcarrera();
            if (idcarrera != null) {
                idcarrera.getCursoCollection().remove(curso);
                idcarrera = em.merge(idcarrera);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
