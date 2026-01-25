package vn.edu.nlu.fit.backend.dto;

import java.util.List;

public class HomePageDTO {

    // Banner slideshow
    private List<HomeCategoryDTO> bannerCategories;

    // Section category bán chạy
    private List<HomeCategoryDTO> topCategorySections;

    // Extension section
    private List<HomeCategoryDTO> extensionSections;

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
}
