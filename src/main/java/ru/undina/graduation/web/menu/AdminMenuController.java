package ru.undina.graduation.web.menu;

import com.sun.istack.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.undina.graduation.model.Menu;
import ru.undina.graduation.repository.MenuRepository;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.undina.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.undina.graduation.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminMenuController {
    static final String REST_URL = "/api/admin/menu";
    @Autowired
   protected MenuRepository menuRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    public ResponseEntity<Menu> createNew(@Valid @RequestBody Menu menu) {
        log.info("create {}", menu);
        checkNew(menu);
        Menu created = menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "menu", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Menu delete {}", id);
        menuRepository.delete(id);
    }

    @GetMapping("restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Menu> findAllByRestaurant(@PathVariable int restaurantId) {
        log.info("get Menu by ID for restaurant {}", restaurantId);
        return menuRepository.findAllByRestaurant(restaurantId);
    }

    @GetMapping()
    public List<Menu> getAll() {
        log.info("get all dishes ");
        return menuRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> get(@PathVariable Integer id) {
        log.info("get menu by id = {} ", id);

        return ResponseEntity.of(menuRepository.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    public void update(@PathVariable int id, @Valid @RequestBody Menu menu) {
        log.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        menuRepository.save(menu);
    }

    @GetMapping("/getdate")
    public List<Menu> findAllByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get menu by {} ", date);

        return menuRepository.findAllByDate(date);
    }
}
