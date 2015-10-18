package pl.polsl.gabrys.arkadiusz.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class represents single Author entry in author table
 * @author arkad_000
 * @version 1.0
 */
@Entity
public class Author implements Serializable {
    
    /**
     * Author unique id
     */
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * Compares current object with the given one 
     * @param obj the object to compare
     * @return comparison result
     */
    @Override
    public boolean equals (Object obj) {
        return false;
    }
    
    /**
     * Calculates hash code which represents current object
     * @return the number which represents current object
     */
    @Override
    public int hashCode() {
        return 0;
    }
}
