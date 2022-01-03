package ru.undina.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.undina.topjava2.error.TimeException;
import ru.undina.topjava2.model.Menu;
import ru.undina.topjava2.model.Restaurant;
import ru.undina.topjava2.model.Vote;
import ru.undina.topjava2.repository.RestaurantRepository;
import ru.undina.topjava2.repository.VoteRepository;
import ru.undina.topjava2.web.AuthUser;
import ru.undina.topjava2.web.SecurityUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.undina.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.undina.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserVoteController {
    static final String REST_URL = "/api/vote";
    VoteRepository voteRepository;

    public UserVoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Vote> create( @Valid @RequestBody Vote vote) {
        log.info("create {}", vote);
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
    public void update(@RequestBody @Valid Vote vote, @PathVariable int id) throws TimeException {
        log.info("update {} with id={}", vote, id);
        assureIdConsistent(vote, id);
        if (LocalTime.now().isAfter(LocalTime.of(11, 00))) {
          throw new TimeException("Vote change time  is ending");
       }
        voteRepository.save(vote);
    }




}
