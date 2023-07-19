package com.example.sampleaffablebean.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter

public class Customer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NotEmpty(message = "name cannot be empty!")
    @Pattern(regexp="[A-Za-z]*",message="Name cannot contain illegal characters.")
    private String name;
    @Email(message = "Invalid Email Format!")
    private String email;

    private String phone;

    private String address;

    private int prague;

    private String creditcardnumber;
}
