package pe.business.app.users.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.business.app.users.config.JwtTokenUtil;
import pe.business.app.users.controller.exception.ServiceException;
import pe.business.app.users.model.PhoneRq;
import pe.business.app.users.model.PhoneRs;
import pe.business.app.users.model.UserRq;
import pe.business.app.users.model.UserRs;
import pe.business.app.users.repository.PhonesRepository;
import pe.business.app.users.repository.UsersRepository;
import pe.business.app.users.repository.entity.Phone;
import pe.business.app.users.repository.entity.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {


    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PhonesRepository phonesRepository;

    @Autowired
    PasswordEncoder bcryptEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService jwtInMemoryUserDetailsService;

    public static final String CODE_EXIST_EMAIL="7005";
    @Override
    public UserRs createUser(UserRq studentRq) {



        User userFind = usersRepository.findByEmail(studentRq.getEmail());
         if (userFind!=null){
            throw new ServiceException(CODE_EXIST_EMAIL);
        }
        UserRs userRs = new UserRs();
        User user = new User();
        user.setName(studentRq.getName());
        user.setEmail(studentRq.getEmail());
        user.setPassword(bcryptEncoder.encode(studentRq.getPassword()));

        user.setActive(true);
        user.setCreatedBy("ADMIN");
        user = usersRepository.save(user);

        for (PhoneRq phoneRq : studentRq.getPhones()) {
          Phone phone = new Phone();
          BeanUtils.copyProperties(phoneRq,phone);
          phone.setUser(user);
          phone = phonesRepository.save(phone);
        }
        userRs.setId(user.getId());
        userRs.setActive(user.isActive());
        userRs.setCreated(user.getCreatedDate());
        userRs.setModified(user.getUpdatedDate());
        userRs.setLastLogin(user.getLastLogin());
        return userRs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRs> findUsers( ) {

        List<UserRs> studentListRs = new ArrayList<>();

        List<User> userList = null;
        List<Phone> phoneList = null;
        userList = usersRepository.findAll();

        for (User user : userList) {
            UserRs userRs = new UserRs();
            userRs.setId(user.getId());
            userRs.setActive(user.isActive());
            userRs.setCreated(user.getCreatedDate());
            userRs.setModified(user.getUpdatedDate());
            userRs.setLastLogin(user.getLastLogin());
            userRs.setToken(user.getToken());
            List<PhoneRs> phoneListRs = new ArrayList<>();
            phoneList = phonesRepository.findByUserId(user.getId());
            for (Phone phone : phoneList) {
                PhoneRs phoneRs = new PhoneRs();
                BeanUtils.copyProperties(phone,phoneRs);
                phoneListRs.add(phoneRs) ;
            }
            userRs.setPhones(phoneListRs);
            studentListRs.add(userRs);
        }
        return studentListRs;
    }

    @Override
    public void updateUser(String email, String token){

        User userFind = usersRepository.findByEmail(email);
        userFind.setToken(token);
        usersRepository.save(userFind);

    }
}
