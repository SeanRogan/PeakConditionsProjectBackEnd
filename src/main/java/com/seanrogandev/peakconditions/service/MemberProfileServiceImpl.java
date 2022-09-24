package com.seanrogandev.peakconditions.service;

import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.MemberProfile;
import com.seanrogandev.peakconditions.dao.MountainPeak;
import com.seanrogandev.peakconditions.repository.MemberProfileRepository;
import com.seanrogandev.peakconditions.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

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
//
//    public void addPeakToFavorites(MemberProfile profile, MountainPeak mountainPeak) {
//        try {
//            Member profileOwner = memberRepository.getById(profile.getOwner_id());
//            log.info("Adding {} to {}'s profile's favorite mountain peaks", mountainPeak.getPeakName(), profileOwner.getUserName());
//            HashSet<MountainPeak> favoritePeaks = profile.getFavoriteMountains();
//            favoritePeaks.add(mountainPeak);
//            saveProfile(profile);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }

}
