package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.laptopshop.domain.Role;
import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Role findByName(String name);
}
