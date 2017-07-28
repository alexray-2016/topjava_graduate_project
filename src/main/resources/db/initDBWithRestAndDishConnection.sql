DROP INDEX users_unique_email_idx IF EXISTS;
DROP INDEX user_votes_unique_user_datetime_idx IF EXISTS;
DROP TABLE restaurant_dishes IF EXISTS;
DROP TABLE dishes IF EXISTS;
DROP TABLE user_votes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE users IF EXISTS;

CREATE TABLE users
(
  id               INTEGER IDENTITY PRIMARY KEY,
  name             VARCHAR(255) NOT NULL,
  email            VARCHAR(255) NOT NULL,
  password         VARCHAR(255) NOT NULL,
  isAdmin          BOOLEAN DEFAULT FALSE
);
CREATE UNIQUE INDEX users_unique_email_idx ON users(email);

CREATE TABLE restaurants(
  id INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE user_votes
(
  id                INTEGER IDENTITY PRIMARY KEY,
  date_time         TIMESTAMP NOT NULL,
  chosen_restaurant_id INTEGER NOT NULL,
  user_id           INTEGER NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (chosen_restaurant_id) REFERENCES restaurants(id)
);
CREATE UNIQUE INDEX user_votes_unique_user_datetime_idx ON user_votes(user_id, date_time);

CREATE TABLE dishes
(
  id INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  price DECIMAL(5,2)
);

CREATE TABLE restaurant_dishes
(
  restaurant_id INTEGER,
  dish_id INTEGER,
  PRIMARY KEY (restaurant_id, dish_id),
  FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
  FOREIGN KEY (dish_id) REFERENCES dishes(id)
);