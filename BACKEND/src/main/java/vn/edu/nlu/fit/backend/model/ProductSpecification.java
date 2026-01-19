package vn.edu.nlu.fit.backend.model;

public class ProductSpecification {
    private int id;
    private int productId;
    private String specName;
    private String specValue;

    public ProductSpecification(int id, int productId, String specName, String specValue) {
        this.id = id;
        this.productId = productId;
        this.specName = specName;
        this.specValue = specValue;
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public String getSpecName() { return specName; }
    public String getSpecValue() { return specValue; }
}
