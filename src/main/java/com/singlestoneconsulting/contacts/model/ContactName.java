package com.singlestoneconsulting.contacts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactName {

    @NotNull
    @NotBlank
    private String first;
    private String middle;
    @NotNull
    @NotBlank
    private String last;

}
