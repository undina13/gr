package ru.undina.topjava2.web.vote;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.undina.topjava2.model.Vote;
import ru.undina.topjava2.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.undina.topjava2.web.vote.AdminVoteController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminVoteController {
    static final String REST_URL = "/api/admin/vote";
    VoteRepository voteRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(voteRepository.findById(id));
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

    @GetMapping("/today")
    public List<Vote> findAllByToday() {
        log.info("get votes by today ");
        LocalDate date = LocalDate.now();
        return voteRepository.findAllByDate(date);
    }

    @GetMapping("/user{id}")
    public List<Vote> getAllByUserId(@PathVariable @Nullable int id) {
        log.info("get menu by userid{} ", id);

        return voteRepository.getAllByUserId(id);
    }
}
