package com.example.CAPSTONE1.auth;

public enum Member {
    NORMAL, MANAGER;

    public static boolean isNormal(Member member) {
        return member.equals(NORMAL);
    }

    public static boolean isManager(Member member) {
        return member.equals(MANAGER);
    }

}
