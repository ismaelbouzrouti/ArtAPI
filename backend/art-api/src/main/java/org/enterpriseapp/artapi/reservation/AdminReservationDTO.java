package org.enterpriseapp.artapi.reservation;

import java.util.List;

public class AdminReservationDTO {

    private List<ReservedItemDTO> reservedItems;
    private int quantity;
    private double totalPrice;
    private String returnDate;
    private String dateOfReservation;
    private String userName;

    public AdminReservationDTO(List<ReservedItemDTO> reservedItems, int quantity, double totalPrice, String returnDate, String dateOfReservation, String userName) {
        this.reservedItems = reservedItems;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.returnDate = returnDate;
        this.dateOfReservation = dateOfReservation;
        this.userName = userName;
    }


    public List<ReservedItemDTO> getReservedItems() {
        return reservedItems;
    }

    public void setReservedItems(List<ReservedItemDTO> reservedItems) {
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

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(String dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
