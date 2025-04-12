package pe.business.app.users.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRs {


 public String id;
 public Date created;
 public Date modified;
 public Date lastLogin;

 @JsonInclude(JsonInclude.Include.NON_NULL)
 public String token;

 public boolean active;

 @JsonInclude(JsonInclude.Include.NON_NULL)
 public List<PhoneRs> phones;
}
