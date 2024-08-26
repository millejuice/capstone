package com.example.CAPSTONE1.auth;

public enum ROLE {
    NORMAL, MANAGER;

    public static boolean isNormal(ROLE member) {
        return member.equals(NORMAL);
    }

    public static boolean isManager(ROLE member) {
        return member.equals(MANAGER);
    }

}
