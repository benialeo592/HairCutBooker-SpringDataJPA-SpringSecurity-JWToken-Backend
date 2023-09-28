package com.leone.HairCutBooker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leone.HairCutBooker.model.enumeration.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate bookingDate = LocalDate.now();
    private LocalDateTime performanceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_user")
    @JsonBackReference
    private User user;

    @ManyToMany()
    @JoinTable(
            name = "reservation_performance",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "performance_id")
    )
    @JsonIgnore
    private Set<Performance> performances;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
