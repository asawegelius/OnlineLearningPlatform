package se.wegelius.olpstudenthandler.model.persistance;
// Generated May 10, 2016 4:15:18 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CourseBranchPersistance generated by hbm2java
 */
public class CourseBranchPersistance  implements java.io.Serializable {


     private Integer courseBranchId;
     private String courseBranchName;
     private Set<CoursePersistance> courses = new HashSet<CoursePersistance>(0);

    public CourseBranchPersistance() {
    }

	
    public CourseBranchPersistance(String courseBranchName) {
        this.courseBranchName = courseBranchName;
    }
    public CourseBranchPersistance(String courseBranchName, Set<CoursePersistance> courses) {
       this.courseBranchName = courseBranchName;
       this.courses = courses;
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
    public Set<CoursePersistance> getCourses() {
        return this.courses;
    }
    
    public void setCourses(Set<CoursePersistance> courses) {
        this.courses = courses;
    }




}


