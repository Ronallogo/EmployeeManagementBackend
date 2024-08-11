package com.EmployeeManagment.Source.Security.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.EmployeeManagment.Source.Security.entities.Permission.*;

@RequiredArgsConstructor
public enum Roles {
    USER(
        Set.of(
                USER_CREATE ,
                USER_READ ,
                USER_UPDATE
        )
    ),
    ADMIN(
        Set.of(
                ADMIN_READ,
                ADMIN_UPDATE,
                ADMIN_DELETE,
                ADMIN_CREATE
        )
    );
     @Getter
     private final Set<Permission> permissionSet ;


     public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = new java.util.ArrayList<>(getPermissionSet()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return  authorities ;
     }

}
