package com.example.orderfoodbtl.Model;

public class Cart {
    private int productId;
    private int userId;
    private int quantity;

    public Cart( int productId, int quantity, int userId) {
        this.productId = productId;
        this.quantity = quantity;
        this.userId = userId;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
