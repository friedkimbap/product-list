package kr.ac.hansung.cse.hellospringdatajpa.controller;

import java.util.ArrayList;
import java.util.List;
import kr.ac.hansung.cse.hellospringdatajpa.entity.MyRole;
import kr.ac.hansung.cse.hellospringdatajpa.entity.MyUser;
import kr.ac.hansung.cse.hellospringdatajpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

  @Autowired
  private UserService userService;


  @GetMapping("/login")
  public String login(@RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout,
      @RequestParam(value = "success", required = false) String success,
      Model model) {
    if (error != null) {
      model.addAttribute("param.error", true);
    }
    if (logout != null) {
      model.addAttribute("param.logout", true);
    }
    if (success != null) {
      model.addAttribute("param.success", true);
    }
    return "login";
  }

  @GetMapping("/signup")
  public String showSignupForm(Model model) {
    model.addAttribute("myUser", new MyUser());
    return "signup";
  }

  @PostMapping("/signup")
  public String registerUser(@ModelAttribute("myUser") MyUser myUser, Model model) {
    if(userService.checkEmailExists(myUser.getEmail())) {
      model.addAttribute("emailExists", true);
      return "redirect:signup?error";
    } else {
      List<MyRole> userRoles = new ArrayList<>();

      MyRole userRole = userService.findByRoleName("ROLE_USER");
      userRoles.add(userRole);

      if("admin@hansung.ac.kr".equals(myUser.getEmail())) {
        MyRole adminRole = userService.findByRoleName("ROLE_ADMIN");
        userRoles.add(adminRole);
      }
      userService.createUser(myUser, userRoles);

      return "redirect:/";
    }
  }

  @GetMapping("/adminhome")
  public String showAdminHome(Model model) {
    List<MyUser> users = userService.loadAllUsers();
    model.addAttribute("users", users);

    return "adminhome";
  }

}