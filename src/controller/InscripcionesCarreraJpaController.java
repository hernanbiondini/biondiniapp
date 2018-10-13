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
import Modelo.Alumno;
import Modelo.Carrera;
import Modelo.InscripcionesCarrera;
import Modelo.InscripcionesCarreraPK;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernanbiondini
 */
public class InscripcionesCarreraJpaController implements Serializable {

    public InscripcionesCarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InscripcionesCarrera inscripcionesCarrera) throws PreexistingEntityException, Exception {
        if (inscripcionesCarrera.getInscripcionesCarreraPK() == null) {
            inscripcionesCarrera.setInscripcionesCarreraPK(new InscripcionesCarreraPK());
        }
        inscripcionesCarrera.getInscripcionesCarreraPK().setIdcarrera(inscripcionesCarrera.getCarrera().getIdentificador());
        inscripcionesCarrera.getInscripcionesCarreraPK().setIdalumno(inscripcionesCarrera.getAlumno().getIdentificador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno alumno = inscripcionesCarrera.getAlumno();
            if (alumno != null) {
                alumno = em.getReference(alumno.getClass(), alumno.getIdentificador());
                inscripcionesCarrera.setAlumno(alumno);
            }
            Carrera carrera = inscripcionesCarrera.getCarrera();
            if (carrera != null) {
                carrera = em.getReference(carrera.getClass(), carrera.getIdentificador());
                inscripcionesCarrera.setCarrera(carrera);
            }
            em.persist(inscripcionesCarrera);
            if (alumno != null) {
                alumno.getInscripcionesCarreraCollection().add(inscripcionesCarrera);
                alumno = em.merge(alumno);
            }
            if (carrera != null) {
                carrera.getInscripcionesCarreraCollection().add(inscripcionesCarrera);
                carrera = em.merge(carrera);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInscripcionesCarrera(inscripcionesCarrera.getInscripcionesCarreraPK()) != null) {
                throw new PreexistingEntityException("InscripcionesCarrera " + inscripcionesCarrera + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InscripcionesCarrera inscripcionesCarrera) throws NonexistentEntityException, Exception {
        inscripcionesCarrera.getInscripcionesCarreraPK().setIdcarrera(inscripcionesCarrera.getCarrera().getIdentificador());
        inscripcionesCarrera.getInscripcionesCarreraPK().setIdalumno(inscripcionesCarrera.getAlumno().getIdentificador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripcionesCarrera persistentInscripcionesCarrera = em.find(InscripcionesCarrera.class, inscripcionesCarrera.getInscripcionesCarreraPK());
            Alumno alumnoOld = persistentInscripcionesCarrera.getAlumno();
            Alumno alumnoNew = inscripcionesCarrera.getAlumno();
            Carrera carreraOld = persistentInscripcionesCarrera.getCarrera();
            Carrera carreraNew = inscripcionesCarrera.getCarrera();
            if (alumnoNew != null) {
                alumnoNew = em.getReference(alumnoNew.getClass(), alumnoNew.getIdentificador());
                inscripcionesCarrera.setAlumno(alumnoNew);
            }
            if (carreraNew != null) {
                carreraNew = em.getReference(carreraNew.getClass(), carreraNew.getIdentificador());
                inscripcionesCarrera.setCarrera(carreraNew);
            }
            inscripcionesCarrera = em.merge(inscripcionesCarrera);
            if (alumnoOld != null && !alumnoOld.equals(alumnoNew)) {
                alumnoOld.getInscripcionesCarreraCollection().remove(inscripcionesCarrera);
                alumnoOld = em.merge(alumnoOld);
            }
            if (alumnoNew != null && !alumnoNew.equals(alumnoOld)) {
                alumnoNew.getInscripcionesCarreraCollection().add(inscripcionesCarrera);
                alumnoNew = em.merge(alumnoNew);
            }
            if (carreraOld != null && !carreraOld.equals(carreraNew)) {
                carreraOld.getInscripcionesCarreraCollection().remove(inscripcionesCarrera);
                carreraOld = em.merge(carreraOld);
            }
            if (carreraNew != null && !carreraNew.equals(carreraOld)) {
                carreraNew.getInscripcionesCarreraCollection().add(inscripcionesCarrera);
                carreraNew = em.merge(carreraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                InscripcionesCarreraPK id = inscripcionesCarrera.getInscripcionesCarreraPK();
                if (findInscripcionesCarrera(id) == null) {
                    throw new NonexistentEntityException("The inscripcionesCarrera with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InscripcionesCarreraPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripcionesCarrera inscripcionesCarrera;
            try {
                inscripcionesCarrera = em.getReference(InscripcionesCarrera.class, id);
                inscripcionesCarrera.getInscripcionesCarreraPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripcionesCarrera with id " + id + " no longer exists.", enfe);
            }
            Alumno alumno = inscripcionesCarrera.getAlumno();
            if (alumno != null) {
                alumno.getInscripcionesCarreraCollection().remove(inscripcionesCarrera);
                alumno = em.merge(alumno);
            }
            Carrera carrera = inscripcionesCarrera.getCarrera();
            if (carrera != null) {
                carrera.getInscripcionesCarreraCollection().remove(inscripcionesCarrera);
                carrera = em.merge(carrera);
            }
            em.remove(inscripcionesCarrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InscripcionesCarrera> findInscripcionesCarreraEntities() {
        return findInscripcionesCarreraEntities(true, -1, -1);
    }

    public List<InscripcionesCarrera> findInscripcionesCarreraEntities(int maxResults, int firstResult) {
        return findInscripcionesCarreraEntities(false, maxResults, firstResult);
    }

    private List<InscripcionesCarrera> findInscripcionesCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InscripcionesCarrera.class));
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

    public InscripcionesCarrera findInscripcionesCarrera(InscripcionesCarreraPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InscripcionesCarrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripcionesCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InscripcionesCarrera> rt = cq.from(InscripcionesCarrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
