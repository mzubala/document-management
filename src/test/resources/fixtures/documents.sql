INSERT INTO EMPLOYEE (id, hashedPassword, login) VALUES (100, 'test100', 'test100');
INSERT INTO DOCUMENT
(id, content, number, documentStatus, title, creator_Id, verificator_Id, createdAt, updatedAt, verificatedAt, deleted)
VALUES
(100, 'draft content', '100', 'DRAFT', 'draft title', 100, NULL, TIMESTAMP '2016-01-01 10:00:00', NULL, NULL, false)