package kr.ac.hansung.cse.hellospringdatajpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.ac.hansung.cse.hellospringdatajpa.entity.MyUser;
import kr.ac.hansung.cse.hellospringdatajpa.entity.MyRole;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public MyUser createUser(MyUser user, List<MyRole> userRoles) {
    for (MyRole role : userRoles) {
      if(roleRepository.findByRoleName(role.getRoleName()).isEmpty()) {
        roleRepository.save(role);
      }
    }

    String encryptedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encryptedPassword);

    user.setRoles(userRoles);

    return userRepository.save(user);
  }

  public boolean checkEmailExists(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  public MyRole findByRoleName(String roleName) {
    Optional<MyRole> myRole = roleRepository.findByRoleName(roleName);
    return myRole.orElseGet(()-> new MyRole(roleName));
  }

  public List<MyUser> loadAllUsers() {
    return userRepository.findAll();
  }
}