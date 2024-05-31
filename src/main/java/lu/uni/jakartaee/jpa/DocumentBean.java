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
        if (searchYear != null && searchYear >= 1900 && searchYear <= currentYear) { // Check
            logger.info("Searching for documents published in the year: {}", searchYear);
            
            // Look in to the database for all documents based on certain publication year
            listDocuments = documentRepository.selectDocumentsByPublicationYear(searchYear);
            
            // Clear list to not render it
            if(!listAllDocuments.isEmpty()){
                listAllDocuments.clear();
            }
  

        } else { // Message for bad input
            logger.warn("Search year is null");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Year", "Please enter a valid year between 1000 and current year."));
        }

    }


    // Method to List all document titles and publication years
    public void listDocumentByTitlesAndYears() {
        logger.info("Loading all documents.");
        // Look in the database for title and author of all documents
        listAllDocuments = documentRepository.selectDocumentByTitlesAndYears();

        // Clear list to not render it
        if(!listDocuments.isEmpty()){
            listDocuments.clear();
        }
        
    }


    // Method to show all details of selected document
    public void selectDocument(Document doc){
        logger.info("Selecting document with ID: {}", selectedDocumentId);
        setSelectedDocument(doc);

        // Get the ID of the selected document
        selectedDocumentId = getSelectedDocument().getId();
        logger.info("Loading details.");
        
        // Look in to the database for the selected document details
        loadDocumentDetails();
    }

    // Helper method to load all the details of selected document
    public void loadDocumentDetails() {
        if (selectedDocumentId != null) { // Check
            logger.info("Loading details for document with ID: {}", selectedDocumentId);
            
            // Extract the data from the database
            selectedDocument = documentRepository.selectDocumentById(selectedDocumentId);
        } else { // Warning message
            logger.warn("Selected document ID is null.");
        }
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

