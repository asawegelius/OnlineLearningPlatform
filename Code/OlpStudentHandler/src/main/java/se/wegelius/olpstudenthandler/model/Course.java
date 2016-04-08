package se.wegelius.olpstudenthandler.model;


import java.util.Date;
import se.wegelius.olpstudenthandler.model.persistance.CoursePersistance;


public class Course  implements java.io.Serializable {


     private Integer courseId;
     private ContentProvider contentProvider;
     private CourseBranch courseBranch;
     private CourseType courseType;
     private String courseName;
     private String courseDescription;
     private Date released;
     private String language;
     private String skillLevel;

    public Course() {
    }

	
    public Course(ContentProvider contentProvider, CourseBranch courseBranch, CourseType courseType) {
        this.contentProvider = contentProvider;
        this.courseBranch = courseBranch;
        this.courseType = courseType;
    }
    public Course(ContentProvider contentProvider, CourseBranch courseBranch, CourseType courseType, String courseName, String courseDescription, Date released, String language, String skillLevel) {
       this.contentProvider = contentProvider;
       this.courseBranch = courseBranch;
       this.courseType = courseType;
       this.courseName = courseName;
       this.courseDescription = courseDescription;
       this.released = released;
       this.language = language;
       this.skillLevel = skillLevel;
    }
    
    public Course(CoursePersistance course){
        this.courseBranch = new CourseBranch(course.getCourseBranch());
        this.courseDescription = course.getCourseDescription();
        this.courseId = course.getCourseId();
        this.courseName = course.getCourseName();
        this.released = course.getReleased();
        this.language = course.getLanguage();
        this.skillLevel = course.getSkillLevel();
        this.contentProvider = new ContentProvider(course.getContentProvider());
    }
   
    public Integer getCourseId() {
        return this.courseId;
    }
    
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    public ContentProvider getContentProvider() {
        return this.contentProvider;
    }
    
    public void setContentProvider(ContentProvider contentProvider) {
        this.contentProvider = contentProvider;
    }
    public CourseBranch getCourseBranch() {
        return this.courseBranch;
    }
    
    public void setCourseBranch(CourseBranch courseBranch) {
        this.courseBranch = courseBranch;
    }
    public CourseType getCourseType() {
        return this.courseType;
    }
    
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
    public String getCourseName() {
        return this.courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getCourseDescription() {
        return this.courseDescription;
    }
    
    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
    public Date getReleased() {
        return this.released;
    }
    
    public void setReleased(Date released) {
        this.released = released;
    }
    public String getLanguage() {
        return this.language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getSkillLevel() {
        return this.skillLevel;
    }
    
    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

}


