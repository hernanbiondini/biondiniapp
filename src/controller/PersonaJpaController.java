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
import Modelo.Persona;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author hernanbiondini
 */
public class PersonaJpaController extends EntityController implements Serializable {

    private static PersonaJpaController personaJpaController;
    private static final String QUERY_GET_ALL_PERSONA = "Persona.findAll";

    private PersonaJpaController() {
    }

    public static PersonaJpaController getInstance() {
        if (personaJpaController == null) {
            personaJpaController = new PersonaJpaController();
        }
        return personaJpaController;
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

    public void create(Persona persona) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno alumno = persona.getAlumno();
            if (alumno != null) {
                alumno = em.getReference(alumno.getClass(), alumno.getIdentificador());
                persona.setAlumno(alumno);
            }
            em.persist(persona);
            if (alumno != null) {
                Persona oldIdpersonaOfAlumno = alumno.getIdpersona();
                if (oldIdpersonaOfAlumno != null) {
                    oldIdpersonaOfAlumno.setAlumno(null);
                    oldIdpersonaOfAlumno = em.merge(oldIdpersonaOfAlumno);
                }
                alumno.setIdpersona(persona);
                alumno = em.merge(alumno);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getIdentificador()) != null) {
                throw new PreexistingEntityException("Persona " + persona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdentificador());
            Alumno alumnoOld = persistentPersona.getAlumno();
            Alumno alumnoNew = persona.getAlumno();
            if (alumnoNew != null) {
                alumnoNew = em.getReference(alumnoNew.getClass(), alumnoNew.getIdentificador());
                persona.setAlumno(alumnoNew);
            }
            persona = em.merge(persona);
            if (alumnoOld != null && !alumnoOld.equals(alumnoNew)) {
                alumnoOld.setIdpersona(null);
                alumnoOld = em.merge(alumnoOld);
            }
            if (alumnoNew != null && !alumnoNew.equals(alumnoOld)) {
                Persona oldIdpersonaOfAlumno = alumnoNew.getIdpersona();
                if (oldIdpersonaOfAlumno != null) {
                    oldIdpersonaOfAlumno.setAlumno(null);
                    oldIdpersonaOfAlumno = em.merge(oldIdpersonaOfAlumno);
                }
                alumnoNew.setIdpersona(persona);
                alumnoNew = em.merge(alumnoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdentificador();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editar(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdentificador());
            Alumno alumnoOld = persistentPersona.getAlumno();
            Alumno alumnoNew = persona.getAlumno();

            if (alumnoNew != null) {
                alumnoNew = em.getReference(alumnoNew.getClass(), alumnoNew.getIdentificador());
                persona.setAlumno(alumnoNew);
            }
            persona = em.merge(persona);

            alumnoNew.setIdpersona(persona);
            AlumnoJpaController.getInstance().updateAlumno(alumnoNew);

//            if (alumnoOld != null && !alumnoOld.equals(alumnoNew)) {
//                alumnoOld.setIdpersona(null);
//                alumnoOld = em.merge(alumnoOld);
//            }
//            if (alumnoNew != null && !alumnoNew.equals(alumnoOld)) {
//                Persona oldIdpersonaOfAlumno = alumnoNew.getIdpersona();
//                if (oldIdpersonaOfAlumno != null) {
//                    oldIdpersonaOfAlumno.setAlumno(null);
//                    oldIdpersonaOfAlumno = em.merge(oldIdpersonaOfAlumno);
//                }
//                alumnoNew.setIdpersona(persona);
//                alumnoNew = em.merge(alumnoNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdentificador();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdentificador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            Alumno alumno = persona.getAlumno();
            if (alumno != null) {
                alumno.setIdpersona(null);
                alumno = em.merge(alumno);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Persona> getAllPersona() {
        return this.executeNamedQuery(QUERY_GET_ALL_PERSONA);
    }

    public String guardarAlunoPersona(Persona p, Alumno a) throws Exception{
        EntityManager em = this.getEntityManager();
        String resultado = "OK";
        em.getTransaction().begin();

        System.out.println("");
        System.out.println("getTipodoc: " + p.getTipodoc());
        System.out.println("getDocumento: " + p.getDocumento());
        System.out.println("getNombre: " + p.getNombre());
        System.out.println("getApellido: " + p.getApellido());
        System.out.println("getDireccion: " + p.getDireccion());
        System.out.println("getFechanac: " + p.getFechanac());
        System.out.println("getAlumno().getLegajo(): " + p.getAlumno().getLegajo());
        System.out.println("");

        //-----------------------Persona--------------------------------------------------
            this.create(p);
        //--------------------------------------------------------------------------------
        //-----------------------Alumno--------------------------------------------------
        a.setIdpersona(p);
        AlumnoJpaController.getInstance().create(a);
       
        //-----------------------Alumno--------------------------------------------------

        em.getTransaction().commit();
        em.close();
        return resultado;
    }

    public void updatePersona(Persona entity) throws Exception {
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            Persona entityx = em.find(Persona.class, entity.getIdentificador());
            entityx.setTipodoc(entity.getTipodoc());
            entityx.setDocumento(entity.getDocumento());
            entityx.setNombre(entity.getNombre());
            entityx.setApellido(entity.getApellido());
            entityx.setFechanac(entity.getFechanac());
            //entityx.setAlumno(entity.getAlumno());
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

     public void removePersona(Persona entity) throws Exception {
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            Persona entityx = em.find(Persona.class, entity.getIdentificador());
            em.remove(entityx);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
     
    public void guardarPersona(Persona p) throws Exception{
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        this.create(p);
        em.getTransaction().commit();
        em.close();
    }
     
}
