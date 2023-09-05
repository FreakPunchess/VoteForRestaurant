package ru.javaops.bootjava.web.vote;


import ru.javaops.bootjava.to.VoteTo;
import ru.javaops.bootjava.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "dateTime");

    public static final VoteTo voteTo1 = new VoteTo(1, LocalDateTime.now());
    public static final VoteTo voteBefore11 =
            new VoteTo(2, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)));
    public static final VoteTo voteAfter11 =
            new VoteTo(2, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30)));

    public static VoteTo getNewVoteTo() {
        return new VoteTo(2, LocalDateTime.now());
    }
}
