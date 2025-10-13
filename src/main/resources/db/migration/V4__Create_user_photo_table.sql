CREATE TABLE user_photos
(
    user_id   VARCHAR(50) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    photo_url VARCHAR(255) NOT NULL
);
