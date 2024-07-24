package uz.adliya.integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.adliya.integration.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUsername(String username);

}
