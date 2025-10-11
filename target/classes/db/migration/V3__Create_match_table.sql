CREATE TABLE matches
(
    id      SERIAL PRIMARY KEY,
    user_id1 BIGINT REFERENCES users (id),
    user_id2 BIGINT REFERENCES users (id),
    matched_at timestamp not null
);