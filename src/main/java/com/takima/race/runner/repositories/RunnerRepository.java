package com.takima.race.runner.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takima.race.runner.entities.Runner;

@Repository
public interface RunnerRepository extends JpaRepository<Runner, Long> {
    List<Runner> findByFirstName(String firstName);

    @Query(value = """
            SELECT ru.*
            FROM runner ru
            JOIN registration reg ON reg.runner_id = ru.id
            WHERE reg.race_id = :raceId
            """, nativeQuery = true)
    List<Runner> findByRaceId(@Param("raceId") Long raceId);
}