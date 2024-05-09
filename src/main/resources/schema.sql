CREATE TABLE IF NOT EXISTS `customer`(
    `customer_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(20) NOT NULL,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `mobile_number` VARCHAR(10) NOT NULL UNIQUE,
    `created_at` DATE NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATE,
    `updated_by` VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS `account`(
    `customer_id` INT NOT NULL,
    `account_number` VARCHAR(12) PRIMARY KEY,
    `account_type` VARCHAR(20) NOT NULL,
    `branch_address` VARCHAR(100) NOT NULL,
    `created_at` DATE NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATE,
    `updated_by` VARCHAR(20)
);