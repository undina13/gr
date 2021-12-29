INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);


INSERT INTO RESTARAUNT (name)
VALUES ('Снежинка'),
       ('Новогодний'),
       ('Дед Мороз'),
       ('Прошлогодний снег');

INSERT INTO DISH(name, price)
VALUES ('Сельдь под шубой', 200),
       ('Заливная рыба', 300),
       ('Оливье', 150),
       ('Шампанское', 100),
       ('Мандарины', 100),
       ('Бутерброды с икрой', 500),
       ('Перепел в сливочном соусе', 1500);



INSERT INTO MENU(restaraunt_id, date_time)
VALUES (1, CURRENT_DATE),
       (2, CURRENT_DATE),
       (3, CURRENT_DATE),
       (1, CURRENT_DATE-1),

       (3, CURRENT_DATE-1);

INSERT INTO MENU_DISH (menu_id, dish_id)
VALUES  (1, 1), (1, 2), (1,3),
        (2, 3), (2, 4), (2,5),
        (3, 1), (3, 4), (3,6),
        (4, 1), (4, 6), (4,3),
        (5, 6), (5, 2), (5,1);

INSERT INTO VOTE(user_id, restaurant_id, vote_day)
VALUES ( 1, 3, CURRENT_DATE-1),
       ( 2, 3, CURRENT_DATE);

