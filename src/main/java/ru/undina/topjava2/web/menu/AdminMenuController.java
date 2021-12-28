package ru.undina.topjava2.web.menu;

import com.sun.istack.Nullable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.webjars.NotFoundException;
import ru.undina.topjava2.model.Dish;
import ru.undina.topjava2.model.Menu;
import ru.undina.topjava2.model.Restaurant;
import ru.undina.topjava2.repository.DishRepository;
import ru.undina.topjava2.repository.MenuRepository;
import ru.undina.topjava2.repository.RestaurantRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminMenuController {
    static final String REST_URL = "/api/admin/restaraut/{restarautId}/menu";

    @Autowired
    MenuRepository menuRepository;
     DishRepository dishRepository;
   RestaurantRepository restaurantRepository;

    @Operation(
            summary = "Create menu for the restaurant",

            parameters = {
                    @Parameter(name = "restaurantId",

                          //  content = @Content(examples = {@ExampleObject(value = "3")}),
                            required = true),
                    @Parameter(name = "forDate",
                            description = "For date. Format yyyy-MM-dd."
                          //  content = @Content(examples = {@ExampleObject(value = "2022-02-21")})
                    )
            }
    )
    @PostMapping()
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Menu> createMenu(@PathVariable int restaurantId,
                                           @RequestParam @Nullable
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                           @RequestParam List<Integer> dishes) throws NotFoundException {
        log.info("create menu for the Restaurant id = {}", restaurantId);
        if ((dishes.size() < 2) || (dishes.size() > 5)) {
            throw new NotFoundException("Wrong dishes number");
        }

        Restaurant rest = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id= " + restaurantId + " not found"));

        Menu menu = new Menu( null, rest, date == null ? LocalDate.now() : date, (Dish) dishes);

        Menu created = menuRepository.save(menu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
