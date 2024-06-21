package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hoidanit);
    User findById(long id);
    void deleteById(long id);
    List<User> findByEmail(String email);
    List<User> findAll();
    boolean existsByEmail(String email);
}
