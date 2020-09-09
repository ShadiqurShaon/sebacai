package com.example.medicine;

public class SpecificCategoryModel {
    private int diImage;
    private int doctorId;
    private String drName;
    private String drCategory;
    private String drEduQualification;
    private String designation;
    private String institute;
    private String consultantFree;

    public SpecificCategoryModel() {
    }

    public SpecificCategoryModel(int diImage, String drName, String drCategory,
                                 String drEduQualification, String designation,
                                 String institute, String consultantFree,int doctorId) {
        this.diImage = diImage;
        this.drName = drName;
        this.drCategory = drCategory;
        this.drEduQualification = drEduQualification;
        this.designation = designation;
        this.institute = institute;
        this.consultantFree = consultantFree;
        this.doctorId = doctorId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getDiImage() {
        return diImage;
    }

    public void setDiImage(int diImage) {
        this.diImage = diImage;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrCategory() {
        return drCategory;
    }

    public void setDrCategory(String drCategory) {
        this.drCategory = drCategory;
    }

    public String getDrEduQualification() {
        return drEduQualification;
    }

    public void setDrEduQualification(String drEduQualification) {
        this.drEduQualification = drEduQualification;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getConsultantFree() {
        return consultantFree;
    }

    public void setConsultantFree(String consultantFree) {
        this.consultantFree = consultantFree;
    }
}
