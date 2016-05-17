package se.wegelius.olpstudenthandler.model;

import java.sql.Time;
import se.wegelius.olpstudenthandler.model.persistance.LecturePersistance;

public class Lecture  implements java.io.Serializable {


     private Integer lectureId;
     private int courseID;
     private String lectureName;
     private String video;
     private Time duration;
     private String description;

    public Lecture() {
    }

	
    public Lecture(LecturePersistance persistance) {
        this.lectureId = persistance.getLectureId();
        this.courseID = persistance.getCourse().getCourseId();
        this.lectureName = persistance.getLectureName();
        this.video = persistance.getVideo();
        this.duration = persistance.getDuration();
        this.description = persistance.getDescription();
    }
    public Lecture(int courseId, String lectureName, String video, Time duration, String description) {
       this.courseID = courseId;
       this.lectureName = lectureName;
       this.video = video;
       this.duration = duration;
       this.description = description;
    }
   
    public Integer getLectureId() {
        return this.lectureId;
    }
    
    public void setLectureId(Integer lectureId) {
        this.lectureId = lectureId;
    }
    public int getCourseId() {
        return this.courseID;
    }
    
    public void setCourseId(int course) {
        this.courseID = course;
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
    public Time getDuration() {
        return this.duration;
    }
    
    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}


