package com.example.hungryeh;

public class CartItem {
        String dishName;
        String img;
        int quantity;
        double totalPrice;
        String orderTime;

        public CartItem() {
        }

        public CartItem(String dishName, String img, int quantity, double totalPrice, String orderTime) {
            this.dishName = dishName;
            this.img = img;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
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
            return totalPrice;
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

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }
    }


