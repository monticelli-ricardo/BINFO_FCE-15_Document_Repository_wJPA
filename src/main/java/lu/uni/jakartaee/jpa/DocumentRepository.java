package lu.uni.jakartaee.jpa;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.List;

@Stateless
public class DocumentRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "Exercise2")
    @Transient private transient EntityManager em;

    @Transient private static final Logger logger = LogManager.getLogger ( Document.class );

    public List<Document> findByPublicationYear(int year) {
        logger.debug("Searching for documents published in the year: {}", year);
        TypedQuery<Document> query = em.createQuery("SELECT d FROM Document d WHERE YEAR(d.publicationDate) = :year", Document.class);
        query.setParameter("year", year);
        return query.getResultList();
    }

    public List<Object[]> listAllDocuments() {
        logger.debug("Listing all documents.");
        return em.createQuery("SELECT d FROM Document d", Object[].class).getResultList();
    }

    public Document findById(Long id) {
        logger.debug("Finding document by ID: {}", id);
        return em.find(Document.class, id);
    }
}

