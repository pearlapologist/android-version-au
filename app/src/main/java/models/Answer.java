package models;

public class Answer {
    private  int id;
    int reviewId;
    private int whoanswersId;
    private int whopostedId; //personId
    private String text;
    private Long createdDate;


    public Answer(int id, int reviewId, int whoanswersId, int whopostedId, String review_text, Long createdDate) {
        this.id = id;
        this.reviewId = reviewId;
        this.whoanswersId = whoanswersId;
        this.whopostedId = whopostedId;
        this.text = review_text;
        this.createdDate = createdDate;
    }

    public Answer() {
    }

    public Answer(int reviewId, int whoanswersId, int whopostedId, String text, Long createdDate) {
        this.reviewId = reviewId;
        this.whoanswersId = whoanswersId;
        this.whopostedId = whopostedId;
        this.text = text;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWhoanswersId() {
        return whoanswersId;
    }

    public void setWhoanswersId(int whoanswersId) {
        this.whoanswersId = whoanswersId;
    }

    public int getWhopostedId() {
        return whopostedId;
    }

    public void setWhopostedId(int whopostedId) {
        this.whopostedId = whopostedId;
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
