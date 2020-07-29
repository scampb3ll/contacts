package com.singlestoneconsulting.contacts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactAddress {

    @NotNull
    @NotBlank
    private String street;
    @NotNull
    @NotBlank
    private String city;
    @NotNull
    @NotBlank
 //   @Size(min = 2,max = 2)
    private String state;
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$") //allow 5 or 9 digit with or without dash
    private String zip;
}
