package pe.business.app.users.service;

import pe.business.app.users.model.UserRq;
import pe.business.app.users.model.UserRs;

import java.util.List;


public interface UsersService {

    public UserRs createUser(UserRq userRq);

    public void updateUser(String email, String token);
    public List<UserRs> findUsers( );

}
