/**
 *
 * @author hernanbiondini
 */
package controller;

import Modelo.Carrera;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Curso;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.InscripcionesCarrera;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernanbiondini
 */
public class CarreraJpaController implements Serializable {

    public CarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrera carrera) throws PreexistingEntityException, Exception {
        if (carrera.getCursoCollection() == null) {
            carrera.setCursoCollection(new ArrayList<Curso>());
        }
        if (carrera.getInscripcionesCarreraCollection() == null) {
            carrera.setInscripcionesCarreraCollection(new ArrayList<InscripcionesCarrera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Curso> attachedCursoCollection = new ArrayList<Curso>();
            for (Curso cursoCollectionCursoToAttach : carrera.getCursoCollection()) {
                cursoCollectionCursoToAttach = em.getReference(cursoCollectionCursoToAttach.getClass(), cursoCollectionCursoToAttach.getIdentificador());
                attachedCursoCollection.add(cursoCollectionCursoToAttach);
            }
            carrera.setCursoCollection(attachedCursoCollection);
            Collection<InscripcionesCarrera> attachedInscripcionesCarreraCollection = new ArrayList<InscripcionesCarrera>();
            for (InscripcionesCarrera inscripcionesCarreraCollectionInscripcionesCarreraToAttach : carrera.getInscripcionesCarreraCollection()) {
                inscripcionesCarreraCollectionInscripcionesCarreraToAttach = em.getReference(inscripcionesCarreraCollectionInscripcionesCarreraToAttach.getClass(), inscripcionesCarreraCollectionInscripcionesCarreraToAttach.getInscripcionesCarreraPK());
                attachedInscripcionesCarreraCollection.add(inscripcionesCarreraCollectionInscripcionesCarreraToAttach);
            }
            carrera.setInscripcionesCarreraCollection(attachedInscripcionesCarreraCollection);
            em.persist(carrera);
            for (Curso cursoCollectionCurso : carrera.getCursoCollection()) {
                Carrera oldIdcarreraOfCursoCollectionCurso = cursoCollectionCurso.getIdcarrera();
                cursoCollectionCurso.setIdcarrera(carrera);
                cursoCollectionCurso = em.merge(cursoCollectionCurso);
                if (oldIdcarreraOfCursoCollectionCurso != null) {
                    oldIdcarreraOfCursoCollectionCurso.getCursoCollection().remove(cursoCollectionCurso);
                    oldIdcarreraOfCursoCollectionCurso = em.merge(oldIdcarreraOfCursoCollectionCurso);
                }
            }
            for (InscripcionesCarrera inscripcionesCarreraCollectionInscripcionesCarrera : carrera.getInscripcionesCarreraCollection()) {
                Carrera oldCarreraOfInscripcionesCarreraCollectionInscripcionesCarrera = inscripcionesCarreraCollectionInscripcionesCarrera.getCarrera();
                inscripcionesCarreraCollectionInscripcionesCarrera.setCarrera(carrera);
                inscripcionesCarreraCollectionInscripcionesCarrera = em.merge(inscripcionesCarreraCollectionInscripcionesCarrera);
                if (oldCarreraOfInscripcionesCarreraCollectionInscripcionesCarrera != null) {
                    oldCarreraOfInscripcionesCarreraCollectionInscripcionesCarrera.getInscripcionesCarreraCollection().remove(inscripcionesCarreraCollectionInscripcionesCarrera);
                    oldCarreraOfInscripcionesCarreraCollectionInscripcionesCarrera = em.merge(oldCarreraOfInscripcionesCarreraCollectionInscripcionesCarrera);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCarrera(carrera.getIdentificador()) != null) {
                throw new PreexistingEntityException("Carrera " + carrera + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrera carrera) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera persistentCarrera = em.find(Carrera.class, carrera.getIdentificador());
            Collection<Curso> cursoCollectionOld = persistentCarrera.getCursoCollection();
            Collection<Curso> cursoCollectionNew = carrera.getCursoCollection();
            Collection<InscripcionesCarrera> inscripcionesCarreraCollectionOld = persistentCarrera.getInscripcionesCarreraCollection();
            Collection<InscripcionesCarrera> inscripcionesCarreraCollectionNew = carrera.getInscripcionesCarreraCollection();
            List<String> illegalOrphanMessages = null;
            for (InscripcionesCarrera inscripcionesCarreraCollectionOldInscripcionesCarrera : inscripcionesCarreraCollectionOld) {
                if (!inscripcionesCarreraCollectionNew.contains(inscripcionesCarreraCollectionOldInscripcionesCarrera)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripcionesCarrera " + inscripcionesCarreraCollectionOldInscripcionesCarrera + " since its carrera field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Curso> attachedCursoCollectionNew = new ArrayList<Curso>();
            for (Curso cursoCollectionNewCursoToAttach : cursoCollectionNew) {
                cursoCollectionNewCursoToAttach = em.getReference(cursoCollectionNewCursoToAttach.getClass(), cursoCollectionNewCursoToAttach.getIdentificador());
                attachedCursoCollectionNew.add(cursoCollectionNewCursoToAttach);
            }
            cursoCollectionNew = attachedCursoCollectionNew;
            carrera.setCursoCollection(cursoCollectionNew);
            Collection<InscripcionesCarrera> attachedInscripcionesCarreraCollectionNew = new ArrayList<InscripcionesCarrera>();
            for (InscripcionesCarrera inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach : inscripcionesCarreraCollectionNew) {
                inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach = em.getReference(inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach.getClass(), inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach.getInscripcionesCarreraPK());
                attachedInscripcionesCarreraCollectionNew.add(inscripcionesCarreraCollectionNewInscripcionesCarreraToAttach);
            }
            inscripcionesCarreraCollectionNew = attachedInscripcionesCarreraCollectionNew;
            carrera.setInscripcionesCarreraCollection(inscripcionesCarreraCollectionNew);
            carrera = em.merge(carrera);
            for (Curso cursoCollectionOldCurso : cursoCollectionOld) {
                if (!cursoCollectionNew.contains(cursoCollectionOldCurso)) {
                    cursoCollectionOldCurso.setIdcarrera(null);
                    cursoCollectionOldCurso = em.merge(cursoCollectionOldCurso);
                }
            }
            for (Curso cursoCollectionNewCurso : cursoCollectionNew) {
                if (!cursoCollectionOld.contains(cursoCollectionNewCurso)) {
                    Carrera oldIdcarreraOfCursoCollectionNewCurso = cursoCollectionNewCurso.getIdcarrera();
                    cursoCollectionNewCurso.setIdcarrera(carrera);
                    cursoCollectionNewCurso = em.merge(cursoCollectionNewCurso);
                    if (oldIdcarreraOfCursoCollectionNewCurso != null && !oldIdcarreraOfCursoCollectionNewCurso.equals(carrera)) {
                        oldIdcarreraOfCursoCollectionNewCurso.getCursoCollection().remove(cursoCollectionNewCurso);
                        oldIdcarreraOfCursoCollectionNewCurso = em.merge(oldIdcarreraOfCursoCollectionNewCurso);
                    }
                }
            }
            for (InscripcionesCarrera inscripcionesCarreraCollectionNewInscripcionesCarrera : inscripcionesCarreraCollectionNew) {
                if (!inscripcionesCarreraCollectionOld.contains(inscripcionesCarreraCollectionNewInscripcionesCarrera)) {
                    Carrera oldCarreraOfInscripcionesCarreraCollectionNewInscripcionesCarrera = inscripcionesCarreraCollectionNewInscripcionesCarrera.getCarrera();
                    inscripcionesCarreraCollectionNewInscripcionesCarrera.setCarrera(carrera);
                    inscripcionesCarreraCollectionNewInscripcionesCarrera = em.merge(inscripcionesCarreraCollectionNewInscripcionesCarrera);
                    if (oldCarreraOfInscripcionesCarreraCollectionNewInscripcionesCarrera != null && !oldCarreraOfInscripcionesCarreraCollectionNewInscripcionesCarrera.equals(carrera)) {
                        oldCarreraOfInscripcionesCarreraCollectionNewInscripcionesCarrera.getInscripcionesCarreraCollection().remove(inscripcionesCarreraCollectionNewInscripcionesCarrera);
                        oldCarreraOfInscripcionesCarreraCollectionNewInscripcionesCarrera = em.merge(oldCarreraOfInscripcionesCarreraCollectionNewInscripcionesCarrera);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carrera.getIdentificador();
                if (findCarrera(id) == null) {
                    throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.");
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
            Carrera carrera;
            try {
                carrera = em.getReference(Carrera.class, id);
                carrera.getIdentificador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InscripcionesCarrera> inscripcionesCarreraCollectionOrphanCheck = carrera.getInscripcionesCarreraCollection();
            for (InscripcionesCarrera inscripcionesCarreraCollectionOrphanCheckInscripcionesCarrera : inscripcionesCarreraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrera (" + carrera + ") cannot be destroyed since the InscripcionesCarrera " + inscripcionesCarreraCollectionOrphanCheckInscripcionesCarrera + " in its inscripcionesCarreraCollection field has a non-nullable carrera field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Curso> cursoCollection = carrera.getCursoCollection();
            for (Curso cursoCollectionCurso : cursoCollection) {
                cursoCollectionCurso.setIdcarrera(null);
                cursoCollectionCurso = em.merge(cursoCollectionCurso);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrera> findCarreraEntities() {
        return findCarreraEntities(true, -1, -1);
    }

    public List<Carrera> findCarreraEntities(int maxResults, int firstResult) {
        return findCarreraEntities(false, maxResults, firstResult);
    }

    private List<Carrera> findCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrera.class));
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

    public Carrera findCarrera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrera> rt = cq.from(Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
