package com.akeel.library.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Contact info is mandatory")
    @Size(max = 100, message = "Contact info should not exceed 100 characters")
    private String contactInfo;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    // Getters and setters

    public String getName() {
        return name;
    }

    public Patron setName(String name) {
        this.name = name;
        return this;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public Patron setContactInfo(String contactInformation) {
        this.contactInfo = contactInformation;
        return this;
    }
}