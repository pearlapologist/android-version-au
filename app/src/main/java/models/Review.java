package models;

import java.util.ArrayList;

public class Review {
    private  int id;
    private int executrId;
    private int customerId; //personId
    private String review_text;
    private int  assessment;
    private Long createdDate;
    private ArrayList<Answer> answers;

    public Review(int id, int executrId, int customerId,  String review_text, int assessment) {
        this.id = id;
        this.executrId = executrId;
        this.customerId = customerId;
        this.review_text = review_text;
        this.assessment = assessment;
        answers = new ArrayList<>();
    }

    public Review(int executrId, int customerId,  String review_text, int assessment) {
        this.executrId = executrId;
        this.customerId = customerId;
        this.review_text = review_text;
        this.assessment = assessment;
        this.createdDate = MyUtils.getCurentDateInLong();
        answers = new ArrayList<>();
    }


    public Review(){
    }
    public int getExecutrId() {
        return executrId;
    }

    public void setExecutrId(int executrId) {
        this.executrId = executrId;
    }

    public int getPersonId() {
        return customerId;
    }

    public void setPersonId(int personId) {
        this.customerId = personId;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String text) {
        this.review_text = text;
    }

    public int getAssessment() {
        return assessment;
    }

    public void setAssessment(int assessment) {
        this.assessment = assessment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
