package com.example.hungryeh;

public class FavouriteItem {
    String dishName;
    String img;


    public FavouriteItem() {
    }

    public FavouriteItem(String dishName, String img) {
        this.dishName = dishName;
        this.img = img;

    }

    public String getDishName() {
        return dishName;
    }

    public String getImg() {
        return img;
    }



    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
