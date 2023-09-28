package com.leone.HairCutBooker.DTO;

import com.leone.HairCutBooker.model.Performance;
import com.leone.HairCutBooker.model.User;
import com.leone.HairCutBooker.model.enumeration.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDTORequest {

    @DateTimeFormat
    private LocalDate bookingDate = LocalDate.now();;
    @DateTimeFormat
    @NotNull
    private LocalDateTime performanceDate;
    private User user;
    private Set<Performance> performances;
    private BookingStatus bookingStatus = BookingStatus.PENDING;
}
