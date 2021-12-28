package ru.undina.topjava2.web.dish;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.undina.topjava2.model.Dish;
import ru.undina.topjava2.repository.DishRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static ru.undina.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class AdminDishController {
    public final static String REST_URL = "/api/admin/dish";
    @Autowired
    DishRepository repository;


    @Operation(
            summary = "Delete dish with ID",
            parameters = {

                    @Parameter(name = "id",
                            description = "The id of dish that needs to be deleted. Use 10 for testing.",
                            content = @Content(examples = {@ExampleObject(value = "10")}),
                            required = true)
            }

    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void delete(@PathVariable int id) {
        log.info("Delete dish with id = {}", id);
        repository.delete(id);
    }

    @GetMapping()
    public List<Dish> getAll() {
        log.info("get all dishes ");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Dish>> get(@PathVariable Integer id) {
        log.info("get dish by id = {} ", id);
        Optional<Dish> dish = repository.findById(id);
        return ResponseEntity.ok(dish);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Dish> createNew(@Valid @RequestBody Dish dish) {
        log.info("create {}", dish);
        checkNew(dish);
        Dish created = repository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable int id, @Valid @RequestBody Dish dish) {
        log.info("update {} with id={}", dish, id);
        Dish updated = repository.getById(id);
        updated.setName(dish.getName());
        updated.setPrice(dish.getPrice());
    }

}
