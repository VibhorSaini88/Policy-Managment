package com.epam.system.policy_management_core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class    PolicyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "first name is required")
    private String firstName;

    @JsonProperty("lName")
    private String lastName;

    @Pattern(
            regexp = "^[a-zA-Z]+[0-9]{0,4}@epam\\.ai$",
            message = "Invalid email format"
    )
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 5, message = "Minimum age is 5 years")
    @Max(value = 30, message = "Maximum age allowed is 30 years")
    private Integer age;

    @Pattern(
            regexp = "^\\+?[91]?[0-9]{10}$",
            message = "Invalid mobile number"
    )
    private String mobileNumber;
}
