package pe.business.app.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.business.app.users.repository.entity.User;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {

    User findByEmail(String emails);

    User findByEmailAndPassword(String email, String password);
}
