package org.enterpriseapp.artapi.shoppingCart;

import jakarta.persistence.*;
import org.enterpriseapp.artapi.users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment id
    private Long id;

// a user has a shopping cart and a shopping cart belongs to one user
    //shopping cart holds the foreign key user_id
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // a shopping cart can have many cartItems
    //if a shopping cart gets deleted its items get deleted as well
    //if a cartItem gets removed from the shopping cart it also gets deleted in the db
    @OneToMany(mappedBy = "shoppingCart",cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItem> cartItems = new ArrayList<>();

    private int quantity;

    private double totalPrice;

    private LocalDate returnDate;


    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
