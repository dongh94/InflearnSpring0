회원 관리 - 백엔드 개발 기초

1. 비즈니스 요구사항 정리 

2. 회원 Domain

   > Getter and Setter 형식

   * ```
     private final Long id;
     private final String name;
     ```

3. 회원 Repository 

* ```java
  public Interface MemberRepository
  public abstract 
  - 모든 멤버변수는 public static final이어야하며, 이를 생략할 수 있다.
  - 모든 메서드는 public abstract이어야하며, 이를 생략할 수 있다.
  ```

* ```java
  @Repository = 스프링 컨테이너에 주입
  public class DBMemberRepository implements MemberRepository
  private final  (내 클래스, 정적 X (DI 가능) ,수정 X)
  @Override
  public (모든 패키지, 정적 X )
  ```

  **접근제어자**

* private : 내 클래스 
* default : 내 패키지 
* protect :  상속 패키지 
* Public : 모든 패키지
* static : 정적
* static X : 정적 X (DI 가능)
* final : 수정 X
* final X : 수정

4. 회원 Repository Test 

> Given / When / Then  = G W T

* ```java
  public class MemoryMemberRepositoryTest {
      MemoryMemberRepository repository = new MemoryMemberRepository();
  
      // 한번씩 Test 할 때마다 저장소를 다 지워준다.(MemoryMemberRepository의 clear)
      // 즉, intance의 속성이 겹쳐지게 test를 구현했을 때 상관없이(서로 영향 X) Test가능.
      @AfterEach
      public void afterEach() {
          repository.clearStore();
      }
  
      @Test
      public void save() {
          Member member = new Member();
          member.setName("spring");
  
          repository.save(member);
          Member result = repository.findById(member.getId()).get();
  
          // result가 기댓값 member가 맞는지.
          Assertions.assertEquals(member, result);
          // assertj활용 null이 기댓값 member가 맞는지. 
          // Assertions 를 alt enter로 static import
          assertThat(result).isEqualTo(member);
      }
  
  ```

* static import : 정적으로 import 해서 마지막 파일로 메서드 사용가능하다.

* ```java
  import static java.lang.Math.abs;
  
  int i = abs(-20);
  ```

  

5. 회원 Service 

* ```java
  @Service = 스프링 컨테이너에 주입
  public class MemberService {
  
      private final MemberRepository memberRepository;
      @Autowired = Service 생성자를 연결 - Controller, Service, Repository
      public MemberService(MemberRepository memberRepository) {
          this.memberRepository = memberRepository;
      }
  }
  
  ```

* 

6. 회원 Service Test

* ```java
  class MemberServiceTest {
  
      MemberService memberService;
      MemoryMemberRepository memberRepository;
      // new memberService를 하면 계속 인스턴스가 생성되기 때문에
      // 하나의 Service로 만들기 위해 DI 즉, 외부에서 직접 넣어주는 방식으로  MemberService를 수정하였다. this.repo
      // BeforeEach = 먼저 넣어주고 시작.
      @BeforeEach
      public void beforeEach() {
          memberRepository = new MemoryMemberRepository();
          memberService = new MemberService(memberRepository);
      }
      @AfterEach
      public void afterEach() {
          memberRepository.clearStore();
      }
  
  ```

* 

1. 회원 Controller GetMapping
2. 회원 Controller PostMapping

* ```java
  package com.hello.helloY.controller;
  
  import com.hello.helloY.domain.Member;
  import com.hello.helloY.service.MemberService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Controller;
  import org.springframework.ui.Model;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PostMapping;
  
  import java.util.List;
  
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
  
      @GetMapping("/members/new")
      public String createForm() {
          return "members/createMemberForm";
      }
  
      @PostMapping("/members/new")
      public String create(MemberForm form) {
          Member member = new Member();
          member.setName(form.getName());
          memberService.join(member);
          return "redirect:/";
      }
  
      @GetMapping("/members")
      public String list(Model model) {
          List<Member> members = memberService.findMembers();
          model.addAttribute("members", members);
          return "members/memberList";
      }
  }
  
  ```

* 

다음 기록은  Component Scan, Bean, DB