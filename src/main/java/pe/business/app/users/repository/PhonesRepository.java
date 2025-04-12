package pe.business.app.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.business.app.users.repository.entity.Phone;

import java.util.List;


@Repository
public interface PhonesRepository extends JpaRepository<Phone,Long> {

    List<Phone> findByUserId(String userId);

}
