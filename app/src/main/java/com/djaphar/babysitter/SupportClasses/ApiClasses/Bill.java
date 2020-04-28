package com.djaphar.babysitter.SupportClasses.ApiClasses;

public class Bill {

    private String theme;
    private Kid kid;
    private Boolean status;
    private Float price;
    private Integer id;

    public Bill(String theme, Kid kid, Boolean status, Float price, Integer id) {
        this.theme = theme;
        this.kid = kid;
        this.status = status;
        this.price = price;
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public Kid getKid() {
        return kid;
    }

    public Boolean getStatus() {
        return status;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setKid(Kid kid) {
        this.kid = kid;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}