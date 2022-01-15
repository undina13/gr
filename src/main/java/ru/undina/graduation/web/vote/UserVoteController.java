package ru.undina.graduation.web.vote;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.webjars.NotFoundException;
import ru.undina.graduation.error.TimeException;
import ru.undina.graduation.model.Vote;
import ru.undina.graduation.repository.RestaurantRepository;
import ru.undina.graduation.repository.VoteRepository;
import ru.undina.graduation.web.SecurityUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.undina.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.undina.graduation.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserVoteController {
    private LocalTime timeEndVoting = LocalTime.of(23, 00);
    static final String REST_URL = "/api/votes";
   private final VoteRepository voteRepository;
   @Autowired
    protected RestaurantRepository restaurantRepository;

    public UserVoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }



    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@RequestParam @NotNull Integer restaurantId) {

        log.info("create vote for restaurant{}", restaurantId);
        Vote vote = new Vote(null, SecurityUtil.authUser(),  restaurantRepository.getById(restaurantId), LocalDate.now());

        checkNew(vote);

        Vote created = voteRepository.save(vote);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam @NotNull Integer restaurantId, @PathVariable int id) throws TimeException {
        log.info("update vote with id={}",  id);
        if (LocalTime.now().isAfter(timeEndVoting)) {
            throw new TimeException("Vote change time  is ending");
        }
        LocalDate date = LocalDate.now();
        int userId = SecurityUtil.authUser().getId();
        Vote vote = voteRepository.getVoteByDateAndUserId(date, userId);
                assureIdConsistent(vote, id);
                voteRepository.save(vote);

    }

    @GetMapping("/today")
    public ResponseEntity<Vote> getTodayVote() {
        log.info("get today");
        LocalDate date = LocalDate.now();
        int userId = SecurityUtil.authUser().getId();
        return ResponseEntity.of(voteRepository.findVoteByDateAndUserId(date, userId));
    }
}
