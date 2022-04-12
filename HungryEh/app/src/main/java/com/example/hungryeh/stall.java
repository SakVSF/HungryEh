package com.example.hungryeh;

import android.os.Parcel;
import android.os.Parcelable;

public class stall implements Parcelable {
    String allergens;
    String dishName;
    String price;
    String stallName;
    String veg;
    String img;


    stall() {
    }

    protected stall(Parcel in) {
        allergens = in.readString();
        dishName = in.readString();
        price = in.readString();
        stallName = in.readString();
        veg = in.readString();
        img= in.readString();
    }

    public static final Creator<stall> CREATOR = new Creator<stall>() {
        @Override
        public stall createFromParcel(Parcel in) {
            return new stall(in);
        }

        @Override
        public stall[] newArray(int size) {
            return new stall[size];
        }
    };

    public String getAllergens() {
        return allergens;
    }

    public String getDishName() {
        return dishName;
    }

    public String getPrice() {
        return price;
    }

    public String getStallName() {
        return stallName;
    }

    public String getVeg() {
        return veg;
    }

    public String getImg(){
        return img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(allergens);
        parcel.writeString(dishName);
        parcel.writeString(price);
        parcel.writeString(stallName);
        parcel.writeString(veg);
        parcel.writeString(img);
    }
}
