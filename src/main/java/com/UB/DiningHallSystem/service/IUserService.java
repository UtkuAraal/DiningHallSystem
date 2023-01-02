package com.UB.DiningHallSystem.service;

import com.UB.DiningHallSystem.entity.AppUser;

import java.util.List;

public interface IUserService {
    AppUser saveUser(AppUser user);
    void addRoleToUser(String username, String role);
    AppUser getUser(String username);
    List<AppUser> getUsers();
    void deleteUser(String username);
    String addEat(int count);
    String eat();
}
