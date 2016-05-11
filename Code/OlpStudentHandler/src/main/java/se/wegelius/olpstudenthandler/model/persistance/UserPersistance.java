package se.wegelius.olpstudenthandler.model.persistance;
// Generated May 10, 2016 4:15:18 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * UserPersistance generated by hbm2java
 */
public class UserPersistance  implements java.io.Serializable {


     private Integer userId;
     private String userName;
     private String email;
     private String password;
     private boolean enabled;
     private Set<VerificationtokenPersistance> verificationtokens = new HashSet<VerificationtokenPersistance>(0);
     private Set <PlaylistPersistance> playlists = new HashSet<PlaylistPersistance>(0);

    public UserPersistance() {
    }

	
    public UserPersistance(boolean enabled) {
        this.enabled = enabled;
    }
    public UserPersistance(String userName, String email, String password, boolean enabled, Set<VerificationtokenPersistance> verificationtokens,
            Set <PlaylistPersistance> playlists) {
       this.userName = userName;
       this.email = email;
       this.password = password;
       this.enabled = enabled;
       this.verificationtokens = verificationtokens;
       this.playlists = playlists;
    }
   
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public Set<VerificationtokenPersistance> getVerificationtokens() {
        return this.verificationtokens;
    }
    
    public void setVerificationtokens(Set<VerificationtokenPersistance> verificationtokens) {
        this.verificationtokens = verificationtokens;
    }

    public Set <PlaylistPersistance> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set <PlaylistPersistance> playlists) {
        this.playlists = playlists;
    }

}


