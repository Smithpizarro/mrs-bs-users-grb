package pe.business.app.users.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRq {

 public int number;
 public int citycode;
 public int countrycode;

}
