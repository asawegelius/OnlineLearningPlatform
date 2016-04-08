package se.wegelius.olpstudenthandler.model;

public class MultipleChoiceOptions  implements java.io.Serializable {


     private Integer multipleChoiceOptionsId;
     private MultipleChoiceQuestion multipleChoiceQuestion;
     private String mcoSequence;
     private String mcoTheOption;

    public MultipleChoiceOptions() {
    }

    public MultipleChoiceOptions(MultipleChoiceQuestion multipleChoiceQuestion, String mcoSequence, String mcoTheOption) {
       this.multipleChoiceQuestion = multipleChoiceQuestion;
       this.mcoSequence = mcoSequence;
       this.mcoTheOption = mcoTheOption;
    }
   
    public Integer getMultipleChoiceOptionsId() {
        return this.multipleChoiceOptionsId;
    }
    
    public void setMultipleChoiceOptionsId(Integer multipleChoiceOptionsId) {
        this.multipleChoiceOptionsId = multipleChoiceOptionsId;
    }
    public MultipleChoiceQuestion getMultipleChoiceQuestion() {
        return this.multipleChoiceQuestion;
    }
    
    public void setMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.multipleChoiceQuestion = multipleChoiceQuestion;
    }
    public String getMcoSequence() {
        return this.mcoSequence;
    }
    
    public void setMcoSequence(String mcoSequence) {
        this.mcoSequence = mcoSequence;
    }
    public String getMcoTheOption() {
        return this.mcoTheOption;
    }
    
    public void setMcoTheOption(String mcoTheOption) {
        this.mcoTheOption = mcoTheOption;
    }


}


