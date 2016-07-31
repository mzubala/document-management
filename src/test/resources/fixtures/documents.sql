INSERT INTO Employee (id, hashedPassword, login) VALUES (100, 'test100', 'test100');

INSERT INTO DOCUMENT (id, content, number, status, title, creator_id, verificator_id, createdAt, updatedAt, verifiedAt, deletor_id, deleted)
VALUES (100, 'draft content', '100', 'DRAFT', 'draft title', 100, NULL, TIMESTAMP '2016-01-01 10:00:00', NULL, NULL, NULL, false);