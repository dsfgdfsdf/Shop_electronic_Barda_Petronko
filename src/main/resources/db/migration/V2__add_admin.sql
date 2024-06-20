INSERT INTO users (id, archive, email, name, password, role, bucket_id)
VALUES (1, false, 'mukolabarda777@gmail.com', 'admin', 'admin', 'ADMIN', null);
ALTER SEQUENCE user_seq RESTART WITH 1;