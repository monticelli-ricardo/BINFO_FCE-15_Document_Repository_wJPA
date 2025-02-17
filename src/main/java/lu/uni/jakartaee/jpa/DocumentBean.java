package lu.uni.jakartaee.jpa;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Named("DocumentBean")
@SessionScoped
public class DocumentBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(DocumentBean.class);
    
    @Inject
    private DocumentRepository documentRepository;

    private List<Document> listDocuments = new ArrayList<>();
    private List<Object[]> listAllDocuments = new ArrayList<>();
    private Document selectedDocument;

    private Integer searchYear;
    private final int currentYear = Year.now().getValue();
    private Long selectedDocumentId;

    private String viewId;

    // Custom Actions

    // Method to find all documents of a certain publication year
    public void listDocumentsByPublicationYear() {
        if (searchYear != null && searchYear >= 1000 && searchYear <= currentYear) { // Check
        // Clear out before performing a new search.
        resetSelectedDocument();
        // Clear the other list to not render it
        if(!listAllDocuments.isEmpty()){
            listAllDocuments.clear();
        }
        // For loggin purposes
        logger.info("Searching for documents published in the year: {}", searchYear);
        
        // Look in to the database for all documents based on certain publication year
        listDocuments = documentRepository.selectDocumentsByPublicationYear(searchYear);
        
        // Check if the search result is empty
        if (listDocuments.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No documents found for the specified year.", null));
        }

        } else { // Message for bad input
            logger.warn("User input for `searchYear` is null.");
            FacesContext.getCurrentInstance().addMessage("searchYear", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Year Input", "Input must be a valid year format, between 1000 and current year."));
        }

    }


    // Method to List all document titles and publication years
    public void listDocumentByTitlesAndYears() {
        // Clear out before performing a new search.
        resetSelectedDocument();
        // Clear the other list to not render it
        if(!listDocuments.isEmpty()){
            listDocuments.clear();
        }
        // For loggin purposes
        logger.info("Loading all documents.");

        // Look in the database for title and author of all documents
        listAllDocuments = documentRepository.selectDocumentByTitlesAndYears();
        logger.info("Documents: {}", listAllDocuments);

        // Check if the search result is empty
        if (listAllDocuments.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No documents found for the specified year.", null));
        }

        
    }


    // Method to show all details of selected document
    public void selectDocument(){
        // For loggin purposes
        logger.info("Selecting document with ID: {}", selectedDocumentId);
        
        // Get the ID of the selected document
        selectedDocument = documentRepository.selectDocumentById(selectedDocumentId);
        logger.info("Loading details of Document: {}", selectedDocument);
    }

    // Helper method to load all the details of selected document
    public void loadDocumentDetails() {
        if (selectedDocumentId != null) { // Check
            // Extract the data from the database
            selectDocument();
            logger.info("Document Title: {}", selectedDocument.getTitle());
            logger.info("Document Abstract text: {}", selectedDocument.getAbstractText());
            logger.info("Document Publisher: {}", selectedDocument.getPublisher());
            logger.info("Document Publish Date: {}", selectedDocument.getPublicationDate());
            logger.info("Document Location: {}", selectedDocument.getStorageLocation());
            logger.info("Document Authors: {}", selectedDocument.getAuthors());

            // Ensure proper update targeting
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":detailsForm");
                
        } else { // Warning message
            logger.warn("Selected document ID is null.");
        }
    }

    // Helper method to reset the `selectedDocument` data after a new search starts
    public void resetSelectedDocument() {
        this.selectedDocument = null;
    }

    // Getters and setters

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getListDocuments() {
        return listDocuments;
    }

    public void setListDocuments(List<Document> documentsList) {
        this.listDocuments = documentsList;
    }

    public List<Object[]> getListAllDocuments() {
        return listAllDocuments;
    }

    public void setListAllDocuments(List<Object[]> documentTitlesAndYears) {
        this.listAllDocuments = documentTitlesAndYears;
    }

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    public Integer getSearchYear() {
        return searchYear;
    }

    public void setSearchYear(Integer searchYear) {
        this.searchYear = searchYear;
    }

    public Long getSelectedDocumentId() {
        return selectedDocumentId;
    }

    public void setSelectedDocumentId(Long selectedDocumentId) {
        this.selectedDocumentId = selectedDocumentId;
    }

    public int getCurrentYear() {
        return currentYear;
    }
    

}

