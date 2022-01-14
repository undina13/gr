package ru.undina.graduation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "RESTAURANT", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Restaurant extends NamedEntity {
    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
