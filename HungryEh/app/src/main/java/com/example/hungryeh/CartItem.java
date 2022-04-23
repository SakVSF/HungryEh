package com.example.hungryeh;

public class CartItem {
    //define the cartitem class with all the attributes needed to be displayed in the cardview of the recyclerview
        String dishName;
        String img;
        int quantity;
        double totalprice;
        String orderTime;

        public CartItem() {
        }

        public CartItem(String dishName, String img, int quantity, double totalprice, String orderTime) {
            this.dishName = dishName;
            this.img = img;
            this.quantity = quantity;
            this.totalprice = totalprice;
            this.orderTime = orderTime;
        }

        public String getDishName() {
            return dishName;
        }

        public String getImg() {
            return img;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotalPrice() {
            return totalprice;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setDishName(String dishName) {
            this.dishName = dishName;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setTotalPrice(double totalprice) {
            this.totalprice = totalprice;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }
    }


