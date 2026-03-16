package com.takima.race.runner.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takima.race.runner.entities.Race;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    List<Race> findByLocation(String location);

    @Query(value = """
            SELECT ra.*
            FROM race ra
            JOIN registration reg ON reg.race_id = ra.id
            WHERE reg.runner_id = :runnerId
            """, nativeQuery = true)
    List<Race> findByRunnerId(@Param("runnerId") Long runnerId);
}