
create table if not exists users
(
    id       bigserial primary key,
    email     varchar(255) not null,
    username varchar(255) not null unique,
    password varchar(255) not null
);


CREATE TABLE if NOT EXISTS Users_Role
(
    user_id bigint       NOT null,
    role    varchar(255) not null,
    primary key (user_id, role),
    constraint fk_user_role_user foreign key (user_id) references users (id) on delete cascade on update no action
);

CREATE TABLE Post
(
    id           bigserial primary key,
    header       varchar(50),
    text_content varchar(255),
    filename     varchar(255),
    author_id    bigint not null,
    when_posted timestamp,
    constraint fk_post_user foreign key (author_id) references Users (id) on delete cascade on update no action
);

create table if not exists user_subscriptions
(
    user_id    bigint not null,
    subscriber_id bigint not null,
    foreign key (user_id) references users (id),
    foreign key (subscriber_id) references users (id),
    primary key (user_id, subscriber_id)
);
