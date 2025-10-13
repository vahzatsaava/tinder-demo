CREATE TABLE matches
(
    id         VARCHAR(50) PRIMARY KEY,
    user_id1   VARCHAR(50) REFERENCES users (id) on delete cascade,
    user_id2   VARCHAR(50) REFERENCES users (id) on DELETE cascade,
    matched_at timestamp not null
);