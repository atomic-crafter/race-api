package com.takima.race.runner.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.runner.entities.Race;
import com.takima.race.runner.entities.Registration;
import com.takima.race.runner.entities.Runner;
import com.takima.race.runner.repositories.RaceRepository;
import com.takima.race.runner.repositories.RegistrationRepository;
import com.takima.race.runner.repositories.RunnerRepository;
@Service
public class RaceService {
    private final RunnerRepository runnerRepository;
    private final RaceRepository raceRepository;
    private final RegistrationRepository registrationRepository;

    public RaceService(RunnerRepository runnerRepository, RaceRepository raceRepository, RegistrationRepository registrationRepository) {
        this.runnerRepository = runnerRepository;
        this.raceRepository = raceRepository;
        this.registrationRepository = registrationRepository;
    }

    public List<Race> getAllRaces(String location) {
        if (location == null || location.isBlank()) {
            return raceRepository.findAll();
        }
        return raceRepository.findByLocation(location);
    }

    public Race getById(Long id) {
        return raceRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Race %s not found", id)
                )
        );
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

    public List<Registration> getRegistrations(Long id) {
        getById(id);
        return registrationRepository.findRegistrationsByRaceId(id);
    }

    public long getParticipantsCount(Long id) {
        Race race = getById(id);
        return registrationRepository.countByRace(race);
    }

    public Registration createRegistration(Long raceId, Long runnerId) {
        if (runnerId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "runnerId is required"
            );
        }

        Race race = getById(raceId);
        Runner runner = runnerRepository.findById(runnerId).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Runner %s not found", runnerId)
                )
        );

        if (registrationRepository.existsByRunnerAndRace(runner, race)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Runner is already registered for this race"
            );
        }

        long participantCount = registrationRepository.countByRace(race);
        if (participantCount >= race.getMaxParticipants()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Race is full"
            );
        }

        Registration registration = new Registration();
        registration.setRunner(runner);
        registration.setRace(race);
        registration.setRegistrationDate(java.time.LocalDate.now());
        return registrationRepository.save(registration);
    }

}