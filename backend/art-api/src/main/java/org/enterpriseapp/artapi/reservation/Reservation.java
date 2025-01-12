package org.enterpriseapp.artapi.reservation;

import jakarta.persistence.*;
import org.enterpriseapp.artapi.reservation.ReservedItem;
import org.enterpriseapp.artapi.users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //a user can have many reservations
    //reservation holds the foreign key user_id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //a reservation can hold many reservedItems a reserved item can only belong to one reservation
    //if a reservation is deleted // all the reserved items belonging to it will also get deleted
    //when a reserved item gest removed from the reservation it is also deleted from the db
    @OneToMany(mappedBy = "reservation",cascade = CascadeType.ALL, orphanRemoval = true)
    List<ReservedItem> reservedItems = new ArrayList<>();

    private int quantity;

    private double totalPrice;

    private LocalDate returnDate;

    private LocalDate dateOfReservation = LocalDate.now();


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

    public List<ReservedItem> getReservedItems() {
        return reservedItems;
    }

    public void setReservedItems(List<ReservedItem> reservedItems) {
        this.reservedItems = reservedItems;
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

    public LocalDate getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(LocalDate dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }
}
