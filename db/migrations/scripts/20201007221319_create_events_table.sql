-- // create events table
-- Migration SQL that makes the change goes here.
CREATE TABLE events (
id NUMERIC(20,0) NOT NULL,
program_type NUMERIC NOT NULL,
color VARCHAR(25) NOT NULL,
message  VARCHAR(255) NOT NULL,
program_time TIMESTAMP NOT NULL,
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP NULL
);

ALTER TABLE events
ADD CONSTRAINT PK_EVENTS
PRIMARY KEY (id);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE events;


