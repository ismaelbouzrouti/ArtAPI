package org.enterpriseapp.artapi.reservation;

import org.enterpriseapp.artapi.shoppingCart.CartItem;
import org.enterpriseapp.artapi.shoppingCart.ShoppingCart;
import org.enterpriseapp.artapi.shoppingCart.ShoppingCartRepository;
import org.enterpriseapp.artapi.users.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, ShoppingCartRepository shoppingCartRepository) {
        this.reservationRepository = reservationRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }


    public List<ReservationDTO> getReservations(Long userId){

        List<Reservation> reservations = reservationRepository.getReservationByUserIdOrderByDateOfReservationAsc(userId);

        if(reservations == null){
            throw new NoSuchElementException("no reservations found");
        }
        //every reservation entity in the list gets converted to a dto
      return reservations.stream()
                .map(reservation -> toDTO(reservation))
                .toList();

    }

    //convert shopping cart into reservation & clear the cart item list of the shopping cart so its empty again
    public void saveShoppingCartToReservation(ShoppingCart shoppingCart){
        Reservation reservation = new Reservation();
        reservation.setUser(shoppingCart.getUser());
        reservation.setQuantity(shoppingCart.getQuantity());
        reservation.setReturnDate(shoppingCart.getReturnDate());
        reservation.setTotalPrice(shoppingCart.getTotalPrice());

        // if the list is null in reservation is null it gets initialized
        if (reservation.getReservedItems() == null) {
            reservation.setReservedItems(new ArrayList<>());
        }

       // the cart items of the shopping cart get converted to reserved items in the reservation
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            ReservedItem reservedItem = new ReservedItem();
            reservedItem.setProduct(cartItem.getProduct());
            reservedItem.setQuantity(cartItem.getQuantity());
            reservedItem.setPrice(cartItem.getPrice());

            reservedItem.setReservation(reservation); //assign the reserved item to its reservation
            // Adding the reserved item tot the reservation list
            reservation.getReservedItems().add(reservedItem);
        }

        reservationRepository.save(reservation);
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
    }

    public List<AdminReservationDTO> getAllReservationsOrderedByUsers(){
        //create a custom sort
        //every reservation has a user_id //sort in ascending order by user(user_id)
        Sort orderBYUser = Sort.by(Sort.Direction.ASC,"user_id");

        List<Reservation> reservations = reservationRepository.findAll(orderBYUser);

        if(reservations == null){
            throw new NoSuchElementException("no reservations found");
        }

        //every reservation mapped to adminDTO
        return reservations.stream()
                .map(reservation -> toAdminReservationDTO(reservation))
                .toList();
    }



    public ReservationDTO toDTO(Reservation reservation){

        return new ReservationDTO(
                //go over every reserved item in the list
                //convert each one to a dto and return them in a list
                reservation.reservedItems.stream()
                        .map(reservedItem -> toDTOReservedItem(reservedItem))
                        .toList(),
                reservation.getQuantity(),
                reservation.getTotalPrice(),
                reservation.getReturnDate().toString(),
                reservation.getDateOfReservation().toString()
        );
    }

    public ReservedItemDTO toDTOReservedItem(ReservedItem reservedItem){

        return new ReservedItemDTO(
                reservedItem.getProduct().getName(),
                reservedItem.getPrice(),
                reservedItem.getQuantity()
        );
    }

    public AdminReservationDTO toAdminReservationDTO(Reservation reservation){

        return new AdminReservationDTO(
                reservation.reservedItems.stream()
                        .map(reservedItem -> toDTOReservedItem(reservedItem))
                        .toList(),
                reservation.getQuantity(),
                reservation.getTotalPrice(),
                reservation.getReturnDate().toString(),
                reservation.getDateOfReservation().toString(),
                reservation.getUser().getUsername()
        );
    }


}
