package com.akeel.library.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatronDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;


    @NotBlank(message = "Contact info is mandatory")
    @Size(max = 100, message = "Contact info should not exceed 100 characters")
    private String contactInfo;

    public String getName() {
        return name;
    }

    public PatronDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public PatronDto setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
        return this;
    }

    public Long getId() {
        return id;
    }

    public PatronDto setId(Long id) {
        this.id = id;
        return this;
    }
}
