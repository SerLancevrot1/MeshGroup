insert into users (id, age, email, name)
 values (1, 23, 'limon.8bit@gmail.com', 'Maxim Solodkov'),
        (2, 25, 'another_user@mail.ru', 'Petr Petrov');

insert into phones (id, value, user_id)
 values (1, '89998739697', 1),
        (2, '89991234567', 2),
        (3, '81122233312', 1);

insert into profiles(id, cash, user_id)
 values (1, 100, 1),
        (2, 50.25, 2);

