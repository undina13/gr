package ru.undina.graduation.web.dish;


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
import org.webjars.NotFoundException;
import ru.undina.graduation.model.Dish;
import ru.undina.graduation.model.Restaurant;
import ru.undina.graduation.repository.DishRepository;
import ru.undina.graduation.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.undina.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.undina.graduation.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class AdminDishController {
    public final static String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

  private final  DishRepository repository;
  RestaurantRepository restaurantRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete dish with id = {}", id);

        repository.deleteExisted(id);
    }

    @GetMapping()
       public List<Dish> getAll(@PathVariable Integer restaurantId) {
        log.info("get dishes for restaurant with id = {}", restaurantId);

        return repository.getDishesByRestaurantId(restaurantId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable Integer id,  @PathVariable Integer restaurantId) {
        log.info("get dish by id = {} ", id);
        Dish dish = repository.getById(id);
        assureIdConsistent(dish.getRestaurant(), restaurantId);
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Dish> createNew(@Valid @RequestBody Dish dish,  @PathVariable Integer restaurantId) {
        log.info("create {}", dish);
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        Dish created = repository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId,created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable int id, @Valid @RequestBody Dish dish,  @PathVariable Integer restaurantId) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        Dish updated = repository.getById(id);
        assureIdConsistent(updated.getRestaurant(), restaurantId);
       repository.save(dish);
    }
}
