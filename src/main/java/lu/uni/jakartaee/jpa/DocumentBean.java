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
import java.util.List;

@Named
@SessionScoped
public class DocumentBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(DocumentBean.class);
    
    @Inject
    private DocumentRepository documentRepository;

    private List<Document> listDocuments;
    private List<Object[]> listAllDocuments;
    private Document selectedDocument;

    private Integer searchYear;
    private final int currentYear = Year.now().getValue();
    private Long selectedDocumentId;

    private String viewId;

    // Custom Actions
    public void searchDocumentsByYear() {
        if (searchYear != null && searchYear >= 1000 && searchYear <= currentYear) {
            logger.info("Searching for documents published in the year: {}", searchYear);
            listDocuments = documentRepository.findByPublicationYear(searchYear);
            chooseAction("list");
        } else {
            logger.warn("Search year is null");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Year", "Please enter a valid year between 1000 and current year."));
        }

    }

    public void loadAllDocuments() {
        logger.info("Loading all documents.");
        listAllDocuments = documentRepository.listAllDocuments();
        chooseAction("list");
    }

    public void selectDocument(Document doc){
        logger.info("Selecting document with ID: {}", selectedDocumentId);
        setSelectedDocument(doc);
        selectedDocumentId = getSelectedDocument().getId();
        logger.info("Loading details.");
        loadDocumentDetails();
        chooseAction("details");
    }

    public void loadDocumentDetails() {
        if (selectedDocumentId != null) {
            logger.info("Loading details for document with ID: {}", selectedDocumentId);
            selectedDocument = documentRepository.findById(selectedDocumentId);
        } else {
            logger.warn("Selected document ID is null.");
        }
    }

    // Helper Method to decide the View
    public String chooseAction(String status) {
        if (status.equals("details")) { // Check for Win Status
            logger.info("Navigating to Document Details View.");
            return "details";

        } else if(status.equals("list"))  { // Check for Lose Status
            logger.info("Navigating to All Documents View.");
            return "list";

        } else {       // Default return statement
            logger.info("Navigating to Home Page View.");
            return "search"; // or return null; or return some default value
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

