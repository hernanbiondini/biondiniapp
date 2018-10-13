/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Modelo.Docente;
import Modelo.ImparteCurso;
import Modelo.Persona;
import Modelo.exceptions.IllegalOrphanException;
import Modelo.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernanbiondini
 */
public class DocenteJpaController implements Serializable {

    public DocenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Docente docente) {
        if (docente.getImparteCurso() == null) {
            docente.setImparteCurso(new ArrayList<ImparteCurso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona idpersona = docente.getIdpersona();
            if (idpersona != null) {
                idpersona = em.getReference(idpersona.getClass(), idpersona.getIdentificador());
                docente.setIdpersona(idpersona);
            }
            Collection<ImparteCurso> attachedImparteCurso = new ArrayList<ImparteCurso>();
            for (ImparteCurso imparteCursoImparteCursoToAttach : docente.getImparteCurso()) {
                imparteCursoImparteCursoToAttach = em.getReference(imparteCursoImparteCursoToAttach.getClass(), imparteCursoImparteCursoToAttach.getImparteCursoPK());
                attachedImparteCurso.add(imparteCursoImparteCursoToAttach);
            }
            docente.setImparteCurso(attachedImparteCurso);
            em.persist(docente);
            if (idpersona != null) {
                Docente oldDocenteOfIdpersona = idpersona.getDocente();
                if (oldDocenteOfIdpersona != null) {
                    oldDocenteOfIdpersona.setIdpersona(null);
                    oldDocenteOfIdpersona = em.merge(oldDocenteOfIdpersona);
                }
                idpersona.setDocente(docente);
                idpersona = em.merge(idpersona);
            }
            for (ImparteCurso imparteCursoImparteCurso : docente.getImparteCurso()) {
                Docente oldDocenteOfImparteCursoImparteCurso = imparteCursoImparteCurso.getDocente();
                imparteCursoImparteCurso.setDocente(docente);
                imparteCursoImparteCurso = em.merge(imparteCursoImparteCurso);
                if (oldDocenteOfImparteCursoImparteCurso != null) {
                    oldDocenteOfImparteCursoImparteCurso.getImparteCurso().remove(imparteCursoImparteCurso);
                    oldDocenteOfImparteCursoImparteCurso = em.merge(oldDocenteOfImparteCursoImparteCurso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Docente docente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente persistentDocente = em.find(Docente.class, docente.getIdentificador());
            Persona idpersonaOld = persistentDocente.getIdpersona();
            Persona idpersonaNew = docente.getIdpersona();
            Collection<ImparteCurso> imparteCursoOld = persistentDocente.getImparteCurso();
            Collection<ImparteCurso> imparteCursoNew = docente.getImparteCurso();
            List<String> illegalOrphanMessages = null;
            for (ImparteCurso imparteCursoOldImparteCurso : imparteCursoOld) {
                if (!imparteCursoNew.contains(imparteCursoOldImparteCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ImparteCurso " + imparteCursoOldImparteCurso + " since its docente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idpersonaNew != null) {
                idpersonaNew = em.getReference(idpersonaNew.getClass(), idpersonaNew.getIdentificador());
                docente.setIdpersona(idpersonaNew);
            }
            Collection<ImparteCurso> attachedImparteCursoNew = new ArrayList<ImparteCurso>();
            for (ImparteCurso imparteCursoNewImparteCursoToAttach : imparteCursoNew) {
                imparteCursoNewImparteCursoToAttach = em.getReference(imparteCursoNewImparteCursoToAttach.getClass(), imparteCursoNewImparteCursoToAttach.getImparteCursoPK());
                attachedImparteCursoNew.add(imparteCursoNewImparteCursoToAttach);
            }
            imparteCursoNew = attachedImparteCursoNew;
            docente.setImparteCurso(imparteCursoNew);
            docente = em.merge(docente);
            if (idpersonaOld != null && !idpersonaOld.equals(idpersonaNew)) {
                idpersonaOld.setDocente(null);
                idpersonaOld = em.merge(idpersonaOld);
            }
            if (idpersonaNew != null && !idpersonaNew.equals(idpersonaOld)) {
                Docente oldDocenteOfIdpersona = idpersonaNew.getDocente();
                if (oldDocenteOfIdpersona != null) {
                    oldDocenteOfIdpersona.setIdpersona(null);
                    oldDocenteOfIdpersona = em.merge(oldDocenteOfIdpersona);
                }
                idpersonaNew.setDocente(docente);
                idpersonaNew = em.merge(idpersonaNew);
            }
            for (ImparteCurso imparteCursoNewImparteCurso : imparteCursoNew) {
                if (!imparteCursoOld.contains(imparteCursoNewImparteCurso)) {
                    Docente oldDocenteOfImparteCursoNewImparteCurso = imparteCursoNewImparteCurso.getDocente();
                    imparteCursoNewImparteCurso.setDocente(docente);
                    imparteCursoNewImparteCurso = em.merge(imparteCursoNewImparteCurso);
                    if (oldDocenteOfImparteCursoNewImparteCurso != null && !oldDocenteOfImparteCursoNewImparteCurso.equals(docente)) {
                        oldDocenteOfImparteCursoNewImparteCurso.getImparteCurso().remove(imparteCursoNewImparteCurso);
                        oldDocenteOfImparteCursoNewImparteCurso = em.merge(oldDocenteOfImparteCursoNewImparteCurso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = docente.getIdentificador();
                if (findDocente(id) == null) {
                    throw new NonexistentEntityException("The docente with id " + id + " no longer exists.");
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
            Docente docente;
            try {
                docente = em.getReference(Docente.class, id);
                docente.getIdentificador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The docente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ImparteCurso> imparteCursoOrphanCheck = docente.getImparteCurso();
            for (ImparteCurso imparteCursoOrphanCheckImparteCurso : imparteCursoOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Docente (" + docente + ") cannot be destroyed since the ImparteCurso " + imparteCursoOrphanCheckImparteCurso + " in its imparteCurso field has a non-nullable docente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona idpersona = docente.getIdpersona();
            if (idpersona != null) {
                idpersona.setDocente(null);
                idpersona = em.merge(idpersona);
            }
            em.remove(docente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Docente> findDocenteEntities() {
        return findDocenteEntities(true, -1, -1);
    }

    public List<Docente> findDocenteEntities(int maxResults, int firstResult) {
        return findDocenteEntities(false, maxResults, firstResult);
    }

    private List<Docente> findDocenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Docente.class));
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

    public Docente findDocente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Docente.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Docente> rt = cq.from(Docente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void updateDocente(Docente entity) throws Exception {
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            Docente entityx = em.find(Docente.class, entity.getIdentificador());
            entityx.setIdpersona(entity.getIdpersona());
            entityx.setMatricula(entity.getMatricula());
            entityx.setImparteCurso(entity.getImparteCurso());
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
     
     public void removeDocente(Docente entity) throws Exception {
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            Docente entityx = em.find(Docente.class, entity.getIdentificador());
            em.remove(entityx);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
     
     public void guardarDocente(Docente a) throws Exception{
        EntityManager em = this.getEntityManager();
        String resultado = "OK";
        em.getTransaction().begin();
        this.create(a);
        em.getTransaction().commit();
        em.close();
    }
}
