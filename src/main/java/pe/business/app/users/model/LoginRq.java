package pe.business.app.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRq {

 public String email;
 public String password;

}
