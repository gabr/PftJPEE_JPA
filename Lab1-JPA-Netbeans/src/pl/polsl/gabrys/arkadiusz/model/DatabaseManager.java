package pl.polsl.gabrys.arkadiusz.model;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockTimeoutException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

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
    private static EntityManager entityManager;
    
    /**
     * Class constructor
     * Creates instance of entity manager for persistance unit name
     * saved in const PERSISTANCE_UNIT_NAME.
     */
    public DatabaseManager() {
        if (entityManager == null)
        {
            EntityManagerFactory emf = Persistence
                    .createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
            entityManager = emf.createEntityManager();
        }
    }
    
    /**
     * Calls begin() method on entity manager transaction
     * @throws IllegalStateException if transaction is already active
     */
    public void startTransaction() throws IllegalStateException {
        entityManager.getTransaction().begin();
    }
    
    /**
     * Commits current transaction and flushes all changes before it
     * @throws TransactionRequiredException if transaction is not started
     * @throws PersistenceException if flushing data fails
     */
    public void commitTransaction() throws TransactionRequiredException, PersistenceException {
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
    
    /**
     * Finds entity by its id
     * @param objectClass the entity class
     * @param tId the entity id
     * @return the entity with given id
     * @throws IllegalArgumentException if the first argument does not denote an entity type or the second argument is is null
     */
    public T find(Class<T> objectClass, Long tId) throws IllegalArgumentException {
        if (tId == null) {
            return null;
        }
        
        return entityManager.find(objectClass, tId);
    }
    
    /**
     * Adds new entity
     * @param t the eintity instance
     * @return the true if operation succeeded
     * @throws EntityExistsException - if the entity already exists. (If the entity already exists, the EntityExistsException may be thrown when the persist operation is invoked, or the EntityExistsException or another PersistenceException may be thrown at flush or commit time.)
     * @throws IllegalArgumentException - if the instance is not an entity
     * @throws TransactionRequiredException - if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    public boolean persist(T t) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException{
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
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transactio
     */
    public T merge(T t) throws IllegalArgumentException, TransactionRequiredException{
        if (t == null) {
            return null;
        }
        
        return entityManager.merge(t);
    }
    
    /**
     * Removed given entity
     * @param t the entity instance
     * @return the true if operation succeeded
     * @throws IllegalArgumentException if the instance is not an entity or is a detached entity
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    public boolean remove(T t) throws IllegalArgumentException, TransactionRequiredException {
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
     * @throws IllegalArgumentException if given class is not valid entity class for this operation
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    public List<T> getAll(Class<T> objectClass) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
        String namedQuery = objectClass.getName() + ".findAll";
        String[] split = namedQuery.split("\\.");
        namedQuery = split[split.length - 2] + "." + split[split.length -1];
        Query query = entityManager.createNamedQuery(namedQuery);
        return query.getResultList();
    }
    
    /**
     * Returns all authors with given name
     * @param <Author> used only for author entity
     * @param name the author name
     * @return the list of authors with given name
     * @throws IllegalArgumentException if given class is not valid entity class for this operation
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    public <Author> List<Author> getByName(String name) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
        name = name.replace("\"", "").trim();   
        Query query = entityManager.createNamedQuery("Author.findByName");
        query.setParameter("name", name);
        return query.getResultList();
    }
    
    /**
     * Returns al books with given title
     * @param <Book> used only for book entity
     * @param title the book title
     * @return the list of books with given title
     * @throws IllegalArgumentException if given class is not valid entity class for this operation
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    public <Book> List<Book> getByTitle(String title) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
        title = title.replace("\"", "").trim();
        Query query = entityManager.createNamedQuery("Book.findByTitle");
        query.setParameter("title", title);
        return query.getResultList();
    }
}
