package com.devsuperior.dsmovie.tests;

import com.devsuperior.dsmovie.entities.RoleEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.projections.UserDetailsProjection;

public class UserDetailsProjectionImpl implements UserDetailsProjection {

    private String username;
    private String password;
    private Long roleId;
    private String authority;

    public UserDetailsProjectionImpl(UserEntity user, RoleEntity role) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roleId = role.getId();
        this.authority = role.getAuthority();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Long getRoleId() {
        return this.roleId;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
