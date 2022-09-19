package com.seanrogandev.peakconditions.controller;


import com.seanrogandev.peakconditions.dao.MemberProfile;
import com.seanrogandev.peakconditions.repository.MemberProfileRepository;
import com.seanrogandev.peakconditions.repository.MemberRepository;
import com.seanrogandev.peakconditions.service.MemberProfileServiceImpl;
import com.seanrogandev.peakconditions.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberProfileController {

    private final MemberProfileServiceImpl profileService;
    private final MemberServiceImpl memberService;
    //get profile
    @GetMapping("/profile")
    public ResponseEntity<ModelAndView> getMemberProfile(@RequestParam Long userId) {
        try{
            return new ResponseEntity<>(profileService.getMemberProfile(memberService.getMemberById(userId)),HttpStatus.OK);
        } catch(Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //create new profile
    @PostMapping("/profile/editor")
    public ResponseEntity<MemberProfile> editProfile(@RequestBody MemberProfile memberProfile) {
        try {
            profileService.saveProfile(memberProfile);
            return new ResponseEntity<>(memberProfile, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
