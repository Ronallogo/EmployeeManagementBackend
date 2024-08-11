package com.EmployeeManagment.Source.Security.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    USER_CREATE("user:create") ,
    USER_UPDATE("uer:update"),
    USER_READ("user:read")
    ;

    @Getter
    private final String permission ;


}
