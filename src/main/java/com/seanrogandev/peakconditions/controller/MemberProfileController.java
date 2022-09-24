package com.seanrogandev.peakconditions.controller;


import com.seanrogandev.peakconditions.dao.MemberProfile;
import com.seanrogandev.peakconditions.service.MemberProfileServiceImpl;
import com.seanrogandev.peakconditions.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberProfileController {

    private final MemberProfileServiceImpl profileService;
    private final MemberServiceImpl memberService;
    //get profile
    @GetMapping("/profile")
    public ResponseEntity<MemberProfile> getMemberProfile(@RequestParam Long userId) {
        try{
            return new ResponseEntity<>(profileService.getMemberProfile(memberService.getMemberById(userId)), HttpStatus.OK);
        } catch(Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //create new profile
    @PostMapping("/profile/new")
    public ResponseEntity<MemberProfile> createNewProfile(@RequestParam Long userId, @RequestBody MemberProfile memberProfile) {
        try {
            //profileService.deleteProfile(userId);
            profileService.saveProfile(memberProfile);
            return new ResponseEntity<>(memberProfile, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<MemberProfile> editProfile(@RequestParam Long userId, @RequestBody MemberProfile newProfile) {

        try {
            MemberProfile memberProfile = profileService.getMemberProfile(memberService.getMemberById(userId));
            memberProfile.setPreferences(newProfile.getPreferences());
            memberProfile.setTemperaturePreferenceHigh(newProfile.getTemperaturePreferenceHigh());
            memberProfile.setTemperaturePreferenceLow(newProfile.getTemperaturePreferenceLow());
            memberProfile.setWindConditionsPreferenceMax(newProfile.getWindConditionsPreferenceMax());
           // memberProfile.setFavoriteMountains(newProfile.getFavoriteMountains());
            profileService.saveProfile(memberProfile);
            return new ResponseEntity<>(memberProfile, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
