ALTER TABLE developers_db.projects 
ADD COLUMN cost INT NOT NULL AFTER project_name;
UPDATE `developers_db`.`projects` SET `cost`='15000' WHERE `id`='1';
UPDATE `developers_db`.`projects` SET `cost`='12000' WHERE `id`='2';
UPDATE `developers_db`.`projects` SET `cost`='10000' WHERE `id`='3';
UPDATE `developers_db`.`projects` SET `cost`='16000' WHERE `id`='4';
UPDATE `developers_db`.`projects` SET `cost`='13000' WHERE `id`='5';
UPDATE `developers_db`.`projects` SET `cost`='20000' WHERE `id`='6';
SELECT * FROM developers_db.projects;