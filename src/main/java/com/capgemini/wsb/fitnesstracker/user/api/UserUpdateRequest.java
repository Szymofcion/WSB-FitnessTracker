package com.capgemini.wsb.fitnesstracker.user.api;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthdate;
}
