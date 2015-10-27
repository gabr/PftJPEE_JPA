package pl.polsl.gabrys.arkadiusz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class represents single Book entry in book table
 * @author Arkadiusz Gabryś
 * @version 1.0
 */
@Entity
@NamedQueries({
  @NamedQuery(name="Book.findAll",
              query="SELECT e FROM Book e"),
  @NamedQuery(name="Book.findByTitle",
              query="SELECT e FROM Book e WHERE e.title = :title")
})
public class Book implements Serializable {
    
    /**
     * Book unique id
     */
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Book title
     */
    @Column(nullable = false)
    private String title;
    
    /**
     * Number of pages in book
     */
    @Column(nullable = false)
    private Long pages;
    
    /**
     * Book release date
     */
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    
    /**
     * Author of the book
     */
    @JoinColumn(nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Author author;

    /**
     * Initializes instance with empty fields
     */
    public Book() {
        this(null);
    }
    
    /**
     * Initializes instance with given parameter
     * @param title the book title
     */
    public Book(String title) {
        this(title, null);
    }
    
    /**
     * Initializes instance with given parameters
     * @param title the book title
     * @param releaseDate the book release date
     */
    public Book(String title, Date releaseDate) {
        this(title, 0L, releaseDate, null);
    }
    
    /**
     * Initializes instance with given parameter
     * @param title the book title
     * @param pages the number of pages
     * @param releaseDate the book release date
     * @param author the book author
     */
    public Book(String title, Long pages, Date releaseDate, Author author) {
        if (title == null) {
            this.title = "";
        } else {
            this.title = title;
        }
        
        if (pages == null) {
            this.pages = 0L;
        } else {
            this.pages = pages;
        }
        
        if (releaseDate == null) {
            this.releaseDate = new Date();
        } else {
            this.releaseDate = releaseDate;
        }
        
        this.author = author;
    }

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
        return String.format("%d; %s; %d; %s; %d",
                this.getId(), this.getTitle(), this.getPages(),
                this.getReleaseDate() != null ? this.getReleaseDate().toString() : (new Date()),
                this.getAuthor() != null ? this.getAuthor().getId() : 0);
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
