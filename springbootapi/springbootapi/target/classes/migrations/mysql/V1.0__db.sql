CREATE TABLE `blogin` (
  `id` varchar(36) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- myuse.codename definition

CREATE TABLE `bcodename` (
  `id` varchar(36) NOT NULL,
  `codeid` char(3) NOT NULL,
  `code` char(2) NOT NULL,
  `name` varchar(10) NOT NULL,
  `option1` varchar(10) NULL,
  `ordercol` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- myuse.school definition

CREATE TABLE `bschool` (
  `id` varchar(36) NOT NULL,
  `schoolname` varchar(50) NOT NULL,
  `schoolloc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- myuse.student definition

CREATE TABLE `bstudent` (
  `id` varchar(36) NOT NULL,
  `stucode` char(3) NOT NULL,
  `stuname` varchar(30) NOT NULL,
  `stuschid` varchar(36) NOT NULL,
  `stuage` int NOT NULL,
  `stubrithday` date DEFAULT NULL,
  `codenameid` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `student_FK` FOREIGN KEY (`stuschid`) REFERENCES `bschool` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;