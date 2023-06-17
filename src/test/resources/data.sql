CREATE TABLE `user_app` (
                        `id` INTEGER NOT NULL AUTO_INCREMENT,
                        `email` varchar(100) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `active` BOOLEAN DEFAULT NULL,
                        `registration_date` datetime DEFAULT NULL,
                        `role_user` varchar(255) DEFAULT NULL,
                        `first_name` varchar(100) NOT NULL,
                        PRIMARY KEY (`id`)
);