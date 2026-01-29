package vn.edu.nlu.fit.backend.dto;

public class SearchSuggestionDTO {
    private int id;
    private String name;
    private Double originalPrice;
    private Double salePrice;
    private Double price; // THÊM TRƯỜNG NÀY để khớp với p.price trong JS
    private String image;

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(Double originalPrice) { this.originalPrice = originalPrice; }

    public Double getSalePrice() { return salePrice; }
    public void setSalePrice(Double salePrice) { this.salePrice = salePrice; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}