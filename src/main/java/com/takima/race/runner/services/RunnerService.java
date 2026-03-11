package com.takima.race.runner.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.runner.entities.Race;
import com.takima.race.runner.entities.Runner;
import com.takima.race.runner.repositories.RaceRepository;
import com.takima.race.runner.repositories.RunnerRepository;

@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;
    private final RaceRepository raceRepository;

    public RunnerService(RunnerRepository runnerRepository, RaceRepository raceRepository) {
        this.runnerRepository = runnerRepository;
        this.raceRepository = raceRepository;
    }

    public List<Runner> getAll() {
        return runnerRepository.findAll();
    }

    public Runner getById(Long id) {
        return runnerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Runner %s not found", id)
                )
        );
    }

    public Runner create(Runner runner) {
        return runnerRepository.save(runner);
    }

    public void delete(Long id) {
        Runner runner = getById(id);
        runnerRepository.delete(runner);
    }

    public Runner update(Long id, Runner runner) {
        Runner existingRunner = getById(id);
        existingRunner.setFirstName(runner.getFirstName());
        existingRunner.setLastName(runner.getLastName());
        existingRunner.setEmail(runner.getEmail());
        existingRunner.setAge(runner.getAge());
        return runnerRepository.save(existingRunner);
    }

    public List<Race> getRaces(Long id) { 
        getById(id);
        return raceRepository.findByRunnerId(id);
    }
}
