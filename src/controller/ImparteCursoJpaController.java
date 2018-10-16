
package controller;

import Modelo.*;
import Modelo.exceptions.NonexistentEntityException;
import Modelo.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author hernanbiondini
 */
public class ImparteCursoJpaController implements Serializable {

    public ImparteCursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ImparteCurso imparteCurso) throws PreexistingEntityException, Exception {
        if (imparteCurso.getImparteCursoPK() == null) {
            imparteCurso.setImparteCursoPK(new ImparteCursoPK());
        }
        imparteCurso.getImparteCursoPK().setIdcurso(imparteCurso.getCurso().getIdentificador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente docente = imparteCurso.getDocente();
            if (docente != null) {
                docente = em.getReference(docente.getClass(), docente.getIdentificador());
                imparteCurso.setDocente(docente);
            }
            em.persist(imparteCurso);
            if (docente != null) {
                docente.getImparteCurso().add(imparteCurso);
                docente = em.merge(docente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findImparteCurso(imparteCurso.getImparteCursoPK()) != null) {
                throw new PreexistingEntityException("ImparteCurso " + imparteCurso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImparteCurso imparteCurso) throws NonexistentEntityException, Exception {
        imparteCurso.getImparteCursoPK().setIdcurso(imparteCurso.getCurso().getIdentificador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImparteCurso persistentImparteCurso = em.find(ImparteCurso.class, imparteCurso.getImparteCursoPK());
            Docente docenteOld = persistentImparteCurso.getDocente();
            Docente docenteNew = imparteCurso.getDocente();
            if (docenteNew != null) {
                docenteNew = em.getReference(docenteNew.getClass(), docenteNew.getIdentificador());
                imparteCurso.setDocente(docenteNew);
            }
            imparteCurso = em.merge(imparteCurso);
            if (docenteOld != null && !docenteOld.equals(docenteNew)) {
                docenteOld.getImparteCurso().remove(imparteCurso);
                docenteOld = em.merge(docenteOld);
            }
            if (docenteNew != null && !docenteNew.equals(docenteOld)) {
                docenteNew.getImparteCurso().add(imparteCurso);
                docenteNew = em.merge(docenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ImparteCursoPK id = imparteCurso.getImparteCursoPK();
                if (findImparteCurso(id) == null) {
                    throw new NonexistentEntityException("The imparteCurso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ImparteCursoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImparteCurso imparteCurso;
            try {
                imparteCurso = em.getReference(ImparteCurso.class, id);
                imparteCurso.getImparteCursoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imparteCurso with id " + id + " no longer exists.", enfe);
            }
            Docente docente = imparteCurso.getDocente();
            if (docente != null) {
                docente.getImparteCurso().remove(imparteCurso);
                docente = em.merge(docente);
            }
            em.remove(imparteCurso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImparteCurso> findImparteCursoEntities() {
        return findImparteCursoEntities(true, -1, -1);
    }

    public List<ImparteCurso> findImparteCursoEntities(int maxResults, int firstResult) {
        return findImparteCursoEntities(false, maxResults, firstResult);
    }

    private List<ImparteCurso> findImparteCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImparteCurso.class));
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

    public ImparteCurso findImparteCurso(ImparteCursoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImparteCurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getImparteCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImparteCurso> rt = cq.from(ImparteCurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
