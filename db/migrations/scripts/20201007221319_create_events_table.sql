-- // create events table
-- Migration SQL that makes the change goes here.
CREATE TABLE events (
id BIGINT NOT NULL AUTO_INCREMENT,
type NUMERIC NOT NULL,
color VARCHAR(25) NOT NULL,
message  VARCHAR(255) NOT NULL,
time VARCHAR(25) NOT NULL,
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP NULL,
CONSTRAINT PK_EVENTS PRIMARY KEY (id)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE events;


