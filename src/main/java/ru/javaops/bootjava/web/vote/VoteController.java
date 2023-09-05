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
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javaops.bootjava.util.VoteUtil.*;

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
    public ResponseEntity<VoteTo> create(@RequestBody @Valid VoteTo voteTo,
                                         @AuthenticationPrincipal AuthUser authUser) {
        log.info("create vote for restaurant with id {}", voteTo.getRestaurantId());
        Vote newVote = repository.getByUserIdAndVoteDate(authUser.id(), LocalDate.now());
        if (newVote != null) {
            updateFromTo(newVote, voteTo);
            return update(newVote);
        }

        newVote = repository.save(fromTo(authUser.id(), voteTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(makeTo(newVote));
    }

    private ResponseEntity<VoteTo> update(Vote forUpdate) {
        if (forUpdate.getVoteTime().isAfter(LocalTime.of(11, 0, 0, 0))) {
            throw new DataConflictException("Updating your vote after 11 AM is forbidden...");
        } else {
            return ResponseEntity.ok(makeTo(repository.save(forUpdate)));
        }
    }
}
