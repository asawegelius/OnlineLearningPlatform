package se.wegelius.olpstudenthandler.model;
// Generated Mar 20, 2016 4:31:10 PM by Hibernate Tools 4.3.1



/**
 * MultipleChoiceAnswer generated by hbm2java
 */
public class MultipleChoiceAnswer  implements java.io.Serializable {


     private Integer multipleChoiceAnswerId;
     private MultipleChoiceOptions multipleChoiceOptions;
     private MultipleChoiceQuestion multipleChoiceQuestion;
     private String mcaExplanation;

    public MultipleChoiceAnswer() {
    }

	
    public MultipleChoiceAnswer(MultipleChoiceOptions multipleChoiceOptions, MultipleChoiceQuestion multipleChoiceQuestion) {
        this.multipleChoiceOptions = multipleChoiceOptions;
        this.multipleChoiceQuestion = multipleChoiceQuestion;
    }
    public MultipleChoiceAnswer(MultipleChoiceOptions multipleChoiceOptions, MultipleChoiceQuestion multipleChoiceQuestion, String mcaExplanation) {
       this.multipleChoiceOptions = multipleChoiceOptions;
       this.multipleChoiceQuestion = multipleChoiceQuestion;
       this.mcaExplanation = mcaExplanation;
    }
   
    public Integer getMultipleChoiceAnswerId() {
        return this.multipleChoiceAnswerId;
    }
    
    public void setMultipleChoiceAnswerId(Integer multipleChoiceAnswerId) {
        this.multipleChoiceAnswerId = multipleChoiceAnswerId;
    }
    public MultipleChoiceOptions getMultipleChoiceOptions() {
        return this.multipleChoiceOptions;
    }
    
    public void setMultipleChoiceOptions(MultipleChoiceOptions multipleChoiceOptions) {
        this.multipleChoiceOptions = multipleChoiceOptions;
    }
    public MultipleChoiceQuestion getMultipleChoiceQuestion() {
        return this.multipleChoiceQuestion;
    }
    
    public void setMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.multipleChoiceQuestion = multipleChoiceQuestion;
    }
    public String getMcaExplanation() {
        return this.mcaExplanation;
    }
    
    public void setMcaExplanation(String mcaExplanation) {
        this.mcaExplanation = mcaExplanation;
    }




}


