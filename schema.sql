-- Création de la base de données 'paymybuddy'
CREATE SCHEMA IF NOT EXISTS `paymybuddy`;
USE `paymybuddy` ;

-- Création de la table 'user'
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    balance DOUBLE NOT NULL,
    bank_account_number VARCHAR(255),
    bank_routing_number VARCHAR(255)
);

-- Création de la table 'transaction'
CREATE TABLE IF NOT EXISTS transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    description VARCHAR(255),
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES user(id),
    FOREIGN KEY (receiver_id) REFERENCES user(id)
);

-- Création de la table 'connection'
CREATE TABLE IF NOT EXISTS connection (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    connection_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (connection_id) REFERENCES user(id)
);
