package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public abstract class EntityController {

    public static final String PU = "veterinariaPU";
    private static EntityManagerFactory entityManagerFactory;

    public EntityManager getEntityManager() {
        return this.getEntityManagerFactory().createEntityManager();
    }

    private EntityManagerFactory getEntityManagerFactory() { 
        entityManagerFactory = Persistence.createEntityManagerFactory( "AlumnosPU" );
        return entityManagerFactory;
    }

    protected void persist(Object entity) throws Exception {
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        entity = em.merge(entity);
        try {
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

    }

    protected void persist(Object entity, EntityManager em) {
        em.persist(entity);
    }

}
