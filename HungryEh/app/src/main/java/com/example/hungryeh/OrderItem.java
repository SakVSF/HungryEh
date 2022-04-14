package com.example.hungryeh;

import com.google.type.DateTime;

import java.util.ArrayList;

public class OrderItem {
        DateTime dateordered;
        String paylah_phone;
        String creditcard_name;
        String creditcard_number;
        String creditcard_security;
        double overalltotalprice;
        ArrayList<CartItem> cartItems;

        public OrderItem() {
        }

        public OrderItem(DateTime dateordered, String paylah_phone, String creditcard_name, String creditcard_number, String creditcard_security, double overalltotalprice, ArrayList<CartItem> cartItems) {
            this.dateordered = dateordered;
            this.paylah_phone = paylah_phone;
            this.creditcard_name = creditcard_name;
            this.creditcard_number = creditcard_number;
            this.creditcard_security = creditcard_security;
            this.overalltotalprice = overalltotalprice;
            this.cartItems = cartItems;
        }

        public DateTime getDateTime() {
            return dateordered;
        }

        public String getPaylah_phone() {
            return paylah_phone;
        }

        public String getCreditcard_name() {
            return creditcard_name;
        }

        public String getCreditcard_number() {
            return creditcard_number;
        }

        public String getCreditcard_security() {
            return creditcard_security;
        }

        public double getOveralltotalprice() {
            return overalltotalprice;
        }

        public ArrayList<CartItem> getCartItems() {
            return cartItems;
        }



        public void setDateordered(DateTime dateordered) {
            this.dateordered = dateordered;
        }

        public void setPaylah_phone(String paylah_phone) {
            this.paylah_phone = paylah_phone;
        }

        public void setCreditcard_name(String creditcard_name) {
            this.creditcard_name = creditcard_name;
        }

        public void setCreditcard_number(String creditcard_number) {
            this.creditcard_number = creditcard_number;
        }

        public void setCreditcard_security(String creditcard_security) {
            this.creditcard_security = creditcard_security;
        }

        public void setOveralltotalprice(double overalltotalprice) {
            this.overalltotalprice = overalltotalprice;
        }

        public void setCartItems(ArrayList<CartItem> cartItems) {
            this.cartItems = cartItems;
        }
    }


