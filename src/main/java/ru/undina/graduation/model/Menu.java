package ru.undina.graduation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"restaraunt_id", "date_time"}, name = "menu_unique_restaurant_date_idx")})
public class Menu extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "restaraunt_id", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDate date;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "menu_dish",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dish;

    public Menu(Integer id, Restaurant restaurant, LocalDate date, Dish... dish) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
        this.dish = List.of(dish);
    }

    public Menu(Integer id, Restaurant restaurant, LocalDate date, List<Dish> dishes) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
        this.dish = dishes;
    }
}
