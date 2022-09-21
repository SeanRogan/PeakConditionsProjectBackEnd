package com.seanrogandev.peakconditions.service;

import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.MemberProfile;

public interface MemberProfileService {

    MemberProfile getMemberProfile(Member member);
    void saveProfile(MemberProfile profile);

}
