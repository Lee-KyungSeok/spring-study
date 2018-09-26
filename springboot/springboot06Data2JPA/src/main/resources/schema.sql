-- 아래처럼 만들 수 있다.
-- 순서는 이것이 먼저 시작한다.
DROP TABLE account if exists
DROP sequence if exists hibernate_sequence
CREATE sequence hibernate_sequence start WITH 1 increment BY 1
CREATE TABLE account (id int8 NOT NULL, email VARCHAR(255), password VARCHAR(255), username VARCHAR(255), PRIMARY KEY (id))