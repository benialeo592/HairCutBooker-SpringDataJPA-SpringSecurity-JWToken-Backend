package com.leone.HairCutBooker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.leone.HairCutBooker.model.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="user")
    @JsonManagedReference
    private List<Reservation> reservations;




}
