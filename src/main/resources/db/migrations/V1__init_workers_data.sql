DROP SCHEMA IF EXISTS workers_schema CASCADE;

-- Создание схемы
CREATE SCHEMA IF NOT EXISTS workers_schema;
SET search_path TO workers_schema, public;

-- workers
CREATE SEQUENCE IF NOT EXISTS workers_schema.workers_id_seq;

CREATE TABLE IF NOT EXISTS workers_schema.workers (
                                                      id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.workers_id_seq'),
                                                      name VARCHAR(255) NOT NULL,
                                                      surname VARCHAR(255) NOT NULL,
                                                      patronymic_name VARCHAR(255),
                                                      job_title VARCHAR(255) NOT NULL,
                                                      department VARCHAR(255) NOT NULL, -- 👈 добавлен отдел
                                                      is_vacated BOOLEAN DEFAULT FALSE,
                                                      is_vacated_in_3_months BOOLEAN DEFAULT FALSE,
                                                      is_category_next_year BOOLEAN DEFAULT FALSE,
                                                      is_train_next_month BOOLEAN DEFAULT FALSE,
                                                      is_ecc_next_month BOOLEAN DEFAULT FALSE,
                                                      is_qualification_next_month BOOLEAN DEFAULT FALSE,
                                                      is_category_next_3_month BOOLEAN DEFAULT FALSE,
                                                      is_bday5 BOOLEAN DEFAULT FALSE,
                                                      b_day DATE
);

create table workers_schema.users
(
    id       bigserial primary key,
    username varchar(255) not null unique,
    password varchar(255) not null
);

create table workers_schema.users_roles
(
    user_id bigint       not null,
    roles   varchar(255) not null,
    primary key (user_id, roles),
    constraint fk_users_roles_users foreign key (user_id) references workers_schema.users (id) on delete cascade on update no action
);

TRUNCATE TABLE workers_schema.workers RESTART IDENTITY CASCADE;
ALTER SEQUENCE workers_schema.workers_id_seq RESTART WITH 1;


insert into workers_schema.users (username, password)
values ('users', '$2a$10$20nAsN7bcXgxYsDMMdkKJO4qROJLupugHXCDbpMuwAHK3oJLonm/.'),
       ('manager', '$2a$10$TJ3X3l3wIqWqi8xfCb0gzOWd8R3yFp.GIz8mnRoORbPZtre0rEKoe');--manager

insert into workers_schema.users_roles(user_id, roles)
values (1, 'ROLE_USER'),
       (2, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');
-- 10 сотрудников, флаги выставлены по событиям
INSERT INTO workers_schema.workers
(name, surname, patronymic_name, job_title, department,
 is_vacated, is_vacated_in_3_months, is_category_next_year,
 is_train_next_month, is_ecc_next_month, is_qualification_next_month,
 is_category_next_3_month, is_bday5, b_day)
VALUES
    ('Иван', 'Иванов', 'Иванович', 'Ведущий эксперт', 'Отдел аналитики',
     false, false, false, false, false, false, false, false, '1990-05-20'),

    ('Петр', 'Петров', 'Петрович', 'Старший эксперт', 'Отдел продаж',
     true, true, true, true, true, true, true, true, '1985-07-10'),

    ('Мария', 'Сидорова', 'Алексеевна', 'Главный эксперт', 'Отдел HR',
     false, false, true, false, false, false, false, false, '1992-12-01'),

    ('Анна', 'Смирнова', 'Сергеевна', 'Эксперт', 'Отдел маркетинга',
     false, false, false, true, false, false, false, true, '1995-09-14'),

    ('Сергей', 'Кузнецов', 'Васильевич', 'Младший эксперт', 'Отдел IT',
     false, false, false, false, true, false, false, false, '1988-03-03'),

    ('Ольга', 'Морозова', 'Игоревна', 'Эксперт', 'Отдел обучения',
     false, false, false, false, false, true, false, false, '1991-11-25'),

    ('Алексей', 'Волков', 'Степанович', 'Старший эксперт', 'Отдел качества',
     false, false, false, false, false, false, true, false, '1987-06-15'),

    ('Татьяна', 'Мельникова', 'Андреевна', 'Ведущий эксперт', 'Отдел аналитики',
     false, true, false, false, true, false, false, false, '1993-09-05'),

    ('Николай', 'Федоров', 'Дмитриевич', 'Эксперт', 'Отдел IT',
     false, false, false, false, false, false, false, false, '1989-04-18'),

    ('Елена', 'Громова', 'Александровна', 'Главный эксперт', 'Отдел продаж',
     false, true, false, false, false, false, false, false, '1994-02-28');

----------------------------------------------------
-- vacation
CREATE SEQUENCE IF NOT EXISTS workers_schema.vacation_id_seq;
CREATE TABLE IF NOT EXISTS workers_schema.vacation (
                                                       id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.vacation_id_seq'),
                                                       worker_id BIGINT NOT NULL,
                                                       vacation_title VARCHAR(255),
                                                       vacation_start_date DATE,
                                                       vacation_end_date DATE,
                                                       description TEXT,
                                                       CONSTRAINT fk_vacation_worker FOREIGN KEY (worker_id)
                                                           REFERENCES workers_schema.workers(id) ON DELETE CASCADE
);

TRUNCATE TABLE workers_schema.vacation RESTART IDENTITY;

INSERT INTO workers_schema.vacation (worker_id, vacation_title, vacation_start_date, vacation_end_date, description) VALUES
                                                                                                                         (1, 'Ежегодный отпуск', '2025-09-01', '2025-09-25', 'Текущий отпуск'),
                                                                                                                         (2, 'Плановый отпуск', '2025-07-10', '2025-08-25', 'Будущий отпуск через 3 месяца'),
                                                                                                                         (5, 'Осенний отпуск', '2025-10-05', '2025-10-20', 'Отдых в октябре'),
                                                                                                                         (10, 'Зимний отпуск', '2025-12-15', '2025-12-31', 'Новый год');

----------------------------------------------------
-- train
CREATE SEQUENCE IF NOT EXISTS workers_schema.train_id_seq;
CREATE TABLE IF NOT EXISTS workers_schema.train (
                                                    id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.train_id_seq'),
                                                    worker_id BIGINT NOT NULL,
                                                    train_title VARCHAR(255),
                                                    train_start_date DATE,
                                                    train_end_date DATE,
                                                    description TEXT,
                                                    CONSTRAINT fk_train_worker FOREIGN KEY (worker_id)
                                                        REFERENCES workers_schema.workers(id) ON DELETE CASCADE
);

TRUNCATE TABLE workers_schema.train RESTART IDENTITY;

INSERT INTO workers_schema.train (worker_id, train_title, train_start_date, train_end_date, description) VALUES
                                                                                                             (4, 'Вводная стажировка', '2025-10-01', '2025-10-07', 'Стажировка в следующем месяце'),
                                                                                                             (6, 'Курс повышения квалификации', '2025-09-20', '2025-09-30', 'Учебная стажировка'),
                                                                                                             (9, 'Обучение новому ПО', '2025-11-10', '2025-11-20', 'Курс без флагов');

----------------------------------------------------
-- qualification
CREATE SEQUENCE IF NOT EXISTS workers_schema.qualification_id_seq;
CREATE TABLE IF NOT EXISTS workers_schema.qualification (
                                                            id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.qualification_id_seq'),
                                                            worker_id BIGINT NOT NULL,
                                                            qualification_title VARCHAR(255),
                                                            qualification_start_date DATE,
                                                            qualification_end_date DATE,
                                                            description TEXT,
                                                            CONSTRAINT fk_qualification_worker FOREIGN KEY (worker_id)
                                                                REFERENCES workers_schema.workers(id) ON DELETE CASCADE
);

TRUNCATE TABLE workers_schema.qualification RESTART IDENTITY;

INSERT INTO workers_schema.qualification (worker_id, qualification_title, qualification_start_date, qualification_end_date, description) VALUES
                                                                                                                                             (3, 'Основная квалификация', '2024-01-01', '2027-01-01', 'Долгосрочная квалификация'),
                                                                                                                                             (6, 'Повышение квалификации', '2025-09-18', '2025-09-25', 'Событие в следующем месяце'),
                                                                                                                                             (8, 'Аттестация', '2025-11-01', '2025-11-10', 'Просто событие');

----------------------------------------------------
-- ecc
CREATE SEQUENCE IF NOT EXISTS workers_schema.ecc_id_seq;
CREATE TABLE IF NOT EXISTS workers_schema.ecc (
                                                  id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.ecc_id_seq'),
                                                  worker_id BIGINT NOT NULL,
                                                  ecc_title VARCHAR(255),
                                                  ecc_start_date DATE,
                                                  ecc_end_date DATE,
                                                  description TEXT,
                                                  CONSTRAINT fk_ecc_worker FOREIGN KEY (worker_id)
                                                      REFERENCES workers_schema.workers(id) ON DELETE CASCADE
);

TRUNCATE TABLE workers_schema.ecc RESTART IDENTITY;

INSERT INTO workers_schema.ecc (worker_id, ecc_title, ecc_start_date, ecc_end_date, description) VALUES
                                                                                                     (5, 'Проверка знаний', '2025-09-20', '2025-09-25', 'ЭКК в следующем месяце'),
                                                                                                     (7, 'Регулярная аттестация', '2026-03-01', '2026-03-05', 'Событие без флага');

----------------------------------------------------
-- category
CREATE SEQUENCE IF NOT EXISTS workers_schema.category_id_seq;
CREATE TABLE IF NOT EXISTS workers_schema.category (
                                                       id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.category_id_seq'),
                                                       worker_id BIGINT NOT NULL,
                                                       category_title VARCHAR(255),
                                                       category_start_date DATE,
                                                       category_end_date DATE,
                                                       description TEXT,
                                                       CONSTRAINT fk_category_worker FOREIGN KEY (worker_id)
                                                           REFERENCES workers_schema.workers(id) ON DELETE CASCADE
);

TRUNCATE TABLE workers_schema.category RESTART IDENTITY;

INSERT INTO workers_schema.category (worker_id, category_title, category_start_date, category_end_date, description) VALUES
                                                                                                                         (3, 'Высшая категория', '2025-01-01', '2026-12-31', 'Категория в следующем году'),
                                                                                                                         (7, 'Первая категория', '2025-11-15', '2027-11-14', 'Категория через 3 месяца'),
                                                                                                                         (9, 'Вторая категория', '2024-05-10', '2026-05-09', 'Без флага');

----------------------------------------------------
-- achievements
CREATE SEQUENCE IF NOT EXISTS workers_schema.achievements_id_seq;
CREATE TABLE IF NOT EXISTS workers_schema.achievements (
                                                           id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.achievements_id_seq'),
                                                           worker_id BIGINT NOT NULL,
                                                           description TEXT,
                                                           achievement_date DATE,
                                                           CONSTRAINT fk_achievements_worker FOREIGN KEY (worker_id)
                                                               REFERENCES workers_schema.workers(id) ON DELETE CASCADE
);

TRUNCATE TABLE workers_schema.achievements RESTART IDENTITY;

INSERT INTO workers_schema.achievements (worker_id, description, achievement_date) VALUES
                                                                                       (1, 'Почетная грамота', '2024-09-18'),
                                                                                       (1, 'Почетная грамота1', '2024-09-19'),
                                                                                       (1, 'Почетная грамота2', '2024-09-20'),
                                                                                       (1, 'Почетная грамота3', '2024-09-21'),
                                                                                       (1, 'Почетная грамота4', '2024-09-22'),
                                                                                       (3, 'Награда за инновации', '2025-05-10'),
                                                                                       (4, 'Сертификат на конференции', '2025-03-12'),
                                                                                       (5, 'Премия отдела', '2025-07-01'),
                                                                                       (6, 'Лучший наставник', '2025-08-15'),
                                                                                       (7, 'Отметка за квалификацию', '2025-09-05'),
                                                                                       (8, 'Приз за научную работу', '2025-11-11'),
                                                                                       (9, 'Диплом молодого специалиста', '2025-04-22'),
                                                                                       (10, 'Награда руководства', '2025-06-30');
