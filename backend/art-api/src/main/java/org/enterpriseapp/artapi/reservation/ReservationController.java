package org.enterpriseapp.artapi.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping("/reservation/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsPerUser(@PathVariable Long userId){

        List<ReservationDTO> reservations = reservationService.getReservations(userId);

        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping("/reservations")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<AdminReservationDTO>> getAllReservations(){

        List<AdminReservationDTO> reservations = reservationService.getAllReservationsOrderedByUsers();
        System.out.println(reservations);
        return ResponseEntity.ok().body(reservations);
    }



}
