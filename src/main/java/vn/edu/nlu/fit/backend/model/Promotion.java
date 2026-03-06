package vn.edu.nlu.fit.backend.model;

import java.time.LocalDateTime;

public class Promotion {

    private int id;
    private int productId;
    private int discountPercent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion() {
    }

    public Promotion(int id, int productId, int discountPercent,
                     LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.productId = productId;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /* ===== Helper ===== */
    public boolean isActive(LocalDateTime now) {
        return (startDate == null || !now.isBefore(startDate))
                && (endDate == null || !now.isAfter(endDate));
    }
}
