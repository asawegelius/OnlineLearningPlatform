package se.wegelius.olp.model;
// Generated Mar 20, 2016 4:31:10 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CourseType generated by hbm2java
 */

public class CourseType{


     private int courseTypeId;
     private CourseBranch courseBranch;
     private String courseTypeName;
     private Set<Course> courses = new HashSet<>(0);

    public CourseType() {
    }

	
    public CourseType(int courseTypeId, String courseTypeName) {
        this.courseTypeId = courseTypeId;
        this.courseTypeName = courseTypeName;
    }
    public CourseType(int courseTypeId, CourseBranch courseBranch, String courseTypeName, Set<Course> courses) {
       this.courseTypeId = courseTypeId;
       this.courseBranch = courseBranch;
       this.courseTypeName = courseTypeName;
       this.courses = courses;
    }
   
    public int getCourseTypeId() {
        return this.courseTypeId;
    }
    
    public void setCourseTypeId(int courseTypeId) {
        this.courseTypeId = courseTypeId;
    }
    public CourseBranch getCourseBranch() {
        return this.courseBranch;
    }
    
    public void setCourseBranch(CourseBranch courseBranch) {
        this.courseBranch = courseBranch;
    }
    public String getCourseTypeName() {
        return this.courseTypeName;
    }
    
    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }
    public Set<Course> getCourses() {
        return this.courses;
    }
    
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }




}

