package kr.ac.hansung.cse.hellospringdatajpa.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class MyRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String roleName;

  @ManyToMany(mappedBy = "roles")
  @ToString.Exclude
  private List<MyUser> users;

  public MyRole(String roleName) {
    this.roleName = roleName;
  }
}
