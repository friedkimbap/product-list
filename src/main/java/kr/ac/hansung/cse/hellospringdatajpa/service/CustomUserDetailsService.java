package kr.ac.hansung.cse.hellospringdatajpa.service;

import java.util.Collection;
import kr.ac.hansung.cse.hellospringdatajpa.entity.MyUser;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    MyUser myUserEntity = userRepository.findByEmail(username).
        orElseThrow(()->new UsernameNotFoundException("Email : "+ username +" not found"));

    return new org.springframework.security.core.userdetails.User(myUserEntity.getEmail(),
        myUserEntity.getPassword(), getAuthorities(myUserEntity));
  }

  private static Collection<? extends GrantedAuthority> getAuthorities(MyUser user)
  {
    String[] userRoles = user.getRoles()
        .stream()
        .map((role) -> role.getRoleName())
        .toArray(String[]::new);

    Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
    return authorities;
  }
}
