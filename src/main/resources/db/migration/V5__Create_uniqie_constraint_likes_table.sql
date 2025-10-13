ALTER TABLE likes
    ADD CONSTRAINT unique_like_pair UNIQUE (from_user_id, to_user_id);