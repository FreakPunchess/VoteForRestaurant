package ru.javaops.bootjava.web.vote;

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
import ru.javaops.bootjava.to.VoteTo;
import ru.javaops.bootjava.web.AuthUser;

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
    public VoteTo get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get today's user vote");
        return makeTo(repository.getByUserIdAndVoteDate(authUser.id(), LocalDate.now()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> createOrUpdate(@RequestBody @Valid VoteTo voteTo,
                                                 @AuthenticationPrincipal AuthUser authUser) {
        log.info("create vote for restaurant with id {}", voteTo.getRestaurantId());
        Vote newOrUpdated = repository.getByUserIdAndVoteDate(authUser.id(), LocalDate.now());
        if (newOrUpdated.getVoteTime().isAfter(LocalTime.of(11, 0))) {
            throw new DataConflictException("Updating your vote after 11 AM is forbidden...");
        } else if (newOrUpdated.getVoteTime().isBefore(LocalTime.of(11, 0))) {
            newOrUpdated.setRestaurantId(voteTo.getRestaurantId());
            return ResponseEntity.ok(makeTo(repository.save(newOrUpdated)));
        }

        newOrUpdated = repository.save(fromTo(authUser.id(), voteTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newOrUpdated.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(makeTo(newOrUpdated));
    }


    private Vote fromTo(int userId, VoteTo voteTo) {
        return new Vote(userId, voteTo.getRestaurantId());
    }

    private VoteTo makeTo(Vote vote) {
        return new VoteTo(vote.getRestaurantId());
    }
}
