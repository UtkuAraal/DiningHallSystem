package com.UB.DiningHallSystem.service;

import com.UB.DiningHallSystem.entity.AppUser;
import com.UB.DiningHallSystem.entity.Role;
import com.UB.DiningHallSystem.repo.AppUserRepo;
import com.UB.DiningHallSystem.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService, UserDetailsService {

    private final AppUserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCountEat(0);
        return userRepo.save(user);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser user = userRepo.findByUsername(username);
        Role Addrole = roleRepo.findByName(role);
        user.getRoles().add(Addrole);
    }

    @Override
    public AppUser getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(String username) {
        AppUser user = userRepo.findByUsername(username);
        userRepo.delete(user);
    }

    @Override
    public String addEat(int count) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        AppUser loggedUser = userRepo.findByUsername(username);
        loggedUser.setCountEat(loggedUser.getCountEat() + count);
        userRepo.save(loggedUser);
        String message = username + " has " + loggedUser.getCountEat() + "right";
        return message;
    }

    @Override
    public String eat() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        String message;
        AppUser loggedUser = userRepo.findByUsername(username);
        if(loggedUser.getCountEat() > 0){
            loggedUser.setCountEat(loggedUser.getCountEat() - 1);
            userRepo.save(loggedUser);
            message = "Enjoy your meal";
        }else{
            message = "Insufficient right";
        }
        return message;
    }


}
