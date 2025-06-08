package kr.ac.hansung.cse.hellospringdatajpa.repo;

import java.util.Optional;
import kr.ac.hansung.cse.hellospringdatajpa.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
  Optional<MyUser> findByEmail(String email);
}
