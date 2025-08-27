package com.example.k5_iot_springboot.security.util;

import com.example.k5_iot_springboot.security.UserPrincipal;



public class PrincipalUtils {
    private PrincipalUtils() {}

    // UserPrincipal 전용 검증
    public static void requiredActive(UserPrincipal principal)  {
        if (principal == null) {

        }
        if (!principal.isAccountNonLocked() || !principal.isEnabled() || !principal.isAccountNonExpired()) {

        }

    }
}
