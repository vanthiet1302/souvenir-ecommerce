package vn.edu.nlu.fit.backend.model;

public class Product {

    private int id;
    private Integer categoryId;
    private String categoryName;
    private String name;
    private String description;
    private String shortDescription;
    private double originalPrice;
    private int discountPercent = 0;  // % giảm giá - mặc định 0
    private Double salePrice;         // Giá sau giảm
    private String image;
    private int stockQuantity;
    private int totalSold;
    private double avgRating;
    private int reviewCount;

    public Product() {
    }

    public Product(int id, Integer categoryId, String name, String description,
                   double originalPrice, String image, int stockQuantity,
                   int totalSold, double avgRating, int reviewCount) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.originalPrice = originalPrice;
        this.image = image;
        this.stockQuantity = stockQuantity;
        this.totalSold = totalSold;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }


    public double getPrice() {
        return originalPrice;
    }

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public boolean hasDiscount() {
        return discountPercent > 0 && salePrice != null;
    }
}
