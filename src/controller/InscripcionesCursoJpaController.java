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
import Modelo.Curso;
import Modelo.InscripcionesCurso;
import Modelo.InscripcionesCursoPK;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernanbiondini
 */
public class InscripcionesCursoJpaController implements Serializable {

    public InscripcionesCursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InscripcionesCurso inscripcionesCurso) throws PreexistingEntityException, Exception {
        if (inscripcionesCurso.getInscripcionesCursoPK() == null) {
            inscripcionesCurso.setInscripcionesCursoPK(new InscripcionesCursoPK());
        }
        inscripcionesCurso.getInscripcionesCursoPK().setIdcurso(inscripcionesCurso.getCurso().getIdentificador());
        inscripcionesCurso.getInscripcionesCursoPK().setIdalumno(inscripcionesCurso.getAlumno().getIdentificador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno alumno = inscripcionesCurso.getAlumno();
            if (alumno != null) {
                alumno = em.getReference(alumno.getClass(), alumno.getIdentificador());
                inscripcionesCurso.setAlumno(alumno);
            }
            Curso curso = inscripcionesCurso.getCurso();
            if (curso != null) {
                curso = em.getReference(curso.getClass(), curso.getIdentificador());
                inscripcionesCurso.setCurso(curso);
            }
            em.persist(inscripcionesCurso);
            if (alumno != null) {
                alumno.getInscripcionesCursoCollection().add(inscripcionesCurso);
                alumno = em.merge(alumno);
            }
            if (curso != null) {
                curso.getInscripcionesCursoCollection().add(inscripcionesCurso);
                curso = em.merge(curso);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInscripcionesCurso(inscripcionesCurso.getInscripcionesCursoPK()) != null) {
                throw new PreexistingEntityException("InscripcionesCurso " + inscripcionesCurso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InscripcionesCurso inscripcionesCurso) throws NonexistentEntityException, Exception {
        inscripcionesCurso.getInscripcionesCursoPK().setIdcurso(inscripcionesCurso.getCurso().getIdentificador());
        inscripcionesCurso.getInscripcionesCursoPK().setIdalumno(inscripcionesCurso.getAlumno().getIdentificador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripcionesCurso persistentInscripcionesCurso = em.find(InscripcionesCurso.class, inscripcionesCurso.getInscripcionesCursoPK());
            Alumno alumnoOld = persistentInscripcionesCurso.getAlumno();
            Alumno alumnoNew = inscripcionesCurso.getAlumno();
            Curso cursoOld = persistentInscripcionesCurso.getCurso();
            Curso cursoNew = inscripcionesCurso.getCurso();
            if (alumnoNew != null) {
                alumnoNew = em.getReference(alumnoNew.getClass(), alumnoNew.getIdentificador());
                inscripcionesCurso.setAlumno(alumnoNew);
            }
            if (cursoNew != null) {
                cursoNew = em.getReference(cursoNew.getClass(), cursoNew.getIdentificador());
                inscripcionesCurso.setCurso(cursoNew);
            }
            inscripcionesCurso = em.merge(inscripcionesCurso);
            if (alumnoOld != null && !alumnoOld.equals(alumnoNew)) {
                alumnoOld.getInscripcionesCursoCollection().remove(inscripcionesCurso);
                alumnoOld = em.merge(alumnoOld);
            }
            if (alumnoNew != null && !alumnoNew.equals(alumnoOld)) {
                alumnoNew.getInscripcionesCursoCollection().add(inscripcionesCurso);
                alumnoNew = em.merge(alumnoNew);
            }
            if (cursoOld != null && !cursoOld.equals(cursoNew)) {
                cursoOld.getInscripcionesCursoCollection().remove(inscripcionesCurso);
                cursoOld = em.merge(cursoOld);
            }
            if (cursoNew != null && !cursoNew.equals(cursoOld)) {
                cursoNew.getInscripcionesCursoCollection().add(inscripcionesCurso);
                cursoNew = em.merge(cursoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                InscripcionesCursoPK id = inscripcionesCurso.getInscripcionesCursoPK();
                if (findInscripcionesCurso(id) == null) {
                    throw new NonexistentEntityException("The inscripcionesCurso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InscripcionesCursoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripcionesCurso inscripcionesCurso;
            try {
                inscripcionesCurso = em.getReference(InscripcionesCurso.class, id);
                inscripcionesCurso.getInscripcionesCursoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripcionesCurso with id " + id + " no longer exists.", enfe);
            }
            Alumno alumno = inscripcionesCurso.getAlumno();
            if (alumno != null) {
                alumno.getInscripcionesCursoCollection().remove(inscripcionesCurso);
                alumno = em.merge(alumno);
            }
            Curso curso = inscripcionesCurso.getCurso();
            if (curso != null) {
                curso.getInscripcionesCursoCollection().remove(inscripcionesCurso);
                curso = em.merge(curso);
            }
            em.remove(inscripcionesCurso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InscripcionesCurso> findInscripcionesCursoEntities() {
        return findInscripcionesCursoEntities(true, -1, -1);
    }

    public List<InscripcionesCurso> findInscripcionesCursoEntities(int maxResults, int firstResult) {
        return findInscripcionesCursoEntities(false, maxResults, firstResult);
    }

    private List<InscripcionesCurso> findInscripcionesCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InscripcionesCurso.class));
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

    public InscripcionesCurso findInscripcionesCurso(InscripcionesCursoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InscripcionesCurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripcionesCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InscripcionesCurso> rt = cq.from(InscripcionesCurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
