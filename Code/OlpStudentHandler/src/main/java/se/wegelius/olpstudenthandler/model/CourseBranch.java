package se.wegelius.olpstudenthandler.model;
// Generated Apr 7, 2016 10:41:12 PM by Hibernate Tools 4.3.1


import java.util.Set;
import se.wegelius.olpstudenthandler.model.persistance.CourseBranchPersistance;


public class CourseBranch  implements java.io.Serializable {


     private Integer courseBranchId;
     private String courseBranchName;

    public CourseBranch() {
    }

	
    public CourseBranch(String courseBranchName) {
        this.courseBranchName = courseBranchName;
    }
    public CourseBranch(String courseBranchName, Set<Course> courses) {
       this.courseBranchName = courseBranchName;
    }
    
    public CourseBranch(CourseBranchPersistance branch){
        this.courseBranchId = branch.getCourseBranchId();
        this.courseBranchName = branch.getCourseBranchName();
    }
   
    public Integer getCourseBranchId() {
        return this.courseBranchId;
    }
    
    public void setCourseBranchId(Integer courseBranchId) {
        this.courseBranchId = courseBranchId;
    }
    public String getCourseBranchName() {
        return this.courseBranchName;
    }
    
    public void setCourseBranchName(String courseBranchName) {
        this.courseBranchName = courseBranchName;
    }
 
}


