package com.singlestoneconsulting.contacts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactPhoneNumber {

    @Pattern(regexp = "^\\D?(\\d{3})\\D?\\D?(\\d{3})\\D?(\\d{4})$") //This RegEx requires a US phone number WITH area code. It is written to all users to enter whatever delimiters they want or no delimiters at all (i.e. 111-222-3333, or 111.222.3333, or (111) 222-3333, or 1112223333, etc...).
    private String number;
    @NotNull
    private PHONE_TYPE type;
}
