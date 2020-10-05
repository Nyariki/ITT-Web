-- // create tasks table
-- Migration SQL that makes the change goes here.
CREATE TABLE tasks (
id NUMERIC(20,0) NOT NULL,
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP NULL
);

ALTER TABLE tasks
ADD CONSTRAINT PK_TASKS
PRIMARY KEY (id);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE tasks;


