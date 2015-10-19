package pl.polsl.gabrys.arkadiusz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class represents single Book entry in book table
 * @author Arkadiusz Gabryś
 * @version 1.0
 */
@Entity
public class Book implements Serializable {
    
    /**
     * Book unique id
     */
    @Id
    private Long id;
    
    /**
     * Book title
     */
    private String title;
    
    /**
     * Number of pages in book
     */
    private Long pages;
    
    /**
     * Book release date
     */
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    /**
     * Compares current object with the given one
     * @param obj the object to compare
     * @return the comparison result
     */
    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof Book))
            return false;
        
        if (obj == this)
            return true;
        
        Book rhs = (Book)obj;
        return Objects.equals(this.getId(), rhs.getId());
    }
    
    /**
     * Formats this entity in the form of string
     * @return the string representing this entity
     */
    @Override
    public String toString() {
        return String.format("%d; ", this.getId());
    }
    
    /**
     * Calculates hash code which represents current object
     * @return the number which represents current object
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
