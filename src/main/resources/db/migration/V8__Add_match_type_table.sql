-- Добавляем новое поле "match_status" для enum MatchType
ALTER TABLE matches ADD COLUMN match_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';