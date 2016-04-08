package se.wegelius.olpstudenthandler.model;

import java.util.Date;

public class Lecture  implements java.io.Serializable {


     private Integer lectureId;
     private Course course;
     private String lectureName;
     private String video;
     private Date duration;

    public Lecture() {
    }

	
    public Lecture(Course course) {
        this.course = course;
    }
    public Lecture(Course course, String lectureName, String video, Date duration) {
       this.course = course;
       this.lectureName = lectureName;
       this.video = video;
       this.duration = duration;
    }
   
    public Integer getLectureId() {
        return this.lectureId;
    }
    
    public void setLectureId(Integer lectureId) {
        this.lectureId = lectureId;
    }
    public Course getCourse() {
        return this.course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    public String getLectureName() {
        return this.lectureName;
    }
    
    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }
    public String getVideo() {
        return this.video;
    }
    
    public void setVideo(String video) {
        this.video = video;
    }
    public Date getDuration() {
        return this.duration;
    }
    
    public void setDuration(Date duration) {
        this.duration = duration;
    }


}


