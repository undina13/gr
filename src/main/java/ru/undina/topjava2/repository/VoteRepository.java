package ru.undina.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import ru.undina.topjava2.model.User;
import ru.undina.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository extends BaseRepository<Vote>{
    @Query("SELECT v FROM Vote v WHERE v.date=current_date")
    List<Vote>getTodayVotes();

    List<Vote>findAllByDate(LocalDate date);

    List<Vote>getAllByUserId(int userId);


}
