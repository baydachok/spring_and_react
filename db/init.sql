CREATE TABLE IF NOT EXISTS product_type
(
    product_type_id   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name              VARCHAR(255),
    updated_timestamp TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS product
(
    product_id        BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    price             NUMERIC(8, 2),
    quantity_in_stock INTEGER,
    updated_timestamp TIMESTAMP DEFAULT NOW(),
    description       TEXT,
    image_src         VARCHAR(255),
    name              VARCHAR(255),
    product_type_id   BIGINT REFERENCES product_type (product_type_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS user_account
(
    user_id           BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    password          VARCHAR(255),
    role              VARCHAR(255) CHECK ((role)::text = ANY
                                          ((ARRAY ['ADMIN'::character varying, 'MANAGER'::character varying, 'USER'::character varying])::text[])),
    username          VARCHAR(255),
    updated_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS shop_order
(
    shop_order_id     BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    updated_timestamp TIMESTAMP,
    address           VARCHAR(255),
    order_status      VARCHAR(255) CHECK ((order_status)::text = ANY
                                          ((ARRAY ['PENDING'::character varying, 'COMPLETED'::character varying, 'CANCELED'::character varying])::text[])),
    user_id           BIGINT REFERENCES user_account (user_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS order_record
(
    order_record_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    quantity        INT DEFAULT 1,
    order_id        BIGINT REFERENCES shop_order (shop_order_id) ON DELETE CASCADE,
    product_id      BIGINT REFERENCES product (product_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ai_thread
(
    ai_thread_id      BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    created_timestamp TIMESTAMP,
    user_id           BIGINT REFERENCES user_account (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ai_message
(
    ai_message_id     BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    role              VARCHAR(255),
    message           TEXT,
    created_timestamp TIMESTAMP,
    ai_thread_id      BIGINT REFERENCES ai_thread (ai_thread_id) ON DELETE CASCADE
);

INSERT INTO user_account(password, role, username, updated_timestamp)
VALUES ('$2a$10$L3zNe5VQRjaKn/raFVtYQeh7YYxfAsIbdPRL2H.N0I9R0TsB.4iNq', 'MANAGER', 'manager',
        '2024-05-20 13:24:08.769869');

INSERT INTO product_type(name)
VALUES ('Телефон'),
       ('Часы'),
       ('Колонки'),
       ('Фитнес-браслет');

INSERT INTO product(price, quantity_in_stock, description, image_src, name, product_type_id)
VALUES (29999.99, 8, '
### Характеристики

-   **Дисплей:** 6,7 дюйма, тип матрицы AMOLED, разрешение Full HD+ (2412×1080 пикселей), частота обновления 120 Гц.
-   **Процессор:** 6-нанометровый 8-ядерный MediaTek Helio G99 с частотой работы 2,2 ГГц и графической системой Mali G57MC.
-   **Оперативная память:**  8 ГБ.
-   **Накопитель:**  128 ГБ.
-   **Аккумулятор:** 5000 мА·ч.
-   **Поддержка быстрой зарядки:**  есть, проводная мощностью 18 Вт.',
        '4e60a5f7-66f0-4767-8c3e-76f47f2cbd95.png',
        'Р-ФОН', 1),
       (9999.99, 8, '
### Характеристики  

-   **Бренд:** NoBrand  
-   **Артикул:** производителя X8 PRO/черный  
-   **Модель:** X8 PRO  
-   **Артикул:** X8 PRO/черный  
',
        '5e60c5f7-66f0-4767-8c3e-76f47f2cbd95.jpg',
        'Smart Watch X8', 2),
       (5999.99, 3, '
### Характеристики  

-   **Страна:** Китай  
-   **Гарантия:** 1 год  
-   **Тип:** Умная колонка  
-   **Тип акустической системы:** 1 колонка  
-   **Суммарная мощность системы:** 5 Вт  
-   **Количество динамиков:** 1  
',
        '6e60c5f7-66f0-4767-8c3e-76f47f2cbd95.jpg',
        'Яндекс.Станция Лайт', 3),
       (9999.99, 4, '
### Характеристики  

-   **Гарантия:** 1 год  
-   **Экран:** 10.1"/1200x1920 Пикс  
-   **Встроенная память (ROM):** 64 ГБ  
-   **Оперативная память (RAM):** 4 ГБ  
-   **Количество ядер:** 8  
-   **Частота процессора:** 1.5 ГГц  
-   **Поддержка стандартов:** 4G LTE  
-   **Встроенный модуль Bluetooth:** 5.1  
',
        '7e60c5f7-66f0-4767-8c3e-76f47f2cbd95.jpg',
        'Xiaomi Mi Band 8 RU', 4)
