package vn.edu.nlu.fit.backend.model;

public class ReviewSummary {
    private int totalReviews;
    private double avgRating;

    public ReviewSummary(int totalReviews, double avgRating) {
        this.totalReviews = totalReviews;
        this.avgRating = avgRating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public double getAvgRating() {
        return avgRating;
    }
}
