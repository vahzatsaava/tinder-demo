CREATE TABLE messages
(
    id         VARCHAR(50) PRIMARY KEY,
    chat_id    VARCHAR(50) REFERENCES chats (id) on delete cascade,
    sender_id  VARCHAR(50) REFERENCES users (id) on delete cascade,
    content    TEXT      not null,
    created_at timestamp not null default now()
);
