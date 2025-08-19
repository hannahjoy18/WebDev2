package org.example;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    private String licensePlateNumber;
    private String make;
    private String model;
    private int year;
    private String color;
    private String bodyType;
    private String engineType;
    private String transmission;
}

