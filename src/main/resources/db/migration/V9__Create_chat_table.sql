CREATE TABLE chats
(
    id           VARCHAR(50) PRIMARY KEY,
    from_user_id VARCHAR(50) REFERENCES users (id) on DELETE cascade ,
    to_user_id   VARCHAR(50) REFERENCES users (id) on delete cascade ,
    created_at   timestamp not null default now()
);
