package ir.ac.kntu.models;

import java.util.Objects;

public class Feedback {
    private Rating rating;

    private String comment;

    public Feedback(Rating rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        return rating == feedback.rating && comment.equals(feedback.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, comment);
    }

    @Override
    public String toString() {
        return "{rating=" + rating.getValue() +
                ", comment='" + comment + '\'' + "}";
    }
}