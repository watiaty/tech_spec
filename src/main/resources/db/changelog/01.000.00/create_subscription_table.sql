--liquibase formatted sql
--changeset Yauheni Haikou:create subscription table localFilePath:01.000.00/create_subscription_table.sql

CREATE TABLE subscriptions
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);
