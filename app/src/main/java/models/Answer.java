package models;

public class Answer {
    private  int id;
    int reviewId;
    private int executorId;
    private int customerId; //personId
    private String text;
    private Long createdDate;


    public Answer(int id, int reviewId, int executorId, int customerId, String review_text, Long createdDate) {
        this.id = id;
        this.reviewId = reviewId;
        this.executorId = executorId;
        this.customerId = customerId;
        this.text = review_text;
        this.createdDate = createdDate;
    }

    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
}
