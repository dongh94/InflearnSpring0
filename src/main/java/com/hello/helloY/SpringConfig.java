package com.hello.helloY;

import com.hello.helloY.repository.MemberRepository;
import com.hello.helloY.repository.MemoryMemberRepository;
import com.hello.helloY.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
//
//    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }
}
