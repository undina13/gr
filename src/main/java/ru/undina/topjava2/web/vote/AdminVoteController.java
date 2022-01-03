package ru.undina.topjava2.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.undina.topjava2.model.Restaurant;
import ru.undina.topjava2.model.Vote;
import ru.undina.topjava2.repository.VoteRepository;
import ru.undina.topjava2.web.restaurant.AdminRestaurantController;

import java.util.List;
import java.util.Optional;

import static ru.undina.topjava2.web.vote.AdminVoteController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminVoteController {
    static final String REST_URL = "/api/admin/vote";
    VoteRepository voteRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Vote>> get(@PathVariable int id) {
        log.info("get {}", id);
        Optional<Vote> vote = voteRepository.findById(id);
        return ResponseEntity.ok(vote);
    }

    @GetMapping
    public List<Vote> getAll() {
        log.info("Vote getAll");
        return voteRepository.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id) {
        voteRepository.delete(id);
    }
}
