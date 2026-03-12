package com.takima.race.runner.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.takima.race.runner.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query(value = """
            SELECT reg.*
            FROM registration reg
            WHERE reg.race_id = :raceId
            """, nativeQuery = true)
    List<Registration> findRegistrationsByRaceId(@Param("raceId") Long raceId);
    
}
