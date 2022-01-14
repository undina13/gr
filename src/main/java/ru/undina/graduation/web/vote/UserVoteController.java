package ru.undina.graduation.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.undina.graduation.error.TimeException;
import ru.undina.graduation.model.Vote;
import ru.undina.graduation.repository.VoteRepository;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalTime;

import static ru.undina.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.undina.graduation.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserVoteController {
    static final String REST_URL = "/api/vote";
   private final VoteRepository voteRepository;

    public UserVoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @RequestBody Vote vote) {
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
