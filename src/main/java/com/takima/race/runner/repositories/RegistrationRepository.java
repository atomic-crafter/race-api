package com.takima.race.runner.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takima.race.runner.entities.Race;
import com.takima.race.runner.entities.Registration;
import com.takima.race.runner.entities.Runner;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query(value = """
            SELECT reg.*
            FROM registration reg
            WHERE reg.race_id = :raceId
            """, nativeQuery = true)
    List<Registration> findRegistrationsByRaceId(@Param("raceId") Long raceId);

    boolean existsByRunnerAndRace(Runner runner, Race race);

    long countByRace(Race race);
    
}
