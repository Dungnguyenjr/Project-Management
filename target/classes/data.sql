use project_management;
-- xoa di 1 dong id = 100 trong bang account roi tao lai du lieu
DELETE FROM account WHERE id = 1;
INSERT INTO account
(id, username, password, email, role, is_active)
VALUES
    (1, 't1', '$2a$12$nht.bRHep8/0mzCfr7l37uSPETlxoMNqYOv1zROAl1uCXD2cn4eKm', 't100@gmail.com', 'ADMIN', 1);

