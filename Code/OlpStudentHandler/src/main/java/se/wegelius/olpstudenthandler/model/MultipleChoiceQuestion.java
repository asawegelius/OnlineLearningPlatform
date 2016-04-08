package se.wegelius.olpstudenthandler.model;

public class MultipleChoiceQuestion  implements java.io.Serializable {


     private Integer multipleChoiceQuestionId;
     private Course course;
     private Lecture lecture;
     private String mcqName;
     private String mcqQuestion;

    public MultipleChoiceQuestion() {
    }

	
    public MultipleChoiceQuestion(Course course, Lecture lecture) {
        this.course = course;
        this.lecture = lecture;
    }
    public MultipleChoiceQuestion(Course course, Lecture lecture, String mcqName, String mcqQuestion) {
       this.course = course;
       this.lecture = lecture;
       this.mcqName = mcqName;
       this.mcqQuestion = mcqQuestion;
    }
   
    public Integer getMultipleChoiceQuestionId() {
        return this.multipleChoiceQuestionId;
    }
    
    public void setMultipleChoiceQuestionId(Integer multipleChoiceQuestionId) {
        this.multipleChoiceQuestionId = multipleChoiceQuestionId;
    }
    public Course getCourse() {
        return this.course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    public Lecture getLecture() {
        return this.lecture;
    }
    
    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }
    public String getMcqName() {
        return this.mcqName;
    }
    
    public void setMcqName(String mcqName) {
        this.mcqName = mcqName;
    }
    public String getMcqQuestion() {
        return this.mcqQuestion;
    }
    
    public void setMcqQuestion(String mcqQuestion) {
        this.mcqQuestion = mcqQuestion;
    }


}


