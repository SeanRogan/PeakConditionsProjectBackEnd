package com.seanrogandev.peakconditions.repository;

import com.seanrogandev.peakconditions.dao.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile,Long> {
}