package vn.edu.nlu.fit.backend.model;

public class Address {
    private int id;
    private int userId;
    private String addressDetail;
    private String city;
    private String district;
    private String ward;

    public Address() {}

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getWard() {
        return ward;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}