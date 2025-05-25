--liquibase formatted sql
--changeset Yauheni Haikou:create user table localFilePath:01.000.00/create_user_table.sql

CREATE TABLE users
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL
);
