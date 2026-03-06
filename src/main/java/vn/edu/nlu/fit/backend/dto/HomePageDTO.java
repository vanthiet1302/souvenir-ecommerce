package vn.edu.nlu.fit.backend.dto;

import java.util.List;

public class HomePageDTO {

    /* ===== CATEGORY SECTIONS ===== */
    private List<HomeCategoryDTO> bannerCategories;
    private List<HomeCategoryDTO> topCategorySections;
    private List<HomeCategoryDTO> extensionSections;

    /* ===== PRODUCT CARD SECTIONS ===== */
    private List<ProductCardDTO> topRatedProductCards;
    private List<ProductCardDTO> newestProductCards;

    // ================= GETTER / SETTER =================

    public List<HomeCategoryDTO> getBannerCategories() {
        return bannerCategories;
    }

    public void setBannerCategories(List<HomeCategoryDTO> bannerCategories) {
        this.bannerCategories = bannerCategories;
    }

    public List<HomeCategoryDTO> getTopCategorySections() {
        return topCategorySections;
    }

    public void setTopCategorySections(List<HomeCategoryDTO> topCategorySections) {
        this.topCategorySections = topCategorySections;
    }

    public List<HomeCategoryDTO> getExtensionSections() {
        return extensionSections;
    }

    public void setExtensionSections(List<HomeCategoryDTO> extensionSections) {
        this.extensionSections = extensionSections;
    }

    public List<ProductCardDTO> getTopRatedProductCards() {
        return topRatedProductCards;
    }

    public void setTopRatedProductCards(List<ProductCardDTO> topRatedProductCards) {
        this.topRatedProductCards = topRatedProductCards;
    }

    public List<ProductCardDTO> getNewestProductCards() {
        return newestProductCards;
    }

    public void setNewestProductCards(List<ProductCardDTO> newestProductCards) {
        this.newestProductCards = newestProductCards;
    }
}
