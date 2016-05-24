package se.wegelius.olp.model;

import java.sql.Time;

public class Lecture {

    private Integer lectureId;
    private int courseId;
    private String lectureName;
    private String video;
    private Time duration;
    private String description;

    public Lecture() {
    }

    public Lecture(int courseId, String lectureName, String video, Time duration, String description) {
        this.courseId = courseId;
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
        return this.courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
