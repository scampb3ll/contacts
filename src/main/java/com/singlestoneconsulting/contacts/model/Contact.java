package com.singlestoneconsulting.contacts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    private String id;
    @Valid
    private ContactName name;
    @Valid
    private ContactAddress address;
    private List<@Valid ContactPhoneNumber> phone;
    @NotNull
    @NotBlank
    private String email;

    public void addPhoneNumber(@Valid ContactPhoneNumber number){
      if(phone==null){
          phone = new ArrayList<>();
      }
      phone.add(number);
    }

}
