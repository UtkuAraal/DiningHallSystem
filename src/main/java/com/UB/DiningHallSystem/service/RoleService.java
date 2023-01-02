package com.UB.DiningHallSystem.service;

import com.UB.DiningHallSystem.entity.Role;
import com.UB.DiningHallSystem.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService implements IRoleService{

    private final RoleRepo roleRepo;

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }
}
