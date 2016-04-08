package se.wegelius.olp.model;
// Generated Mar 20, 2016 4:31:10 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Course generated by hbm2java
 */

public class Course  {


     private Integer courseId;
     private ContentProvider contentProvider;
     private CourseBranch courseBranch;
     private CourseType courseType;
     private String courseName;
     private String courseDescription;
     private Date released;
     private String language;
     private String skillLevel;
     private Set<MultipleChoiceQuestion> multipleChoiceQuestions = new HashSet<MultipleChoiceQuestion>(0);
     private Set<Lecture> lectures = new HashSet<Lecture>(0);

    public Course() {
    }

	
    public Course(ContentProvider contentProvider, CourseBranch courseBranch, CourseType courseType) {
        this.contentProvider = contentProvider;
        this.courseBranch = courseBranch;
        this.courseType = courseType;
    }
    public Course(ContentProvider contentProvider, CourseBranch courseBranch, CourseType courseType, String courseName, String courseDescription, Date released, String language, String skillLevel, Set<MultipleChoiceQuestion> multipleChoiceQuestions, Set<Lecture> lectures) {
       this.contentProvider = contentProvider;
       this.courseBranch = courseBranch;
       this.courseType = courseType;
       this.courseName = courseName;
       this.courseDescription = courseDescription;
       this.released = released;
       this.language = language;
       this.skillLevel = skillLevel;
       this.multipleChoiceQuestions = multipleChoiceQuestions;
       this.lectures = lectures;
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
    public Set<MultipleChoiceQuestion> getMultipleChoiceQuestions() {
        return this.multipleChoiceQuestions;
    }
    
    public void setMultipleChoiceQuestions(Set<MultipleChoiceQuestion> multipleChoiceQuestions) {
        this.multipleChoiceQuestions = multipleChoiceQuestions;
    }
    public Set<Lecture> getLectures() {
        return this.lectures;
    }
    
    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }




}


