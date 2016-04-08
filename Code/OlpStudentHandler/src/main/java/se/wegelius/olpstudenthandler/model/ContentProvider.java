package se.wegelius.olpstudenthandler.model;

import se.wegelius.olpstudenthandler.model.persistance.ContentProviderPersistance;

public class ContentProvider  implements java.io.Serializable {


     private Integer contentProviderId;
     private String contentProviderName;
     private String contentProviderDescription;
     private String contentProviderEmail;

    public ContentProvider() {
    }

	
    public ContentProvider(String contentProviderName) {
        this.contentProviderName = contentProviderName;
    }
    public ContentProvider(String contentProviderName, String contentProviderDescription, String contentProviderEmail) {
       this.contentProviderName = contentProviderName;
       this.contentProviderDescription = contentProviderDescription;
       this.contentProviderEmail = contentProviderEmail;
    }
    
    public ContentProvider(ContentProviderPersistance provider){
        this.contentProviderId = provider.getContentProviderId();
        this.contentProviderDescription = provider.getContentProviderDescription();
        this.contentProviderEmail = provider.getContentProviderEmail();
        this.contentProviderName = provider.getContentProviderName();
    }
   
    public Integer getContentProviderId() {
        return this.contentProviderId;
    }
    
    public void setContentProviderId(Integer contentProviderId) {
        this.contentProviderId = contentProviderId;
    }
    public String getContentProviderName() {
        return this.contentProviderName;
    }
    
    public void setContentProviderName(String contentProviderName) {
        this.contentProviderName = contentProviderName;
    }
    public String getContentProviderDescription() {
        return this.contentProviderDescription;
    }
    
    public void setContentProviderDescription(String contentProviderDescription) {
        this.contentProviderDescription = contentProviderDescription;
    }
    public String getContentProviderEmail() {
        return this.contentProviderEmail;
    }
    
    public void setContentProviderEmail(String contentProviderEmail) {
        this.contentProviderEmail = contentProviderEmail;
    }

}


