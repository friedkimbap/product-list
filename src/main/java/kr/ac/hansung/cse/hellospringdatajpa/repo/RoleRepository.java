package kr.ac.hansung.cse.hellospringdatajpa.repo;

import java.util.Optional;
import kr.ac.hansung.cse.hellospringdatajpa.entity.MyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<MyRole, Integer> {
  Optional<MyRole> findByRoleName(String roleName);
}
