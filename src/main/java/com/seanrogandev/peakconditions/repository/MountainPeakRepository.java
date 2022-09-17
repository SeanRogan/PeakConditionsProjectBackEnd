package com.seanrogandev.peakconditions.repository;

import com.seanrogandev.peakconditions.dao.MountainPeak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainPeakRepository  extends JpaRepository<MountainPeak, Long> {
}
