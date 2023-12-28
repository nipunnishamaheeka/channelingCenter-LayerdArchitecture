drop
    database if exists channelingCenter;
create
    database if not exists channelingCenter;

use
    channelingCenter;

CREATE TABLE `patient` (
                           `patient_id` varchar(10) NOT NULL,
                           `patient_name` varchar(25) NOT NULL,
                           `mobile_number` varchar(10) NOT NULL,
                           `address` text NOT NULL,
                           `sex` varchar(200) NOT NULL,
                           `email` varchar(25) NOT NULL,
                           `age` varchar(10) NOT NULL,
                           `blood` varchar(100) DEFAULT NULL,
                           PRIMARY KEY (`patient_id`)
);

CREATE TABLE `doctor` (
                          `id` varchar(10) NOT NULL,
                          `doctor_name` varchar(25) NOT NULL,
                          `address` text NOT NULL,
                          `email` varchar(25) NOT NULL,
                          `number` varchar(25) NOT NULL,
                          `type` varchar(25) NOT NULL,
                          `drFee` decimal(5,2) NOT NULL,
                          PRIMARY KEY (`id`)
);
CREATE TABLE `appoinment` (
                              `appoinment_id` varchar(10) NOT NULL,
                              `date` date NOT NULL,
                              `patient_id` varchar(10) NOT NULL,
                              `age` varchar(25) NOT NULL,
                              `id` varchar(10) NOT NULL,
                              `doctor_name` varchar(25) DEFAULT NULL,
                              `patient_name` varchar(25) DEFAULT NULL,
                              `status` varchar(30) NOT NULL,
                              PRIMARY KEY (`appoinment_id`),
                              KEY `patient_id` (`patient_id`),
                              KEY `id` (`id`),
                              CONSTRAINT `appoinment_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE,
                              CONSTRAINT `appoinment_ibfk_2` FOREIGN KEY (`id`) REFERENCES `doctor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `medicine` (
                            `medi_code` varchar(10) NOT NULL,
                            `medicine_name` varchar(40) NOT NULL,
                            `description` varchar(60) NOT NULL,
                            `qty` varchar(10) DEFAULT NULL,
                            `unit_price` varchar(10) NOT NULL,
                            PRIMARY KEY (`medi_code`)
);

CREATE TABLE `completeorders` (
                                  `medi_code` varchar(10) NOT NULL,
                                  `appoinment_id` varchar(10) NOT NULL,
                                  `qty` int NOT NULL,
                                  KEY `fk1` (`medi_code`),
                                  KEY `fk2` (`appoinment_id`),
                                  CONSTRAINT `fk1` FOREIGN KEY (`medi_code`) REFERENCES `medicine` (`medi_code`),
                                  CONSTRAINT `fk2` FOREIGN KEY (`appoinment_id`) REFERENCES `appoinment` (`appoinment_id`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `employee` (
                            `emp_id` varchar(10) NOT NULL,
                            `emp_name` varchar(25) NOT NULL,
                            `emp_address` text NOT NULL,
                            `mobile_number` varchar(25) NOT NULL,
                            `job_role` varchar(25) NOT NULL,
                            `qualification` varchar(25) NOT NULL,
                            `salary` varchar(25) NOT NULL,
                            PRIMARY KEY (`emp_id`)
);

CREATE TABLE `lab` (
                       `lab_id` varchar(10) NOT NULL,
                       `emp_id` varchar(10) NOT NULL,
                       `l_report` varchar(25) NOT NULL,
                       PRIMARY KEY (`lab_id`),
                       KEY `emp_id` (`emp_id`),
                       CONSTRAINT `lab_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `labreport` (
                             `lab_reportId` varchar(10) NOT NULL,
                             `patient_id` varchar(25) NOT NULL,
                             `date` date NOT NULL,
                             `doctor_id` varchar(10) NOT NULL,
                             `doctor_name` varchar(100) NOT NULL,
                             `age` varchar(10) NOT NULL,
                             `gender` varchar(20) NOT NULL,
                             `patient_name` varchar(200) NOT NULL,
                             `test_name` varchar(250) NOT NULL,
                             `test_result` varchar(250) NOT NULL,
                             `units` varchar(50) DEFAULT NULL,
                             `others` varchar(500) DEFAULT NULL,
                             PRIMARY KEY (`lab_reportId`),
                             KEY `doctor_id` (`doctor_id`),
                             KEY `patient_id` (`patient_id`)
);

CREATE TABLE `login` (
                         `full_name` varchar(25) NOT NULL,
                         `user_name` varchar(25) NOT NULL,
                         `password` varchar(25) NOT NULL,
                         `email` varchar(100) DEFAULT NULL,
                         PRIMARY KEY (`user_name`)
);

CREATE TABLE `medicalreport` (
                                 `id` varchar(10) DEFAULT NULL,
                                 `patient_id` varchar(10) NOT NULL,
                                 `patient_name` varchar(25) NOT NULL,
                                 `date` date NOT NULL,
                                 KEY `id` (`id`),
                                 KEY `patient_id` (`patient_id`),
                                 CONSTRAINT `medicalreport_ibfk_1` FOREIGN KEY (`id`) REFERENCES `doctor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                 CONSTRAINT `medicalreport_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TABLE `payment` (
                           `payment_id` varchar(10) NOT NULL,
                           `payment_date` date NOT NULL,
                           `payment_time` time NOT NULL,
                           `amount` double NOT NULL,
                           `appoinment_id` varchar(10) NOT NULL,
                           PRIMARY KEY (`payment_id`),
                           KEY `appoinment_id` (`appoinment_id`),
                           CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`appoinment_id`) REFERENCES `appoinment` (`appoinment_id`) ON DELETE CASCADE ON UPDATE CASCADE
);