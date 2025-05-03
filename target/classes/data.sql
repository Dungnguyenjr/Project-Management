use project_management;
-- xoa di 1 dong id = 100 trong bang account roi tao lai du lieu
DELETE FROM account WHERE id = 1;
INSERT INTO account
(id, username, password, email, role, is_active)
VALUES
    (1, 't1', '$2a$12$nht.bRHep8/0mzCfr7l37uSPETlxoMNqYOv1zROAl1uCXD2cn4eKm', 't100@gmail.com', 'ADMIN', 1);

-- INSERT INTO field
-- (id, code, name)
-- VALUES
--     (1, 'Mã 138', 'Ngành CNTT'),
--     (2, 'Mã 234', 'Ngành Quản Trị Mạng'),
--     (3, 'Mã 323', 'Ngành Marketting'),
--     (4, 'Mã 421', 'Ngành Kinh Tế'),
--     (5, 'Mã 538', 'Ngành Luật');
--
--
-- INSERT INTO course_entity
-- (id, course_name, start_Year)
-- VALUES
--     (1, 'Khoá CN23489', 2022),
--     (2, 'Khoá QT23480', 2021),
--     (3, 'Khoá MK32784', 2018),
--     (4, 'Khoá KT32893', 2023),
--     (5, 'Khoá NL23479', 2024);
--
--
-- INSERT INTO class_entity
-- (id, class_name, course, course_id)
-- VALUES
--     (1, 'Lớp CN04', 'Năm 2022', 1),
--     (2, 'Lớp QT03', 'Năm 2021', 2),
--     (3, 'Lớp MK01', 'Năm 2018', 3),
--     (4, 'Lớp KT04', 'Năm 2023', 4),
--     (5, 'Lớp NL05', 'Năm 2024', 5);
--
--
-- INSERT INTO batch
-- (id, date_end, date_start, name, year)
-- VALUES
--     (1, '2024-03-15', '2024-01-10', 'Đợt 1 năm 2024', 2024),
--     (2, '2024-06-10', '2024-04-01', 'Đợt 2 năm 2024', 2024),
--     (3, '2024-09-20', '2024-07-05', 'Đợt 3 năm 2024', 2024),
--     (4, '2025-03-30', '2025-01-15', 'Đợt 1 năm 2025', 2025),
--     (5, '2025-06-25', '2025-04-10', 'Đợt 2 năm 2025', 2025);
