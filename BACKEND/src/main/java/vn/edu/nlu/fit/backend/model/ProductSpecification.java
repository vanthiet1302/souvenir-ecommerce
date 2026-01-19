package vn.edu.nlu.fit.backend.model;

public class ProductSpecification {

    private int id;
    private int productId;
    private String specName;
    private String specValue;

    public ProductSpecification() {
    }
    public ProductSpecification(int id, int productId, String specName, String specValue) {
        this.id = id;
        this.productId = productId;
        this.specName = specName;
        this.specValue = specValue;
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

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    @Override
    public String toString() {
        return specName + ": " + specValue;
    }
}
