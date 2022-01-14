package ru.undina.graduation.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.undina.graduation.model.Dish;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish>{
    @Transactional
    @Modifying
    @Query("DELETE FROM Dish u WHERE u.id=:id")
    int delete(int id);
}
