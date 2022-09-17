package com.seanrogandev.peakconditions;

import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.MemberProfile;
import com.seanrogandev.peakconditions.dao.Role;
import com.seanrogandev.peakconditions.service.MemberServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class PeakConditionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeakConditionsApplication.class, args);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(MemberServiceImpl memberService) {
        return args -> {
            memberService.saveRole(new Role(null, "ROLE_USER_FREE"));
            memberService.saveRole(new Role(null, "ROLE_USER_PAID"));
            memberService.saveRole(new Role(null, "ROLE_ADMIN"));
            memberService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            memberService.saveMember(new Member(null, "Sean","Rogan","Srogan88@Gmail.com", "mknmvs", new ArrayList<>(), new MemberProfile()));

            memberService.assignRole("Srogan88@Gmail.com","ROLE_SUPER_ADMIN");
            memberService.assignRole("Srogan88@Gmail.com","ROLE_USER_PAID");
        };
    }
}
