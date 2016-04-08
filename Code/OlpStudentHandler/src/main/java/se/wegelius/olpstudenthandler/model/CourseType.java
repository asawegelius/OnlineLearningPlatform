package se.wegelius.olpstudenthandler.model;


import java.util.Set;


public class CourseType  implements java.io.Serializable {


     private Integer courseTypeId;
     private String courseTypeName;
     private Integer ctCourseBranchFk;

    public CourseType() {
    }

    public CourseType(String courseTypeName, Integer ctCourseBranchFk, Set<Course> courses) {
       this.courseTypeName = courseTypeName;
       this.ctCourseBranchFk = ctCourseBranchFk;
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

}


