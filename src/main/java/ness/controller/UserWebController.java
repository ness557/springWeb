package ness.controller;

import org.apache.commons.lang3.StringUtils;
import ness.model.Role;
import ness.model.User;
import ness.model.UserInfo;
import ness.service.RoleService;
import ness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserWebController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserWebController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping
    public String index(){

        return "redirect:users/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getUsers(Model model) {

        model.addAttribute("usersList", userService.getUserList());
        return "show_user";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(Model model) {

        User user = new User();
        UserInfo info = new UserInfo();
        user.setUserInfo(info);

        model.addAttribute("user", user)
                .addAttribute("roleList", roleService.getRoles());
        return "form_user";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateUser(@RequestParam(value = "id") int id, Model model) {

        User user = userService.getUserById(id);
        model.addAttribute("user", user)
                .addAttribute("roleList", roleService.getRoles());

        return "form_user";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(value = "id") int id) {

        userService.removeUser(id);
        return "redirect:list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user, BindingResult result1,
                           @ModelAttribute("rolesString") String roles, BindingResult result2) {

        Set<Role> roleSet = new HashSet<>();

        for (String roleName: roles.split(",")) {
            Role role = roleService.findRoleByName(
                    StringUtils.deleteWhitespace(roleName));
            roleSet.add(role);
        }

        user.setRoles(roleSet);

        userService.saveOrUpdate(user);

        return "redirect:list";
    }

}
