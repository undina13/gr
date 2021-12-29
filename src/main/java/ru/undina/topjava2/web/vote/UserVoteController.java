package ru.undina.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.undina.topjava2.error.TimeException;
import ru.undina.topjava2.model.Vote;
import ru.undina.topjava2.repository.RestaurantRepository;
import ru.undina.topjava2.repository.VoteRepository;
import ru.undina.topjava2.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static ru.undina.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.undina.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserVoteController {
    static final String REST_URL = "/api/vote";
    VoteRepository voteRepository;
    RestaurantRepository restaurantRepository;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Vote> createNew(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) throws TimeException {
        log.info("create vote");
        Vote vote = new Vote(null, authUser.getUser(), restaurantRepository.getById(restaurantId), LocalDate.now() );
        checkNew(vote);

        Vote created = voteRepository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void update(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) throws TimeException {
        log.info("update with restaurantId {} ", restaurantId);
        if (LocalTime.now().isAfter(LocalTime.of(11, 00))) {
            throw new TimeException("Vote change time  is ending");
        }
        Vote vote = new Vote(null, authUser.getUser(), restaurantRepository.getById(restaurantId), LocalDate.now() );
        Optional<Vote> oldVote = voteRepository.findVoteByDateAndUserId(LocalDate.now(), authUser.getUser().getId());
        int id = oldVote.get().getId();
        assureIdConsistent(vote, id);
        voteRepository.save(vote);

    }


}
