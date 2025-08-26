package com.example.k5_iot_springboot.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
    === UserPrincipal ===
    : "보안 관점에서 사용자 표현"을 담당하는 값 객체(Value Object)
    - Spring Security가 인증/인가 과정에서 인지하는 최소한의 사용자 정보 집합
    - 엔티티(G_User) 자체를 지니지 않고, 인증이 필요한 값만 안전하게 전달/보관
        >> 결합도 낮춤, 캐시/직렬화 안정성 향상

    필요성
    1) Security의 표준 진입점: AuthenticationProvider는 UserDetails 타입을 통해 사용자 정보와 권한을 검사
        >> 토큰의 payload의 username을 통해 DB에서 사용자 정보를 읽고 해당 클래스로 감싼 뒤 반환
            , 이후 인증 과정이 표준화되어 동작

    2) 경량/안정성 향상: 영속성 엔티티 (G_User)를 SecurityContext에 보관하면
                        , 직렬화 문제, 지연로딩, 순환 참조 등의 문제 발생 가능성 증가

    >> 인증 성공 시 Authentication(principal)에 들어가 SecurityContextHolder에 저장됨
        - 컨트롤러 @AuthenticationPrincipal UserPrincipal principal로 주입받아 사용

    >> 설계 포인트
    1) 불변성: 모든 필드는 final (생성 이후 변경 불가)
    2) @JsonIgnore, @ToString(exclude="password")를 통해 비밀번호 유출을 2중 차단
    3) 빌더 사용: 가독성 향상, 테스트 용이
 */
@Getter
@ToString(exclude = "password")
public class UserPrincipal implements UserDetails {
    // UserDetails: 시큐리티가 요구하는 사용자 정보 인터페이스

    private final Long id;                                              // PK
    private final String username;                                      // 로그인 아이디
    @JsonIgnore
    private final String password;                                      // 해시 비밀번호
    private final Collection<? extends GrantedAuthority> authorities;   // 권한

    // === UserDetails 구현 === //
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return List.of();}
    @Override public String getPassword() { return "";}
    @Override public String getUsername() { return "";}
}
