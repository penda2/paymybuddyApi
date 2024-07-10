-- Supprimer les données existantes
DELETE FROM connection;
DELETE FROM transaction;
DELETE FROM user;

-- Réinitialiser les identifiants auto-incrémentés
ALTER TABLE connection AUTO_INCREMENT = 1;
ALTER TABLE transaction AUTO_INCREMENT = 1;
ALTER TABLE user AUTO_INCREMENT = 1;

-- Insertion de données dans la table 'user'
INSERT INTO user (username, email, password, balance, bank_account_number, bank_routing_number)VALUES 
('Emine', 'empolat@exemple.com', '$2a$10$GsQ.Q0.JZyvxU9sjbnFZIOH1UX722nwFGfPqttZ7VKhtCKTZt01Lq', 500.00, '123456789', '987654321'),
('Victor', 'victor@exemple.com', '$2a$10$ZVkgdoCBOdKWsmJlF5NL1O7.yQumQ0hX9p/hTp95QeFT8QnpHaK4e', 600.00, '223456789', '887654321'),
('Jordan', 'jordan@exemple.com', '$2a$10$8N3zlIIcUTQHgKqPbktEz.hKbIAMAl5zY8ks.npzVs0EdbczXEEZe', 100.00, '18454119', '002650054'), ('Elodie', 'elodie@exemple.com', '$2a$10$o.Pp7Gen5vDCse8ohWk01OxYZEnidUnqIT1BlKuhW4auwqkUbXfyu', 300.00, '18454119', '002650054');

-- Insertion de données dans la table 'transaction'
INSERT INTO transaction (amount, description, sender_id, receiver_id) 
VALUES (50.00, 'Plomberie', 1, 2),(75.00, 'Téléphone', 2, 1);

-- Insertion de données dans la table 'connection'
INSERT INTO connection (user_id, connection_id) VALUES (1, 2),(2, 1),(1, 3);
