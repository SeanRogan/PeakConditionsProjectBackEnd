package com.seanrogandev.peakconditions.service;
import com.seanrogandev.peakconditions.dao.Role;
import com.seanrogandev.peakconditions.repository.RoleRepository;
import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService , UserDetailsService {
    private final RoleRepository roleRepo;
    private final MemberRepository memberRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Member saveMember(Member member) {
        log.info("saving new user {} to the database" , member.getUserName());
        member.setPassWord(passwordEncoder.encode(member.getPassWord()));
        return memberRepo.save(member);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void assignRole(String userName, String roleName) {
        Member member = memberRepo.findByUserName(userName);
        Role role = roleRepo.findByName(roleName);
        member.getRoles().add(role);
    }

    @Override
    public Member getMember(String userName) {
        return memberRepo.findByUserName(userName);
    }

    @Override
    public Page<Member> getMembers() {
        return memberRepo.findAll(Pageable.ofSize(10));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load the user from the database
        Member user = memberRepo.findByUserName(username);
        //if the user wasnt found, log an error
        if(user == null) {
            log.error("User not found in the database");
        }
        //instantiate an empty list of
        //SimpleGrantedAuthorities
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //loop through list and add user roles to list.
        try {
            assert user != null;
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
        } catch (NullPointerException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
        //return a spring.security User object, with the members username, password, and the authoritize/roles list
        return new User(user.getUserName(),user.getPassWord(), authorities);
    }
}