package com.leone.HairCutBooker.controller;

import com.leone.HairCutBooker.DTO.ReservationDTORequest;
import com.leone.HairCutBooker.DTO.ReservationDTOResponse;
import com.leone.HairCutBooker.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ReservationController {

    private ReservationService reservationService;

    @PostMapping("/reservation")
    public ResponseEntity<ReservationDTOResponse> storeReservation(@RequestBody ReservationDTORequest reservation, @RequestHeader (name="Authorization") String token){
        return ResponseEntity.ok().body(this.reservationService.create(reservation, token));
    }
}
