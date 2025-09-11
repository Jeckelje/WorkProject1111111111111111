-- Создание схемы workers_schema (если еще не создана)
CREATE SCHEMA IF NOT EXISTS workers_schema;

-- Установка пути поиска для текущей сессии (опционально, для удобства)
SET search_path TO workers_schema, public;

-- Создание последовательности для генерации ID workers (если еще не создана)
CREATE SEQUENCE IF NOT EXISTS workers_schema.workers_id_seq;

-- Создание таблицы workers (если не существует)
CREATE TABLE IF NOT EXISTS workers_schema.workers (
                                                      id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.workers_id_seq'),
                                                      name VARCHAR(255) NOT NULL,
                                                      surname VARCHAR(255) NOT NULL,
                                                      patronymic_name VARCHAR(255),
                                                      job_title VARCHAR(255) NOT NULL,
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

-- Очистка существующих данных workers (если нужно перезаполнить)
TRUNCATE TABLE workers_schema.workers RESTART IDENTITY CASCADE;

-- После TRUNCATE TABLE workers_schema.workers
ALTER SEQUENCE workers_schema.workers_id_seq RESTART WITH 1;

-- Вставка тестовых данных workers
INSERT INTO workers_schema.workers
(name, surname, patronymic_name, job_title,
 is_vacated, is_vacated_in_3_months, is_category_next_year,
 is_train_next_month, is_ecc_next_month, is_qualification_next_month,
 is_category_next_3_month, is_bday5, b_day)
VALUES
    ('Иван', 'Иванов', 'Иванович', 'Ведущий эксперт', false, true, false, true, false, true, false, false, '1990-05-20'),
    ('Петр', 'Петров', 'Петрович', 'Старший эксперт', true, false, true, false, true, false, true, true, '1985-07-10'),
    ('Мария', 'Сидорова', 'Алексеевна', 'Главный эксперт', false, false, true, true, false, false, true, false, '1992-12-01'),
    ('Анна', 'Смирнова', 'Сергеевна', 'Эксперт', true, true, false, false, true, true, false, true, '1995-09-14'),
    ('Сергей', 'Кузнецов', 'Васильевич', 'Младший эксперт', false, true, true, false, false, true, true, false, '1988-03-03');

-- Комментарий к таблице и колонкам workers
COMMENT ON TABLE workers_schema.workers IS 'Таблица для хранения данных о сотрудниках';
COMMENT ON COLUMN workers_schema.workers.id IS 'Уникальный идентификатор сотрудника';
COMMENT ON COLUMN workers_schema.workers.name IS 'Имя сотрудника';
COMMENT ON COLUMN workers_schema.workers.surname IS 'Фамилия сотрудника';
COMMENT ON COLUMN workers_schema.workers.patronymic_name IS 'Отчество сотрудника';
COMMENT ON COLUMN workers_schema.workers.job_title IS 'Должность сотрудника';
COMMENT ON COLUMN workers_schema.workers.is_vacated IS 'Находится ли в отпуске';
COMMENT ON COLUMN workers_schema.workers.is_vacated_in_3_months IS 'Будет ли в отпуске в ближайшие 3 месяца';
COMMENT ON COLUMN workers_schema.workers.is_category_next_year IS 'Будет ли категория в следующем году';
COMMENT ON COLUMN workers_schema.workers.is_train_next_month IS 'Стажировка в следующем месяце';
COMMENT ON COLUMN workers_schema.workers.is_ecc_next_month IS 'Будет ли ЭКК в следующем месяце';
COMMENT ON COLUMN workers_schema.workers.is_qualification_next_month IS 'Будет ли повышение квалификации в следующем месяце';
COMMENT ON COLUMN workers_schema.workers.is_category_next_3_month IS 'Будет ли категория в следующих 3 месяцев';
COMMENT ON COLUMN workers_schema.workers.is_bday5 IS 'Кратен ли день рождения 5';
COMMENT ON COLUMN workers_schema.workers.b_day IS 'Дата рождения сотрудника';

-- Создание последовательности для генерации ID vacation (если еще не создана)
CREATE SEQUENCE IF NOT EXISTS workers_schema.vacation_id_seq;

-- Создание таблицы vacation (если не существует)
CREATE TABLE IF NOT EXISTS workers_schema.vacation (
                                                       id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.vacation_id_seq'),
                                                       worker_id BIGINT NOT NULL,
                                                       vacation_title VARCHAR(255),
                                                       vacation_start_date DATE,
                                                       vacation_end_date DATE,
                                                       description TEXT,
                                                       CONSTRAINT fk_vacation_worker
                                                           FOREIGN KEY (worker_id)
                                                               REFERENCES workers_schema.workers(id)
                                                               ON DELETE CASCADE
);

-- Создание индекса для улучшения производительности запросов по worker_id
CREATE INDEX IF NOT EXISTS idx_vacation_worker_id ON workers_schema.vacation(worker_id);

-- Очистка существующих данных vacation (если нужно перезаполнить)
TRUNCATE TABLE workers_schema.vacation RESTART IDENTITY;

-- Вставка тестовых данных для отпусков (используем ID из только что вставленных workers)
INSERT INTO workers_schema.vacation (worker_id, vacation_title, vacation_start_date, vacation_end_date, description) VALUES
                                                                                                                         (1, 'Плановый ежегодный оплачиваемый отпуск', '2024-07-01', '2024-07-28', 'Ежегодный отпуск по графику'),
                                                                                                                         (1, 'Дополнительный отпуск', '2024-12-15', '2024-12-22', 'Дополнительные дни за ненормированный рабочий день'),
                                                                                                                         (2, 'Отпуск по семейным обстоятельствам', '2024-08-10', '2024-08-20', 'Неоплачиваемый отпуск'),
                                                                                                                         (3, 'Ежегодный отпуск', '2024-06-01', '2024-06-28', 'Летний отпуск'),
                                                                                                                         (4, 'Учебный отпуск', '2024-09-01', '2024-09-14', 'Отпуск для сдачи сессии'),
                                                                                                                         (5, 'Творческий отпуск', '2024-10-01', '2024-10-31', 'Отпуск для написания научной работы'),
                                                                                                                         (2, 'Ежегодный отпуск', '2024-11-01', '2024-11-28', 'Осенний отпуск');

-- Комментарий к таблице и колонкам vacation
COMMENT ON TABLE workers_schema.vacation IS 'Таблица для хранения данных об отпусках сотрудников';
COMMENT ON COLUMN workers_schema.vacation.id IS 'Уникальный идентификатор отпуска';
COMMENT ON COLUMN workers_schema.vacation.worker_id IS 'ID сотрудника (внешний ключ)';
COMMENT ON COLUMN workers_schema.vacation.vacation_title IS 'Название/тип отпуска';
COMMENT ON COLUMN workers_schema.vacation.vacation_start_date IS 'Дата начала отпуска';
COMMENT ON COLUMN workers_schema.vacation.vacation_end_date IS 'Дата окончания отпуска';
COMMENT ON COLUMN workers_schema.vacation.description IS 'Описание отпуска';

-- Создание последовательности для генерации ID train (если еще не создана)
CREATE SEQUENCE IF NOT EXISTS workers_schema.train_id_seq;

-- Создание таблицы train (если не существует)
CREATE TABLE IF NOT EXISTS workers_schema.train (
                                                    id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.train_id_seq'),
                                                    worker_id BIGINT NOT NULL,
                                                    train_title VARCHAR(255),
                                                    train_start_date DATE,
                                                    train_end_date DATE,
                                                    description TEXT,
                                                    CONSTRAINT fk_train_worker
                                                        FOREIGN KEY (worker_id)
                                                            REFERENCES workers_schema.workers(id)
                                                            ON DELETE CASCADE
);

-- Создание индекса для улучшения производительности запросов по worker_id
CREATE INDEX IF NOT EXISTS idx_train_worker_id ON workers_schema.train(worker_id);

-- Очистка существующих данных train (если нужно перезаполнить)
TRUNCATE TABLE workers_schema.train RESTART IDENTITY;

-- Вставка тестовых данных для стажировок
INSERT INTO workers_schema.train (worker_id, train_title, train_start_date, train_end_date, description) VALUES
                                                                                                             (1, 'Плановая стажировка', '2024-01-15', '2024-01-30', 'Ежегодная плановая стажировка по технике безопасности'),
                                                                                                             (1, 'Специализированная стажировка', '2024-03-10', '2024-03-20', 'Стажировка по новому оборудованию'),
                                                                                                             (2, 'Вводная стажировка', '2024-02-01', '2024-02-15', 'Стажировка для новых сотрудников'),
                                                                                                             (3, 'Повышение квалификации', '2024-04-05', '2024-04-12', 'Стажировка по новым методикам работы'),
                                                                                                             (4, 'Межотделенческая стажировка', '2024-05-20', '2024-05-25', 'Обмен опытом между отделами'),
                                                                                                             (5, 'Спецкурс по программному обеспечению', '2024-06-10', '2024-06-14', 'Обучение работе с новым ПО'),
                                                                                                             (2, 'Стажировка у партнеров', '2024-07-01', '2024-07-05', 'Выездная стажировка у компаний-партнеров');
-- Создание последовательности для генерации ID qualification (если еще не создана)
CREATE SEQUENCE IF NOT EXISTS workers_schema.qualification_id_seq;

-- Создание таблицы qualification (если не существует)
CREATE TABLE IF NOT EXISTS workers_schema.qualification (
                                                            id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.qualification_id_seq'),
                                                            worker_id BIGINT NOT NULL,
                                                            qualification_title VARCHAR(255),
                                                            qualification_start_date DATE,
                                                            qualification_end_date DATE,
                                                            description TEXT,
                                                            CONSTRAINT fk_qualification_worker
                                                                FOREIGN KEY (worker_id)
                                                                    REFERENCES workers_schema.workers(id)
                                                                    ON DELETE CASCADE
);

-- Создание индекса для улучшения производительности запросов по worker_id
CREATE INDEX IF NOT EXISTS idx_qualification_worker_id ON workers_schema.qualification(worker_id);

-- Очистка существующих данных qualification (если нужно перезаполнить)
TRUNCATE TABLE workers_schema.qualification RESTART IDENTITY;

-- Вставка тестовых данных для квалификаций
INSERT INTO workers_schema.qualification (worker_id, qualification_title, qualification_start_date, qualification_end_date, description) VALUES
                                                                                                                                             (1, 'Получение квалификации эксперта', '2023-10-23', '2026-09-30', 'Основная профессиональная квалификация'),
                                                                                                                                             (1, 'Повышение категории', '2024-03-15', '2027-03-14', 'Переход на высшую категорию'),
                                                                                                                                             (2, 'Сертификация по ISO 9001', '2023-05-10', '2026-05-09', 'Международная сертификация качества'),
                                                                                                                                             (3, 'Квалификация ведущего специалиста', '2024-01-20', '2027-01-19', 'Присвоение квалификации ведущего специалиста'),
                                                                                                                                             (4, 'Профессиональная переподготовка', '2023-11-01', '2026-10-31', 'Переподготовка по новому направлению'),
                                                                                                                                             (5, 'Сертификация по охране труда', '2024-02-05', '2027-02-04', 'Курс повышения квалификации по охране труда'),
                                                                                                                                             (2, 'Квалификация аудитора', '2023-08-12', '2026-08-11', 'Получение квалификации внутреннего аудитора');
-- Создание последовательности для генерации ID ECC (если еще не создана)
CREATE SEQUENCE IF NOT EXISTS workers_schema.ecc_id_seq;

-- Создание таблицы ECC (если не существует)
CREATE TABLE IF NOT EXISTS workers_schema.ECC (
                                                  id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.ecc_id_seq'),
                                                  worker_id BIGINT NOT NULL,
                                                  ecc_title VARCHAR(255),
                                                  ecc_start_date DATE,
                                                  ecc_end_date DATE,
                                                  description TEXT,
                                                  CONSTRAINT fk_ecc_worker
                                                      FOREIGN KEY (worker_id)
                                                          REFERENCES workers_schema.workers(id)
                                                          ON DELETE CASCADE
);

-- Создание индекса для улучшения производительности запросов по worker_id
CREATE INDEX IF NOT EXISTS idx_ecc_worker_id ON workers_schema.ECC(worker_id);

-- Очистка существующих данных ECC (если нужно перезаполнить)
TRUNCATE TABLE workers_schema.ECC RESTART IDENTITY;

-- Вставка тестовых данных для ЭКК
INSERT INTO workers_schema.ECC (worker_id, ecc_title, ecc_start_date, ecc_end_date, description) VALUES
                                                                                                     (1, 'Плановая внеочередная проверка знаний', '2024-09-23', '2024-09-30', 'Ежегодная плановая проверка знаний'),
                                                                                                     (1, 'Внеплановая проверка по новым нормативам', '2024-11-15', '2024-11-20', 'Проверка знаний новых нормативных документов'),
                                                                                                     (2, 'Очередная аттестация', '2024-10-10', '2024-10-15', 'Регулярная аттестация сотрудника'),
                                                                                                     (3, 'Внеочередная проверка после отпуска', '2024-08-01', '2024-08-05', 'Проверка знаний после длительного отсутствия'),
                                                                                                     (4, 'Проверка знаний по технике безопасности', '2024-12-05', '2024-12-10', 'Ежегодная проверка знаний ТБ'),
                                                                                                     (5, 'Аттестация на соответствие должности', '2024-07-20', '2024-07-25', 'Проверка профессиональных знаний'),
                                                                                                     (2, 'Внеплановая проверка по итогам года', '2024-12-20', '2024-12-25', 'Итоговая проверка знаний за год');
-- Создание последовательности для генерации ID category (если еще не создана)
CREATE SEQUENCE IF NOT EXISTS workers_schema.category_id_seq;

-- Создание таблицы category (если не существует)
CREATE TABLE IF NOT EXISTS workers_schema.category (
                                                       id BIGINT PRIMARY KEY DEFAULT nextval('workers_schema.category_id_seq'),
                                                       worker_id BIGINT NOT NULL,
                                                       category_title VARCHAR(255),
                                                       category_start_date DATE,
                                                       category_end_date DATE,
                                                       description TEXT,
                                                       CONSTRAINT fk_category_worker
                                                           FOREIGN KEY (worker_id)
                                                               REFERENCES workers_schema.workers(id)
                                                               ON DELETE CASCADE
);

-- Создание индекса для улучшения производительности запросов по worker_id
CREATE INDEX IF NOT EXISTS idx_category_worker_id ON workers_schema.category(worker_id);

-- Очистка существующих данных category (если нужно перезаполнить)
TRUNCATE TABLE workers_schema.category RESTART IDENTITY;

-- Вставка тестовых данных для категорий
INSERT INTO workers_schema.category (worker_id, category_title, category_start_date, category_end_date, description) VALUES
                                                                                                                         (1, 'Высшая категория эксперта', '2024-09-18', '2026-11-21', 'Присвоение высшей квалификационной категории'),
                                                                                                                         (1, 'Первая категория специалиста', '2023-05-15', '2025-05-14', 'Первая квалификационная категория'),
                                                                                                                         (2, 'Вторая категория эксперта', '2024-03-10', '2026-03-09', 'Вторая квалификационная категория'),
                                                                                                                         (3, 'Высшая категория ведущего специалиста', '2024-01-20', '2026-01-19', 'Высшая категория по основной специальности'),
                                                                                                                         (4, 'Первая категория младшего эксперта', '2023-11-01', '2025-10-31', 'Первая категория для младшего персонала'),
                                                                                                                         (5, 'Специалист высшей категории', '2024-02-05', '2026-02-04', 'Высшая категория с правом проведения экспертиз'),
                                                                                                                         (2, 'Категория главного эксперта', '2023-08-12', '2025-08-11', 'Присвоение категории главного эксперта');

-- Комментарий к таблице и колонкам category
COMMENT ON TABLE workers_schema.category IS 'Таблица для хранения данных о категориях сотрудников';
COMMENT ON COLUMN workers_schema.category.id IS 'Уникальный идентификатор категории';
COMMENT ON COLUMN workers_schema.category.worker_id IS 'ID сотрудника (внешний ключ)';
COMMENT ON COLUMN workers_schema.category.category_title IS 'Название категории';
COMMENT ON COLUMN workers_schema.category.category_start_date IS 'Дата получения категории';
COMMENT ON COLUMN workers_schema.category.category_end_date IS 'Дата окончания категории';
COMMENT ON COLUMN workers_schema.category.description IS 'Описание категории';