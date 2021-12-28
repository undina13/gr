package ru.undina.topjava2.model;

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
@Table(name = "dish" )
public class Dish extends NamedEntity{
    @Column(name = "price")
    private int price;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_id")
//    @JsonIgnore
//    private Menu menu;

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;

    }

   }
