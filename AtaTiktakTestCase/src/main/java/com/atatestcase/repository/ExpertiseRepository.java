package com.atatestcase.repository;

import com.atatestcase.entity.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {
    Optional<Expertise> findFirstByCarIdOrderByIdDesc(String carId);
}