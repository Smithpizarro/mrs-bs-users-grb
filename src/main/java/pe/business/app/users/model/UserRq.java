package pe.business.app.users.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Setter
@Getter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRq {

 @NotNull
 @NotEmpty
 @Size(max = 50)
 public String name;


 @Email
 public String email;

 @NotNull
 @NotEmpty
 public String password;

 @NotNull
 public List<PhoneRq> phones;

}
