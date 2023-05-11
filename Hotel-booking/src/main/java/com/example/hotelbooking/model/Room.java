package com.example.hotelbooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name of the room is required")
    private String name;

    @NotNull(message = "Type of the room is required")
    private TypeOfRoom type;

    @NotNull(message = "Cost of the room is required")
    @DecimalMin(value = "0.0", message = "Cost per night must be greater than or equal to 0")
    private BigDecimal costPerNight;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> img;

    @NotBlank(message = "Url video is required")
    @URL(message = "Invalid video URL")
    private String video;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Lob
    private String description;

    @NotNull(message = "Size of room is required")
    @Min(value = 1, message = "Size must be greater than or equal to 1")
    private int size;

    private boolean isActive;

}
