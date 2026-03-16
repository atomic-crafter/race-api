package com.takima.race.runner.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.takima.race.runner.entities.Race;
import com.takima.race.runner.entities.Registration;
import com.takima.race.runner.services.RaceService;



@RestController
@RequestMapping("/races")
public class RaceController {

    public record RegistrationRequest(Long runnerId) {}
    public record ParticipantCountResponse(long count) {}

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    public List<Race> getAll(@RequestParam(required = false) String location) {
        return raceService.getAllRaces(location);
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
    @ResponseStatus(HttpStatus.CREATED)
    public Race create(@RequestBody Race race) {
        return raceService.create(race);
    }

    @GetMapping("/{id}/participants/count")
    public ParticipantCountResponse getParticipantsCount(@PathVariable Long id) {
        return new ParticipantCountResponse(raceService.getParticipantsCount(id));
    }


    @GetMapping("/{id}/registrations")
    public List<Registration> getRegistrations(@PathVariable Long id) {
        return raceService.getRegistrations(id);
    }

    @PostMapping("/{raceId}/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public Registration createRegistration(@PathVariable Long raceId, @RequestBody RegistrationRequest body) {
        return raceService.createRegistration(raceId, body.runnerId());
    }
}
