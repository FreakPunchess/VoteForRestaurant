package ru.javaops.bootjava.util;

import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.to.VoteTo;

public class VoteUtil {

    public static Vote fromTo(int userId, VoteTo voteTo) {
        return new Vote(userId, voteTo.getRestaurantId(), voteTo.getDateTime());
    }

    public static VoteTo makeTo(Vote vote) {
        return new VoteTo(vote.getRestaurantId(), vote.getDateTime());
    }

    public static void updateFromTo(Vote vote, VoteTo voteTo) {
        vote.setRestaurantId(voteTo.getRestaurantId());
        vote.setDateTime(voteTo.getDateTime());
    }
}
