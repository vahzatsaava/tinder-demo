-- Удаляем старое поле "liked"
ALTER TABLE likes DROP COLUMN liked;

-- Добавляем новое поле "like_status" для enum LikeType
ALTER TABLE likes ADD COLUMN like_status VARCHAR(20) NOT NULL DEFAULT 'LIKE';
