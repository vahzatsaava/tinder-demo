CREATE TABLE likes
(
    id           VARCHAR(50) PRIMARY KEY,
    from_user_id VARCHAR(50) REFERENCES users (id) on delete cascade,
    to_user_id   VARCHAR(50) REFERENCES users (id) on delete cascade,
    liked        boolean not null
);


