package com.board.boardmaking;


import com.board.boardmaking.domain.Member.Member;
import com.board.boardmaking.domain.Member.Role;
import com.board.boardmaking.domain.Repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

        @Autowired MemberRepository memberRepository;
        @Autowired EntityManager em;

        @AfterEach
        private void after() {

            em.clear();
        }

        private void clear(){
            em.flush();
            em.clear();
        }

         @Test
        public void 회원저장_성공() throws Exception{




              Member member = Member.builder().username("sarang").age(30).nickname("바보").password("12345").role(Role.USER).name("남아연").build();


                 Member saveMember = memberRepository.save(member);

                 Member findMember = memberRepository.findById(saveMember.getId()).orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다."));

                 assertThat(findMember).isSameAs(saveMember);
                 assertThat(findMember).isSameAs(member);


         }

         @Test
        public void 오류_아이디없이_회원가입시() throws Exception{
             Member member = Member.builder().age(30).nickname("바보").password("12345").role(Role.USER).name("남아연").build();

           assertThrows(Exception.class, ()-> memberRepository.save(member));
         }

         @Test
        public void 오류_이름없이_회원가입시() throws Exception{
             Member member = Member.builder().username("sarang").age(30).nickname("바보").password("12345").role(Role.USER).build();


             assertThrows(Exception.class, ()->memberRepository.save(member));
         }

         @Test
        public void 오류_닉네임없이_회원가입시() throws Exception {
             Member member = Member.builder().username("sarang").age(30).password("12345").role(Role.USER).name("남아연").build();

             assertThrows(Exception.class, ()->memberRepository.save(member));

         }

    @Test
    public void 오류_나이없이_회원가입시() throws Exception {
        Member member = Member.builder().nickname("바보").password("12345").role(Role.USER).name("남아연").build();

        assertThrows(Exception.class, ()->memberRepository.save(member));

    }

    @Test
    public void 오류_회원가입시_중복된_아이디가_있음() throws Exception{

        Member member1 = Member.builder().age(30).nickname("바보").password("12345").role(Role.USER).name("남아연").username("namayeon").build();
        Member member2 = Member.builder().age(30).nickname("멍청이").password("4789237498374").role(Role.USER).name("남다연").username("namayeon").build();

        memberRepository.save(member1);
      //  System.out.println("member1 = " + member1);
      // clear();

        assertThrows(Exception.class, ()->   memberRepository.save(member2));
      //  System.out.println("member2 = " + member2);
                /*id값에 널값이 들어가서 exception이 터지지 않았음 */
    }



}