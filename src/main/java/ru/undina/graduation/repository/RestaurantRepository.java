package ru.undina.graduation.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.undina.graduation.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant>{

}
