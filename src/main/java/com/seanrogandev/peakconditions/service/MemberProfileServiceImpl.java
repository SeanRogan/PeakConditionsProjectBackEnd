package com.seanrogandev.peakconditions.service;

import com.google.common.base.Joiner;
import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.MemberProfile;
import com.seanrogandev.peakconditions.repository.MemberProfileRepository;
import com.seanrogandev.peakconditions.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public MemberProfile getMemberProfile(Member member){
        log.info("fetching profile of {}", member.getUserName());
        try{
            return profileRepository.getById(member.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
    @Override
    public void saveProfile(MemberProfile profile){
        log.info("Saving new profile for {}", memberRepository.getById(profile.getOwner_id()).getUserName());
        try{
            profileRepository.save(profile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    public void setProfileWeatherPreferences(MemberProfile profile, String weatherPreferences) {
            log.info("Updating profile \"{}\"" , profile.getOwner_id());
            try {
                profile.setPreferences(weatherPreferences);
                profileRepository.save(profile);
            } catch(Exception e) {
                log.error(e.getMessage());
            }
            }
}
