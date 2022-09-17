package com.seanrogandev.peakconditions.repository;

import com.seanrogandev.peakconditions.dao.MountainRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MountainRangeRepository extends JpaRepository<MountainRange, Long> {
}

