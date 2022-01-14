INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);


INSERT INTO RESTAURANT (name)
VALUES ('Снежинка'),
       ('Новогодний'),
       ('Дед Мороз'),
       ('Прошлогодний снег');

INSERT INTO DISH(name, price, RESTAURANT_ID )
VALUES ('Сельдь под шубой', 200, 1),
       ('Заливная рыба', 300, 1),
       ('Оливье', 150, 2),
       ('Шампанское', 100, 2),
       ('Мандарины', 100, 3),
       ('Бутерброды с икрой', 500, 3),
       ('Перепел в сливочном соусе', 1500, 3);



INSERT INTO MENU(RESTAURANT_ID, date)
VALUES (1, CURRENT_DATE),
       (2, CURRENT_DATE),
       (3, CURRENT_DATE),
       (1, CURRENT_DATE-1),

       (2, CURRENT_DATE-1);

INSERT INTO MENU_DISH (menu_id, dish_id)
VALUES  (1, 1), (1, 2),
        (2, 3), (2, 4),
        (3, 5), (3, 6),
        (4, 1), (4, 2),
        (5, 3), (5, 4);

INSERT INTO VOTE(user_id, restaurant_id, vote_day)
VALUES ( 1, 1, CURRENT_DATE-1),
       ( 2, 1, CURRENT_DATE);

