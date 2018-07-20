CREATE TABLE IF NOT EXISTS `Setup` (
	`setup_id` INTEGER PRIMARY KEY AUTOINCREMENT, 
	`name` TEXT NOT NULL COLLATE NOCASE, 
	`gear` TEXT, 
	`setup_notes` TEXT
	);
	
	CREATE UNIQUE INDEX `index_Setup_name` ON `${TABLE_NAME}` (`name`)