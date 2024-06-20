--Users
DROP SEQUENCE IF EXISTS user_seq;
create sequence user_seq start 1 increment 1;

DROP TABLE IF EXISTS users CASCADE;
create table users
(
    id        int8 not null,
    archive   boolean not null,
    email     varchar(255),
    name      varchar(255),
    password  varchar(255),
    role      varchar(255),
    bucket_id int8,
    primary key (id)
);
--Bucket
DROP SEQUENCE IF EXISTS bucket_seq;
create sequence bucket_seq start 1 increment 1;

DROP TABLE IF EXISTS buckets CASCADE;
create table buckets
(
    id          int8 not null,
    user_id     int8,
    primary key (id)
);

--Link between bucket and user
alter table if exists buckets
    add constraint buckets_fk_user
        foreign key (user_id) references users;

alter table if exists users
    add constraint  users_fk_bucket
        foreign key (bucket_id) references buckets;

--Category
DROP SEQUENCE IF EXISTS category_seq;
create sequence category_seq start 1 increment 1;

DROP TABLE IF EXISTS categories CASCADE;
create table categories
(
    id    int8 not null,
    title varchar(255),
    primary key (id)
);

--Product
DROP SEQUENCE IF EXISTS product_seq;
create sequence product_seq start 1 increment 1;

DROP TABLE IF EXISTS products CASCADE;
create table products
(
    id int8 not null,
    price numeric(19, 2),
    title varchar(255),
    primary key (id)
);

--category and product
DROP TABLE IF EXISTS products_categories CASCADE;
create table products_categories
(
    product_id  int8 not null,
    category_id int8 not null
);

alter table if exists products_categories
    add constraint products_categories_fk_category
        foreign key (category_id) references categories;

alter table if exists products_categories
    add constraint products_categories_fk_products
        foreign key (product_id) references products;

--Product in bucket
DROP TABLE IF EXISTS buckets_products CASCADE;
create table buckets_products
(
    bucket_id   int8 not null,
    product_id  int8 not null
);

alter table if exists buckets_products
    add constraint buckets_products_fk_products
        foreign key (product_id) references products;

alter table if exists buckets_products
    add constraint buckets_products_fk_bucket
        foreign key (bucket_id) references buckets;

--Orders
DROP SEQUENCE IF EXISTS order_seq;
create sequence order_seq start 1 increment 1;

DROP TABLE IF EXISTS orders CASCADE;
create table orders
(
    id          int8 not null,
    address     varchar(255),
    updated     timestamp,
    created     timestamp,
    status      varchar(255),
    sum         numeric(19, 2),
    user_id     int8,
    primary key (id)
);

alter table if exists orders
    add constraint orders_fk_user
        foreign key (user_id) references users;

--Order details
DROP SEQUENCE IF EXISTS orders_details_seq;
create sequence orders_details_seq start 1 increment 1;

DROP TABLE IF EXISTS orders_details CASCADE;
create table orders_details
(
    id          int8 not null,
    amount      numeric(19, 2),
    price       numeric(19,2),
    order_id    int8,
    product_id  int8,
    details_id  int8 not null,
    primary key (id)
);

alter table if exists orders_details
    add constraint order_details_fk_order
        foreign key (order_id) references orders;

alter table if exists orders_details
    add constraint order_details_fk_products
        foreign key (product_id) references products;


