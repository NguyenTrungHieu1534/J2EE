-- ─────────────────────────────────────────────────────────────────────
-- Insert roles (bỏ qua nếu đã có)
-- ─────────────────────────────────────────────────────────────────────
INSERT IGNORE INTO role (name) VALUES ('USER');
INSERT IGNORE INTO role (name) VALUES ('ADMIN');

-- ─────────────────────────────────────────────────────────────────────
-- Insert accounts với password đã được BCrypt encode
--   user    / 123456
--   admin   / admin123
-- ─────────────────────────────────────────────────────────────────────
INSERT IGNORE INTO account (login_name, password) VALUES
  ('user',  '$2a$10$7EqJtq98hPqEX7fNZaFWoOe2j8RxmIgBOtVDMPJ7fBqXkMbCjZxKi'),
  ('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.');

-- ─────────────────────────────────────────────────────────────────────
-- Gán role
-- ─────────────────────────────────────────────────────────────────────
-- user  → USER
INSERT IGNORE INTO account_role (account_id, role_id)
  SELECT a.id, r.id FROM account a, role r
  WHERE a.login_name = 'user' AND r.name = 'USER';

-- admin → ADMIN + USER
INSERT IGNORE INTO account_role (account_id, role_id)
  SELECT a.id, r.id FROM account a, role r
  WHERE a.login_name = 'admin' AND r.name IN ('ADMIN', 'USER');
