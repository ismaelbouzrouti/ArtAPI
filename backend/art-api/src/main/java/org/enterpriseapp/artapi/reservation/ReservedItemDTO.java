package org.enterpriseapp.artapi.reservation;

public class ReservedItemDTO {



    private String productName;
    private double price;
    private int quantity;

    // Constructors
    public ReservedItemDTO() {
    }

    public ReservedItemDTO(String productName, double price, int quantity) {

        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
