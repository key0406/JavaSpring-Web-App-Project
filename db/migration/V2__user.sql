CREATE TABLE `user` (
                        `id` varchar(255) NOT NULL,
                        `active` tinyint DEFAULT NULL,
                        `created_time` datetime(6) DEFAULT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `fname` varchar(255) DEFAULT NULL,
                        `lname` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `phone` varchar(255) DEFAULT NULL,
                        `role` varchar(255) DEFAULT NULL,
                        `updated_time` datetime(6) DEFAULT NULL,
                        `verified` tinyint DEFAULT NULL,
                        `notes` text,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `email` (`email`),
                        KEY `user_email_index` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
