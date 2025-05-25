--liquibase formatted sql
--changeset Yauheni Haikou:create users_subscriptions table localFilePath:01.000.00/create_user_subscriptions_table.sql

CREATE TABLE users_subscriptions
(
    user_id         BIGINT NOT NULL,
    subscription_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, subscription_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (subscription_id) REFERENCES subscriptions (id) ON DELETE CASCADE
);