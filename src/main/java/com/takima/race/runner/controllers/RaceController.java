package com.takima.race.runner.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takima.race.runner.entities.Race;
import com.takima.race.runner.services.RaceService;



@RestController
@RequestMapping("/races")
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    public List<Race> getAll() {
        return raceService.getAllRaces();
    }

    @GetMapping("/{id}")
    public Race getById(@PathVariable Long id) {
        return raceService.getById(id);
    }

    @PutMapping("/{id}")
    public Race update(@PathVariable Long id, @RequestBody Race race) {
        return raceService.update(id, race);
    }

    @PostMapping
    public Race create(@RequestBody Race race) {
        return raceService.create(race);
    }

    @GetMapping("/{id}/participants/count")
    public int getParticipantsCount(@PathVariable Long id) {
        return raceService.getParticipants(id).size();
    }

}
