-- flyway migration 을 위해서는 아래처럼 해야 한다.
-- V숫자__이름.sql
-- V는 꼭 대문자로.
-- 숫자는 순차적으로 (타임스탬프 권장)
-- 숫자와 이름 사이에 언더바 두 개.
-- 이름은 가능한 서술적으로.
-- 또한 한번 적용된 script 는 절대 건드리지 말고 새로 파일을 만들어서 하도록 한다.
DROP TABLE if exists account;
DROP sequence if exists hibernate_sequence;
CREATE sequence hibernate_sequence start WITH 1 increment BY 1;
CREATE TABLE account (id int8 NOT NULL, email VARCHAR(255), password VARCHAR(255), username VARCHAR(255), PRIMARY KEY (id));