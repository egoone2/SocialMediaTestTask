CREATE SCHEMA IF NOT EXISTS tasklist;

create table if not exists users
(
    id       bigserial primary key,
    name     varchar(255) not null,
    username varchar(255) not null unique,
    password varchar(255) not null
);


CREATE TABLE if NOT EXISTS Users_Role
(
    user_id bigint NOT null,
    role varchar(255) not null,
    primary key(user_id, role),
    constraint fk_user_role_user foreign key (user_id) references users(id) on delete cascade on update no action
);


-- create table if not exists tasks
-- (
--     id              bigserial primary key,
--     title           varchar(255) not null,
--     description     varchar(255) null,
--     status          varchar(255) not null,
--     expiration_date timestamp    null
-- );
--
-- CREATE TABLE if NOT EXISTS Users_task
-- (
--     user_id bigint not null,
--     task_id bigint not null,
--     primary key(user_id, task_id),
--     CONSTRAINT fk_user_task_user FOREIGN key (user_id) references users(id) on delete cascade on update no action,
--         CONSTRAINT fk_user_task_task FOREIGN key (task_id) references tasks(id) on delete cascade on update no action
-- );



