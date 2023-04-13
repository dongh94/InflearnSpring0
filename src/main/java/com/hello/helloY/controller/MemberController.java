package com.hello.helloY.controller;

import com.hello.helloY.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    // 생성자로 주입되는 이유 :
    // 필드 주입은 추천하지 않음 - 바꿀 수 없기 때문
    // Setter 주입은 추천하지 않음 - public하게 공개해야 하기 때문
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
