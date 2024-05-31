package lu.uni.jakartaee.jpa;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.Year;
import java.util.List;

@Named
@SessionScoped
public class DocumentBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private DocumentRepository documentRepository;

    private List<Document> listDocuments;
    private List<Object[]> listAllDocuments;
    private Document selectedDocument;

    private Integer searchYear;
    private final int currentYear = Year.now().getValue();
    private Long selectedDocumentId;

    // Custom Actions
    public void searchDocumentsByYear() {
        if (searchYear != null && searchYear >= 1000 && searchYear <= currentYear) {
            listDocuments = documentRepository.findByPublicationYear(searchYear);
            chooseAction("list");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Year", "Please enter a valid year between 1000 and current year."));
        }

    }

    public void loadAllDocuments() {
        listAllDocuments = documentRepository.listAllDocuments();
        chooseAction("list");
    }

    public void selectDocument(Document doc){
        setSelectedDocument(doc);
        selectedDocumentId = getSelectedDocument().getId();
        loadDocumentDetails();
        chooseAction("details");
    }

    public void loadDocumentDetails() {
        if (selectedDocumentId != null) {
            selectedDocument = documentRepository.findById(selectedDocumentId);
        }
    }

    // Helper Method to decide the View
    public String chooseAction(String status) {
        if (status.equals("details")) { // Check for Win Status
            return "details";

        } else if(status.equals("list"))  { // Check for Lose Status
            return "list";

        } else {       // Default return statement
            return "search"; // or return null; or return some default value
        }
        
    }


    // Getters and setters
    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getDocumentsList() {
        return listDocuments;
    }

    public void setDocumentsList(List<Document> documentsList) {
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

