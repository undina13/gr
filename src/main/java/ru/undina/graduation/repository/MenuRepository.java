package ru.undina.graduation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.undina.graduation.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    List<Menu> findAllByDateAndRestaurantId(LocalDate date, int RestaurantId);

    List<Menu> findAllByDate(LocalDate date);

    @Query("SELECT m FROM Menu m  WHERE  m.restaurant.id = :RestaurantId ")
    List<Menu> findAllByRestaurant(int RestaurantId);
}
