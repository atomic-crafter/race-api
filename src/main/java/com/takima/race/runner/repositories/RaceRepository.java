package com.takima.race.runner.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takima.race.runner.entities.Race;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    List<Race> findByRunnerId(Long runnerId);
}