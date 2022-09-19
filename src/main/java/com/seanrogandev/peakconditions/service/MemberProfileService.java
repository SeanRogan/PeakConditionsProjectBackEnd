package com.seanrogandev.peakconditions.service;

import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.MemberProfile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public interface MemberProfileService {

    ModelAndView getMemberProfile(Member member);
    void saveProfile(MemberProfile profile);

}
