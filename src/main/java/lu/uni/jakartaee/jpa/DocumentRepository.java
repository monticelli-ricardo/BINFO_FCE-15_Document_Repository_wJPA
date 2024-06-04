package lu.uni.jakartaee.jpa;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.List;

@Stateless
@Transactional
public class DocumentRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "Exercise2")
    @Transient private transient EntityManager em;

    @Transient private static final Logger logger = LogManager.getLogger ( Document.class );

    @Transactional
    public List<Document> selectDocumentsByPublicationYear(int year) {
        // For logging purposes
        logger.debug("Searching for documents published in the year: {}", year);
        // JPQL query to select documents published in the specified year
        TypedQuery<Document> query = em.createQuery(
            // LEFT JOIN FETCH to eagerly load the associated authors to avoid lazy loading issues
            "SELECT DISTINCT d FROM Document d LEFT JOIN FETCH d.authors WHERE YEAR(d.publicationDate) = :year", 
            Document.class
        );
        // Set the input `year` as query parameter 
        // Execute the query and returns the list of documents
        List<Document> documents = query.setParameter("year", year).getResultList();
        return documents;
    }

    @Transactional
    public List<Object[]> selectDocumentByTitlesAndYears() {
        // For logging purposes
        logger.debug("Listing all documents.");
        // JPQL query to select the title, publication date, and ID of each document in the documents table
        TypedQuery<Object[]> query = em.createQuery(
            "SELECT d.title, d.publicationDate, d.id FROM Document d", 
            Object[].class);
        
        // Execute the query and returns the list of documents
        List<Object[]> documents = query.getResultList();

        return documents;
    }

    @Transactional
    public Document selectDocumentById(Long id) {
        // For logging purposes
        logger.debug("Finding document by ID: {}", id);
        // Uses the EntityManager's find method to fetch the Document entity with the specified ID
        Document document = em.find(Document.class, id);
       // Check to avoud LazyInitializationException 
        if (document != null) {
            //  Initializes the authors collection to ensure it can be accessed outside the persistence context.
            Hibernate.initialize(document.getAuthors());
        }
        return document;
    }

}

