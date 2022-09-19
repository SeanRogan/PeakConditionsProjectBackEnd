package com.seanrogandev.peakconditions.service;

import com.google.common.base.Joiner;
import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.MemberProfile;
import com.seanrogandev.peakconditions.repository.MemberProfileRepository;
import com.seanrogandev.peakconditions.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional

public class MemberProfileServiceImpl implements MemberProfileService{
    private final MemberProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    @Override
    public ModelAndView getMemberProfile(Member member){
        ModelAndView mav = new ModelAndView("profile-view");
        mav.addObject(profileRepository.getById(member.getId()));
        log.info("fetching profile of {}", member.getUserName());
        return mav;
    }
    @Override
    public void saveProfile(MemberProfile profile){
        log.info("Saving new profile for {}", memberRepository.getById(profile.getOwner_id()).getUserName());
        profileRepository.save(profile);
    }
    public void setProfileWeatherPreferences(MemberProfile profile, String weatherPreferences) {
            profile.setPreferences(weatherPreferences);
            log.info("Updating profile \"{}\"" , profile.getOwner_id());
            profileRepository.save(profile);
    }
}
