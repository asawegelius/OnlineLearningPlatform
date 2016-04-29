package se.wegelius.olpstudenthandler.model.persistance;
// Generated Apr 27, 2016 1:23:38 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CourseTypePersistance generated by hbm2java
 */
public class CourseTypePersistance  implements java.io.Serializable {


     private Integer courseTypeId;
     private String courseTypeName;
     private Integer ctCourseBranchFk;
     private Set<CoursePersistance> courses = new HashSet<CoursePersistance>(0);

    public CourseTypePersistance() {
    }

    public CourseTypePersistance(String courseTypeName, Integer ctCourseBranchFk, Set<CoursePersistance> courses) {
       this.courseTypeName = courseTypeName;
       this.ctCourseBranchFk = ctCourseBranchFk;
       this.courses = courses;
    }
   
    public Integer getCourseTypeId() {
        return this.courseTypeId;
    }
    
    public void setCourseTypeId(Integer courseTypeId) {
        this.courseTypeId = courseTypeId;
    }
    public String getCourseTypeName() {
        return this.courseTypeName;
    }
    
    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }
    public Integer getCtCourseBranchFk() {
        return this.ctCourseBranchFk;
    }
    
    public void setCtCourseBranchFk(Integer ctCourseBranchFk) {
        this.ctCourseBranchFk = ctCourseBranchFk;
    }
    public Set<CoursePersistance> getCourses() {
        return this.courses;
    }
    
    public void setCourses(Set<CoursePersistance> courses) {
        this.courses = courses;
    }




}


