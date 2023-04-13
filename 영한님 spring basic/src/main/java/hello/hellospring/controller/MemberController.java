package hello.hellospring.controller;


import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //spring 컨테이너에서 관리
public class MemberController {

    private final MemberService memberService;

    @Autowired//의존관계 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new") //조회해주는 역할
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") //데이터를 전달해주는 역할
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());


        memberService.join(member);

        return "redirect:/"; //홈으로 다시 돌아감

    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/membersList";

    }


}
