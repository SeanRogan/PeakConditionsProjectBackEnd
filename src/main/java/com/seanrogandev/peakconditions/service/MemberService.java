package com.seanrogandev.peakconditions.service;

import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.Role;
import org.springframework.data.domain.Page;

public interface MemberService {
    Member saveMember(Member member);
    Role saveRole(Role role);
    void assignRole(String memberName, String roleName);
    Member getMember(String memberName);
    Page<Member> getMembers();
}

