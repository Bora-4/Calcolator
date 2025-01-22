CREATE DATABASE IF NOT EXISTS calorator; 
USE calorator;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('user', 'admin') DEFAULT 'user' NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE food_entries (
    entry_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    food_name VARCHAR(255) NOT NULL,
    calories INT NOT NULL,
    price DECIMAL(10, 2) DEFAULT 0.00,
    entry_date DATETIME NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);
ALTER TABLE food_entries ADD CONSTRAINT chk_calories CHECK (calories >= 0);
ALTER TABLE food_entries ADD CONSTRAINT chk_price CHECK (price >= 0);


CREATE TABLE calorie_thresholds (
    threshold_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    threshold_date DATE NOT NULL,
    total_calories INT NOT NULL,
    is_warning_triggered BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

ALTER TABLE calorie_thresholds
ADD calorie_limit INT DEFAULT 2500; 

CREATE TABLE monthly_expenditure (
    expenditure_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    month DATE NOT NULL,
    total_spent DECIMAL(10, 2) DEFAULT 0.00,
    warning BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id INT NOT NULL,
    report_date DATE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE weekly_statistics (
    statistic_id INT AUTO_INCREMENT PRIMARY KEY,
    report_id INT NOT NULL,
    statistic_name VARCHAR(255) NOT NULL,
    statistic_value INT NOT NULL,
    FOREIGN KEY (report_id) REFERENCES Reports(report_id) ON DELETE CASCADE
);

CREATE TABLE user_spending (
    spending_id INT AUTO_INCREMENT PRIMARY KEY,
    report_id INT NOT NULL,
    user_id INT NOT NULL,
    total_spent DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (report_id) REFERENCES Reports(report_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);



