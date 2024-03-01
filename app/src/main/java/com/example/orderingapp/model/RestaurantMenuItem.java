package com.example.orderingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantMenuItem implements Parcelable { //Rename u Drink

    private Long id;
    private String name;
    private double price;

    public RestaurantMenuItem(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RestaurantMenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
    protected RestaurantMenuItem(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        id = in.readLong();
    }

    public static final Creator<RestaurantMenuItem> CREATOR = new Creator<RestaurantMenuItem>() {
        @Override
        public RestaurantMenuItem createFromParcel(Parcel in) {
            return new RestaurantMenuItem(in);
        }

        @Override
        public RestaurantMenuItem[] newArray(int size) {
            return new RestaurantMenuItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeLong(id);
    }
}

