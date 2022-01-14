package ru.undina.graduation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dish")
public class Dish extends NamedEntity {
    @Column(name = "price", nullable = false)
    private int price;

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
