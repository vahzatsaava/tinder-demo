CREATE TABLE likes
(
    id      SERIAL PRIMARY KEY,
    from_user_id BIGINT REFERENCES users (id),
    to_user_id BIGINT REFERENCES users (id),
    liked boolean not null
);