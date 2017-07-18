package thozhilali.com.thozhilali;

/**
 * Created by SEMI on 4/30/2017.
 */

public class ReviewData {
    int id;
    String review,reviewed_by,reviewed_to,date;

    public ReviewData(String review, String reviewed_by, String date) {
        this.review = review;
        this.reviewed_by = reviewed_by;
        this.date = date;
    }

    public ReviewData(int id, String review, String reviewed_by, String reviewed_to, String date) {
        this.id = id;
        this.review = review;
        this.reviewed_by = reviewed_by;
        this.reviewed_to = reviewed_to;
        this.date = date;
    }

    public ReviewData() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewed_by() {
        return reviewed_by;
    }

    public void setReviewed_by(String reviewed_by) {
        this.reviewed_by = reviewed_by;
    }

    public String getReviewed_to() {
        return reviewed_to;
    }

    public void setReviewed_to(String reviewed_to) {
        this.reviewed_to = reviewed_to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
