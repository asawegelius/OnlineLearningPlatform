package se.wegelius.olpstudenthandler.model.persistance;
// Generated 17-Apr-2016 20:57:20 by Hibernate Tools 4.3.1

/**
 * UserPersistance generated by hbm2java
 */
public class UserPersistance implements java.io.Serializable {

    private Integer userId;
    private String userName;
    private String password;
    private boolean enabled;

    public UserPersistance() {
    }

    public UserPersistance(String userName, String password, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}