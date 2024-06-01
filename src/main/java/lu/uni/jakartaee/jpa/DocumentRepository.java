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
        logger.debug("Searching for documents published in the year: {}", year);
        TypedQuery<Document> query = em.createQuery(
            "SELECT DISTINCT d FROM Document d LEFT JOIN FETCH d.authors WHERE YEAR(d.publicationDate) = :year", 
            Document.class
        );
        
        List<Document> documents = query.setParameter("year", year).getResultList();
        for (Document doc : documents) {
            Hibernate.initialize(doc.getAuthors());
        }
        return documents;
    }

    @Transactional
    public List<Object[]> selectDocumentByTitlesAndYears() {
        logger.debug("Listing all documents.");
        return em.createQuery("SELECT d.title, d.publicationDate, d.id FROM Document d", Object[].class).getResultList();
    }

    @Transactional
    public Document selectDocumentById(Long id) {
        logger.debug("Finding document by ID: {}", id);
        Document document = em.find(Document.class, id);
        if (document != null) {
            Hibernate.initialize(document.getAuthors());
        }
        return document;
    }

}

