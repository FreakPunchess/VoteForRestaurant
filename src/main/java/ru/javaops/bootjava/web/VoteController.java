package ru.javaops.bootjava.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.error.DataConflictException;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.VoteRepository;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/api/vote";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected VoteRepository repository;

    @GetMapping()
    public Vote get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get today's user vote");
        return repository.getByUserIdAndVoteDate(authUser.id(), LocalDate.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Valid @RequestBody Vote vote,
                                                   @AuthenticationPrincipal AuthUser authUser) {
        log.info("create {}", vote);
//        checkNew(restaurant);
        Vote newOrUpdated = repository.getByUserIdAndVoteDate(authUser.id(), LocalDate.now());
        if(newOrUpdated.getVoteTime().isAfter(LocalTime.of(11, 0))) {
            throw new DataConflictException("Updating your vote after 11 AM is forbidden...");
        } else if (newOrUpdated.getVoteTime().isBefore(LocalTime.of(11, 0))) {
            return ResponseEntity.ok(repository.save(vote));
        }

        newOrUpdated = repository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newOrUpdated.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newOrUpdated);
    }
}
