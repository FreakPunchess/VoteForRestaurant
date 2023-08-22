INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('Three Pigs'),
       ('Mad horse');

INSERT INTO DISH (name, price, dish_date, restaurant_id)
VALUES ('Борщ', 15000, '2023-04-18', 1),
       ('Греча', 5000, '2023-04-18', 1),
       ('Шашлык', 53000, '2023-04-18', 2),
       ('Оливье', 18000, '2023-04-20', 2),
       ('Плов', 30000, '2023-04-20', 2),
       ('Фаршированный перец', 20000, '2023-04-20', 1);

INSERT INTO VOTE (restaurant_id, date_time, user_id)
VALUES (1, '2023-04-18 10:00:00', 2),
       (2, '2023-04-18 11:02:00', 1);