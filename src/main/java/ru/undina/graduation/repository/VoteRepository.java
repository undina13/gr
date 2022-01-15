package ru.undina.graduation.repository;

import org.springframework.data.jpa.repository.Query;
import ru.undina.graduation.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends BaseRepository<Vote>{
    @Query("SELECT v FROM Vote v WHERE v.date= :date")
    List<Vote>findAllByDate(LocalDate date);

    List<Vote>getAllByUserId(int userId);

    Optional<Vote> findVoteByDateAndUserId(LocalDate date, int userId);

   Vote getVoteByDateAndUserId(LocalDate date, int userId);
}
