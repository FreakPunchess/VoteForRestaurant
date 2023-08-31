package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Query;
import ru.javaops.bootjava.model.Vote;

import java.time.LocalDate;

public interface VoteRepository extends BaseRepository<Vote> {

    @Query("select v from Vote v where v.userId = (:userId) and v.voteDate = (:voteDate)")
    Vote getByUserIdAndVoteDate(int userId, LocalDate voteDate);

    @Query("select count(v) from Vote v where v.restaurantId = (:restaurantId)")
    int getVotesCount(int restaurantId);
}
