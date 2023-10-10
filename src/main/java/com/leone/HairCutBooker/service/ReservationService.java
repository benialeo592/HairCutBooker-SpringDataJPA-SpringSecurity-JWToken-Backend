package com.leone.HairCutBooker.service;

import com.leone.HairCutBooker.DTO.PerformanceDTORequest;
import com.leone.HairCutBooker.DTO.ReservationDTORequest;
import com.leone.HairCutBooker.DTO.ReservationDTOResponse;
import com.leone.HairCutBooker.exception.ResourceNotFoundException;
import com.leone.HairCutBooker.model.Performance;
import com.leone.HairCutBooker.model.Reservation;
import com.leone.HairCutBooker.model.User;
import com.leone.HairCutBooker.repository.PerformanceRepo;
import com.leone.HairCutBooker.repository.ReservationRepo;
import com.leone.HairCutBooker.repository.UserRepo;
import com.leone.HairCutBooker.security.jwt.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private ReservationRepo reservationRepo;
    private UserRepo userRepo;
    private PerformanceRepo performanceRepo;
    private JwtUtil jwtUtil;

    public List<ReservationDTOResponse> all(){
        List<Reservation> list = reservationRepo.findAll();
        if(list.isEmpty()){
            throw new ResourceNotFoundException("No reservations found");
        }
        return list.stream()
                .map(this::convertModelToReservationDTOResponse)
                .collect(Collectors.toList());
    }

    public ReservationDTOResponse create(ReservationDTORequest reservation, String token){
        String email = this.jwtUtil.extractUsername(token);
        Optional<User> foundUser = this.userRepo.findByEmail(email);
        if(foundUser.isEmpty()){
            throw new EntityNotFoundException("User doesn't'exist");
        }
        Reservation reservationToStore = this.convertToReservationModel(reservation);
        foundUser.get().getReservations().add(reservationToStore);
        this.userRepo.save(foundUser.get());
        return this.convertModelToReservationDTOResponse(reservationToStore);
    }

    public ReservationDTOResponse createByAdmin(ReservationDTORequest reservation, Long id){
        Optional<User> foundUser = userRepo.findById(id);
        if(foundUser.isEmpty()){
            throw new EntityNotFoundException("User doesn't'exist");
        }
        reservation.setUser(foundUser.get());
        Reservation savedReservation = this.reservationRepo.save(this.convertToReservationModel(reservation));
        return this.convertModelToReservationDTOResponse(savedReservation);
    }

    public ReservationDTOResponse update(ReservationDTORequest reservation, Long id){
        Optional<Reservation> foundReservation = reservationRepo.findById(id);
        if(foundReservation.isEmpty()){
            throw new EntityNotFoundException("Reservation not found");
        }
        foundReservation.get().setPerformanceDate(reservation.getPerformanceDate());
        foundReservation.get().setBookingDate(reservation.getBookingDate());
        foundReservation.get().setBookingStatus(reservation.getBookingStatus());
        Reservation updatedReservation = this.reservationRepo.save(foundReservation.get());
        return this.convertModelToReservationDTOResponse(updatedReservation);
    }

    public String destroy(Long id){
        Optional<Reservation> foundReservation = reservationRepo.findById(id);
        if(foundReservation.isEmpty()){
            throw new EntityNotFoundException("Reservation not found");
        }
        this.reservationRepo.delete(foundReservation.get());
        return "Reservation n." + id + " is deleted";
    }

    public ReservationDTOResponse addPerformanceToExistingReservation(Long idReservation, Long idPerformance){
        Optional<Reservation> foundReservation = reservationRepo.findById(idReservation);
        Optional<Performance> foundPerformance = performanceRepo.findById(idPerformance);
        if(foundReservation.isEmpty() || foundPerformance.isEmpty()){
            throw new EntityNotFoundException("Not found");
        }
        foundReservation.get().getPerformances().add(foundPerformance.get());
        Reservation reservation = this.reservationRepo.save(foundReservation.get());
        return this.convertModelToReservationDTOResponse(reservation);
    }

    public ReservationDTOResponse removePerformanceFromExistingReservation(Long idReservation, Long idPerformance){
        Optional<Reservation> foundReservation = reservationRepo.findById(idReservation);
        Optional<Performance> foundPerformance = performanceRepo.findById(idPerformance);
        if(foundReservation.isEmpty() || foundPerformance.isEmpty()){
            throw new EntityNotFoundException("Not found");
        }
        Optional<Performance> foundPerformanceInReservationfound = foundReservation.get().getPerformances()
                .stream().filter(performance -> performance.getId().equals(foundPerformance.get().getId())).findFirst();
        if(foundPerformanceInReservationfound.isEmpty()){
            throw new ResourceNotFoundException("The perfomance is not in the reservation");
        }
            foundReservation.get().getPerformances().remove(foundPerformanceInReservationfound.get());
            Reservation savedReservation = reservationRepo.save(foundReservation.get());
            return this.convertModelToReservationDTOResponse(savedReservation);
    }



    private Reservation convertToReservationModel(ReservationDTORequest request){
        Reservation reservation = new Reservation();
        reservation.setBookingDate(request.getBookingDate());
        reservation.setPerformanceDate(request.getPerformanceDate());
        reservation.setUser(request.getUser());
        reservation.setBookingStatus(request.getBookingStatus());
        reservation.setPerformances(request.getPerformances());
        return reservation;
    }

    private ReservationDTOResponse convertModelToReservationDTOResponse(Reservation reservation){
        ReservationDTOResponse response = new ReservationDTOResponse();
        response.setId(reservation.getId());
        response.setBookingDate(reservation.getBookingDate());
        response.setPerformanceDate(reservation.getPerformanceDate());
        response.setUserEmail(reservation.getUser().getEmail());
        response.setPerformances(reservation.getPerformances());
        response.setBookingStatus(reservation.getBookingStatus());
        return response;
    }
}
