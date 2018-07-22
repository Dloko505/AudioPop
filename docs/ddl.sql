CREATE TABLE IF NOT EXISTS `Setup` (
	`setup_id` INTEGER PRIMARY KEY AUTOINCREMENT, 
	`name` TEXT NOT NULL COLLATE NOCASE, 
	`gear` TEXT, 
	`setup_notes` TEXT
	);
	
CREATE UNIQUE INDEX `index_Setup_name` ON `Setup` (`name`)

CREATE TABLE IF NOT EXISTS `Test` (
	`test_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
	`setup_id` INTEGER, `notes` TEXT, `testType` TEXT NOT NULL, 
	`testResult` INTEGER NOT NULL, 
	`timestamp` INTEGER NOT NULL, 
	FOREIGN KEY(`setup_id`) REFERENCES `Setup`(`setup_id`) ON UPDATE NO ACTION ON DELETE CASCADE );
	
CREATE  INDEX `index_Test_setup_id` ON `Test` (`setup_id`);

CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT);