package com.takima.race.runner.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.takima.race.runner.entities.Race;
import com.takima.race.runner.entities.Runner;
import com.takima.race.runner.repositories.RaceRepository;
import com.takima.race.runner.repositories.RunnerRepository;
@Service
public class RaceService {
    private final RunnerRepository runnerRepository;
    private final RaceRepository raceRepository;

    public RaceService(RunnerRepository runnerRepository, RaceRepository raceRepository) {
        this.runnerRepository = runnerRepository;
        this.raceRepository = raceRepository;
    }

    public List<Race> getAllRaces() {
        return raceRepository.findAll();
    }

    public Race getById(Long id) {
        return raceRepository.findById(id).orElseThrow();
    }

    public Race create(Race race) {
        return raceRepository.save(race);
    }

    public Race update(Long id, Race race) {
        Race existingRace = getById(id);
        existingRace.setName(race.getName());
        existingRace.setDate(race.getDate());
        existingRace.setLocation(race.getLocation());
        existingRace.setMaxParticipants(race.getMaxParticipants());
        return raceRepository.save(existingRace);
    }

    public List<Runner> getParticipants(Long id) {
        getById(id);
        return runnerRepository.findByRaceId(id);
    }

}