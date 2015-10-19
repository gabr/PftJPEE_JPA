package pl.polsl.gabrys.arkadiusz.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Provides CRUD implementation for entities
 * @author Arkadiusz Gabryś
 * @version 1.0
 * @param <T> the entity class
 */
public class DatabaseManager<T> {
    
    /**
     * Persistance unit name
     */
    private final String PERSISTANCE_UNIT_NAME = "Lab1-JPA";
    
    /**
     * Entity manager instance
     */
    private EntityManager entityManager;
    
    /**
     * Class constructor
     * Creates instance of entity manager for persistance unit name
     * saved in const PERSISTANCE_UNIT_NAME.
     */
    public DatabaseManager() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
        entityManager = emf.createEntityManager();
    }
    
    /**
     * Calls begin() method on entity manager transaction
     */
    public void startTransaction() {
        entityManager.getTransaction().begin();
    }
    
    /**
     * Commits current transaction and flushes all changes before it
     */
    public void commitTransaction() {
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
    
    /**
     * Closes entity manager
     */
    public void close() {
        entityManager.close();
    }
    
    /**
     * Finds entity by its id
     * @param objectClass the entity class
     * @param tId the entity id
     * @return the entity with given id
     */
    public T find(Class<T> objectClass, Integer tId) {
        if (tId == null) {
            return null;
        }
        
        return entityManager.find(objectClass, tId);
    }
    
    /**
     * Adds new entity
     * @param t the eintity instance
     * @return the true if operation succeeded
     */
    public boolean persist(T t) {
        if (t == null) {
            return false;
        }
        
        entityManager.persist(t);
        return true;
    }
    
    /**
     * Updates given entity
     * @param t the entity instance
     * @return the chenged entity instance
     */
    public T merge(T t) {
        if (t == null) {
            return null;
        }
        
        return entityManager.merge(t);
    }
    
    /**
     * Removed given entity
     * @param t the entity instance
     * @return the true if operation succeeded
     */
    public boolean remove(T t) {
        if (t == null) {
            return false;
        }
        
        entityManager.remove(t);
        return true;
    }
    
    /**
     * Returns all entities of given entity
     * @param objectClass the entity class
     * @return the list of with all entities
     */
    public List<T> getAll(Class<T> objectClass) {
        String namedQuery = objectClass.getName() + ".findAll";
        Query query = entityManager.createNamedQuery(namedQuery);
        return query.getResultList();
    }
}
