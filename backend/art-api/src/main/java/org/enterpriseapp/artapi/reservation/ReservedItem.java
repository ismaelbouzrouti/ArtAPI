package org.enterpriseapp.artapi.reservation;

import jakarta.persistence.*;
import org.enterpriseapp.artapi.products.Product;

@Entity
public class ReservedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private double price;

    private int quantity;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation shoppingCart) {
        this.reservation = shoppingCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
