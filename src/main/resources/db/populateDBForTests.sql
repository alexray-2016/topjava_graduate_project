DELETE FROM DISHES;
DELETE FROM USER_VOTES;
DELETE FROM RESTAURANTS;
DELETE FROM USER_ROLES;
DELETE FROM USERS;
ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE RESTAURANTS ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE DISHES ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE USER_VOTES ALTER COLUMN ID RESTART WITH 1;

INSERT INTO USERS (NAME, EMAIL, PASSWORD) VALUES
  ('Admin', 'admin@gmail.com', 'adminpassword'),
  ('FirstUser', 'first_user@gmail.com', 'user1password'),
  ('SecondUser', 'second_user@gmail.com', 'user2password');

INSERT INTO USER_ROLES (USER_ID, ROLE) VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER'),
  (3, 'ROLE_USER');

INSERT INTO RESTAURANTS (NAME) VALUES ('Hof Van Cleve'), ('Mugaritz');

INSERT INTO DISHES (NAME, PRICE, RESTAURANT_ID) VALUES
  ('Masala dosa', 230, 1),
  ('Potato chips', 70, 2),
  ('Tacos', 125, 1),
  ('Buttered toast with Marmite', 312, 2),
  ('Texas barbecue pork', 675, 1),
  ('Chili crab', 999, 2),
  ('Pho', 355, 1),
  ('Montreal-style smoked meat', 1099, 2),
  ('Croissant', 299, 1),
  ('Arepas', 499, 2),
  ('Kalua pig', 3599, 1),
  ('Donuts', 199, 2),
  ('Ice cream', 315, 1),
  ('Tom yum goong', 649, 2),
  ('Chocolate', 399, 1),
  ('Neapolitan pizza', 579, 2);


INSERT INTO USER_VOTES (CHOSEN_RESTAURANT_ID, USER_ID, DATE, TIME) VALUES (1, 2, '2017-08-01', '10:00:00'),
  (2, 3, '2017-08-01', '10:00:00'),
  (1, 2, '2017-08-02', '10:00:00'),
  (2, 3, '2017-08-02', '10:00:00'),
  (1, 2, '2017-08-03', '10:00:00'),
  (2, 3, '2017-08-03', '10:00:00'),
  (1, 2, '2017-08-04', '10:00:00'),
  (2, 3, '2017-08-04', '10:00:00'),
  (1, 2, '2017-08-05', '10:00:00'),
  (2, 3, '2017-08-05', '10:00:00');