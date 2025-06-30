/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dell-PC
 */
public class SubCategory {
private int id;
    private String subCategoryName;
    private CategoryMaterial categoryMaterialId;

    public SubCategory() {
    }

    public SubCategory(int id, String subCategoryName, CategoryMaterial categoryMaterialId) {
        this.id = id;
        this.subCategoryName = subCategoryName;
        this.categoryMaterialId = categoryMaterialId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public CategoryMaterial getCategoryMaterialId() {
        return categoryMaterialId;
    }

    public void setCategoryMaterialId(CategoryMaterial categoryMaterialId) {
        this.categoryMaterialId = categoryMaterialId;
    }
}
