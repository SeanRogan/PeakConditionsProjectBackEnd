package com.seanrogandev.peakconditions.repository;

import com.seanrogandev.peakconditions.dao.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserName(String userName);
}