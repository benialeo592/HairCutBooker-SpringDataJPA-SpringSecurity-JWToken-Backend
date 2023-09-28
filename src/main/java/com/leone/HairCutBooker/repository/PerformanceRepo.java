package com.leone.HairCutBooker.repository;

import com.leone.HairCutBooker.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepo extends JpaRepository<Performance, Long> {
}
