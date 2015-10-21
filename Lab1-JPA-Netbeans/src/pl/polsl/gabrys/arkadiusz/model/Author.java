package pl.polsl.gabrys.arkadiusz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Class represents single Author entry in author table
 * @author Arkadiusz Gabryś
 * @version 1.0
 */
@Entity
@NamedQueries({
  @NamedQuery(name="Author.findAll",
              query="SELECT e FROM Author e"),
  @NamedQuery(name="Author.findByName",
              query="SELECT e FROM Author e WHERE e.name = :name")
})
public class Author implements Serializable {
    
    /**
     * Author unique id
     */
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Author name
     */
    @Column(nullable = false)
    private String name;
    
    /**
     * Author last name
     */
    @Column(nullable = false)
    private String lastName;
    
    /**
     * Author books
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private List<Book> books;
    
    /**
     * Initializes instance with empty fields
     */
    public Author() {
        this("", "");
    }
    
    /**
     * Initializes instance with given parameters
     * @param name the author name
     * @param lastName the author last name
     */
    public Author(String name, String lastName) {
        this(name, lastName, null);
    }
    
    /**
     * Initializes instance with given parameters
     * @param name the author name
     * @param lastName the author last name
     * @param books the author books list
     */
    public Author(String name, String lastName, List<Book> books) {
        this.name = name == null ? "" : name;
        this.lastName = lastName == null ? "" : lastName;
        
        if (books == null) {
            this.books = new ArrayList<Book>();
        } else {
            this.books = books;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    /**
     * Compares current object with the given one 
     * @param obj the object to compare
     * @return comparison result
     */
    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof Author))
            return false;
        
        if (obj == this)
            return true;
        
        Author rhs = (Author)obj;
        return Objects.equals(this.getId(), rhs.getId());
    }
    
    /**
     * Formats this entity in the form of string
     * @return the string representing this entity
     */
    @Override
    public String toString() {
        return String.format("%d; %s; %s",
                this.getId(),
                this.getName(),
                this.getLastName());
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
