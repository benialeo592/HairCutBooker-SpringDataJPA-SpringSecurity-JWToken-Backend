package com.leone.HairCutBooker.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookingStatus {
    PENDING("To be confirmed..."),
    CONFIRMED("Confirmed!"),
    CANCELLED("Cancelled by the store."),
    COMPLETED("Completed");

    private String description;

}
