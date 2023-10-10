package com.leone.HairCutBooker.controller.admin;

import com.leone.HairCutBooker.DTO.PerformanceDTORequest;
import com.leone.HairCutBooker.DTO.ReservationDTORequest;
import com.leone.HairCutBooker.DTO.ReservationDTOResponse;
import com.leone.HairCutBooker.model.Performance;
import com.leone.HairCutBooker.service.ReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTOResponse>>getAll(){
        return ResponseEntity.ok().body(this.reservationService.all());
    }

    @PostMapping("/reservation/{userId}")
    public ResponseEntity<ReservationDTOResponse> storeReservation(@RequestBody @Valid ReservationDTORequest reservationRequest, @PathVariable Long userId ){
        return ResponseEntity.ok().body(this.reservationService.createByAdmin(reservationRequest, userId));
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<ReservationDTOResponse> updateReservation(@RequestBody @Valid ReservationDTORequest reservationRequest, @PathVariable Long id){
        return ResponseEntity.ok().body(this.reservationService.update(reservationRequest, id));
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id){
        return ResponseEntity.ok().body(this.reservationService.destroy(id));
    }
    @PostMapping("/reservation/{idReservation}/performance/{idPerformance}/add")
    public ResponseEntity<ReservationDTOResponse> addPerformanceToReservation(@PathVariable Long idReservation, @PathVariable Long idPerformance){
        return ResponseEntity.ok().body(this.reservationService.addPerformanceToExistingReservation(idReservation, idPerformance));
    }

    @DeleteMapping("/reservation/{idReservation}/performance/{idPerformance}/remove")
    public ResponseEntity<ReservationDTOResponse> removePerformanceToReservation(@PathVariable Long idReservation, @PathVariable Long idPerformance){
        return ResponseEntity.ok().body(this.reservationService.removePerformanceFromExistingReservation(idReservation, idPerformance));
    }
}
