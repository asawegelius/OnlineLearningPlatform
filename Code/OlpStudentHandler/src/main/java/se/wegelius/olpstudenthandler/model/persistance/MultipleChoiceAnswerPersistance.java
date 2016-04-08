package se.wegelius.olpstudenthandler.model.persistance;
// Generated Apr 7, 2016 10:41:12 PM by Hibernate Tools 4.3.1



/**
 * MultipleChoiceAnswerPersistance generated by hbm2java
 */
public class MultipleChoiceAnswerPersistance  implements java.io.Serializable {


     private Integer multipleChoiceAnswerId;
     private MultipleChoiceOptionsPersistance multipleChoiceOptions;
     private MultipleChoiceQuestionPersistance multipleChoiceQuestion;
     private String mcaExplanation;

    public MultipleChoiceAnswerPersistance() {
    }

	
    public MultipleChoiceAnswerPersistance(MultipleChoiceOptionsPersistance multipleChoiceOptions, MultipleChoiceQuestionPersistance multipleChoiceQuestion) {
        this.multipleChoiceOptions = multipleChoiceOptions;
        this.multipleChoiceQuestion = multipleChoiceQuestion;
    }
    public MultipleChoiceAnswerPersistance(MultipleChoiceOptionsPersistance multipleChoiceOptions, MultipleChoiceQuestionPersistance multipleChoiceQuestion, String mcaExplanation) {
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
    public MultipleChoiceOptionsPersistance getMultipleChoiceOptions() {
        return this.multipleChoiceOptions;
    }
    
    public void setMultipleChoiceOptions(MultipleChoiceOptionsPersistance multipleChoiceOptions) {
        this.multipleChoiceOptions = multipleChoiceOptions;
    }
    public MultipleChoiceQuestionPersistance getMultipleChoiceQuestion() {
        return this.multipleChoiceQuestion;
    }
    
    public void setMultipleChoiceQuestion(MultipleChoiceQuestionPersistance multipleChoiceQuestion) {
        this.multipleChoiceQuestion = multipleChoiceQuestion;
    }
    public String getMcaExplanation() {
        return this.mcaExplanation;
    }
    
    public void setMcaExplanation(String mcaExplanation) {
        this.mcaExplanation = mcaExplanation;
    }




}


