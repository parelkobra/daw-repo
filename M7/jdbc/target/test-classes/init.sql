CREATE TABLE users(user_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                  username VARCHAR(10) NOT NULL,
                  password VARCHAR(50) NOT NULL,
                  name  VARCHAR(20) NOT NULL,
                  email VARCHAR(50) NOT NULL,
                  rank INT DEFAULT 0,
                  active BOOLEAN DEFAULT true,
                  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);

INSERT INTO users (username, password, name, email) VALUES ('user1', 'passwd', 'John Test', 'john@email.com');
INSERT INTO users (username, password, name, email) VALUES ('user2', 'passwd', 'Paul Test', 'paul@email.com');