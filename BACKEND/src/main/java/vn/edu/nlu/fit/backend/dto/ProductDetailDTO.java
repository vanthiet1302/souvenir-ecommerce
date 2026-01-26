package vn.edu.nlu.fit.backend.dto;

import vn.edu.nlu.fit.backend.model.*;

import java.util.List;
import java.util.Map;

public class ProductDetailDTO {

    private Product product;
    private Category category;
    private Promotion promotion;

    private Double discountedPrice;

    private List<ProductSpecification> specifications;

    private double avgRating;
    private int totalReviews;
    private Map<Integer, Integer> ratingCount;

    private List<ProductCardDTO> relatedProductCards;

    /* ===== Getter / Setter ===== */

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public List<ProductSpecification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<ProductSpecification> specifications) {
        this.specifications = specifications;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public Map<Integer, Integer> getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Map<Integer, Integer> ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<ProductCardDTO> getRelatedProductCards() {
        return relatedProductCards;
    }

    public void setRelatedProductCards(List<ProductCardDTO> relatedProductCards) {
        this.relatedProductCards = relatedProductCards;
    }
}
