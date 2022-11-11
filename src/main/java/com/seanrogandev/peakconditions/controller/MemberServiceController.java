package com.seanrogandev.peakconditions.controller;

import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.Role;
import com.seanrogandev.peakconditions.service.impl.MemberServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class MemberServiceController {
    private final MemberServiceImpl memberService;

    //get all members
    @GetMapping("/members")
    //request for members returns an ok response with a page of members, via the member services class
    public ResponseEntity<Page<Member>> getMembers(){
        return ResponseEntity.ok().body(memberService.getMembers());
    }

    //register a new member
    @PostMapping("/members/register")
    //post request with a Member object
    public ResponseEntity<Member> registerNewMember(@RequestBody Member member){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/members/register").toUriString());
        return ResponseEntity.created(uri).body(memberService.saveMember(member));
    }
    //save a new role
    @PostMapping("/roles/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles/save").toUriString());
        return ResponseEntity.created(uri).body(memberService.saveRole(role));
    }
    //assign a role to a member
    @PostMapping("/role/assign")
    public ResponseEntity<?> assignRole(@RequestBody RoleToUserForm form) {
        memberService.assignRole(form.getUserName(),form.getUserName());
        return  ResponseEntity.ok().build();
    }

}
@Data
@RequiredArgsConstructor
class RoleToUserForm {
    private String userName;
    private String roleName;
}
