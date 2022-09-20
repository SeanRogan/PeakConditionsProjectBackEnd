package com.seanrogandev.peakconditions.service;

import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.MemberProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public interface MemberProfileService {

    MemberProfile getMemberProfile(Member member);
    void saveProfile(MemberProfile profile);

}
