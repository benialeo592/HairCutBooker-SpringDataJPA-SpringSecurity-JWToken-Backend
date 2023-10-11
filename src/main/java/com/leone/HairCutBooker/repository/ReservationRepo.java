package com.leone.HairCutBooker.repository;

import com.leone.HairCutBooker.model.Performance;
import com.leone.HairCutBooker.model.Reservation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(long id);

    List<Reservation> findAllByBookingDateLessThanEqual(LocalDate bookingDate, Sort sort);
}
