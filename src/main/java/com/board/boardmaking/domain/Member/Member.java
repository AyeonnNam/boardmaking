package com.board.boardmaking.domain.Member;


import com.board.boardmaking.BaseTimeEntity;
import lombok.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Table(name="MEMBER")
@Getter
@NoArgsConstructor(access = PUBLIC)  // 테스트를 위해선 임시로 PUBLIC 으로 열어야함
@Entity
@Builder
@AllArgsConstructor
public class Member extends BaseTimeEntity {


        /* 빌더 패턴
        *   생성자에 인자가 많을 때는 빌더 패턴을 고려하라
        *  복합 객체의 생성과정과 표현방법을 분리하여 동일한 생성 절차에서 서로 다른 표현 결과를 만들 수 있게 하는 패턴
        *  생성자가 많아지면 빌더 패턴을 만드는 게 편함.
        * 빌더 패턴을 활용하면 어떤 필드에 어떤 인자를 넣어줬는지 명확히 알수 있고, 넣어 줄 필요 없는 필드(NULL)은 굳이
        * 선언할 필요가 없다,
        * 어떤 필드에 NULL이 들어간다는 걸 명확히 볼 수 있는 점 때문에 생성자를 통해 객체를 생성하는 방법을 택하기도 함
        *
        *
        *
        * */

    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username; //아이디

    @Column(nullable = false, length= 30)
    private String name;

    private String password;

    @Column(nullable=false, length=30)
    private String nickname; //별명

    @Column(nullable = false, length=30)
    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;


    //--- 정보 수정---//
    public void updatePassword(PasswordEncoder passwordEncoder, String password ){
        this.password = passwordEncoder.encode(password);
    }

    public void updateName(String username){
        this.username = username;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateAge(int age){
        this.age = age;
    }

    //----패스워드 암호화----//
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }
}
