DELETE FROM DISHES;
DELETE FROM RESTAURANTS;
DELETE FROM USER_VOTES;
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

INSERT INTO RESTAURANTS (NAME) VALUES ('Hof Van Cleve'),
  ('Mugaritz'),
  ('Le Louis XV'),
  ('El Celler de Can Roca'),
  ('Le Bernardin'),
  ('The Fat Duck');

INSERT INTO DISHES (NAME, PRICE, RESTAURANT_ID) VALUES ('Buttered popcorn', 525, 4),
  ('Masala dosa', 230, 1),
  ('Potato chips', 70, 2),
  ('Seafood paella', 310, 3),
  ('Som tam', 623, 4),
  ('Chicken rice', 322, 5),
  ('Poutine', 185, 6),
  ('Tacos', 125, 1),
  ('Buttered toast with Marmite', 312, 2),
  ('Stinky tofu', 165, 3),
  ('Marzipan', 785, 4),
  ('French toast', 459, 5),
  ('Chicken parm', 355, 6),
  ('Texas barbecue pork', 675, 1),
  ('Chili crab', 999, 2),
  ('Maple syrup', 333, 3),
  ('Ankimo', 799, 4),
  ('Parma ham', 675, 5),
  ('Ohmi-gyu beef steak', 869, 6),
  ('Pho', 355, 1),
  ('Montreal-style smoked meat', 1099, 2),
  ('Fajitas', 589, 3),
  ('Butter garlic crab', 1299, 4),
  ('Champ', 499, 5),
  ('Lasagna', 759, 6),
  ('Croissant', 299, 1),
  ('Arepas', 499, 2),
  ('Nam tok moo', 315, 3),
  ('Kebab', 415, 4),
  ('Lobster', 1856, 5),
  ('Egg tart', 565, 6),
  ('Kalua pig', 3599, 1),
  ('Donuts', 199, 2),
  ('Corn on the cob', 055, 3),
  ('Shepherds pie', 749, 4),
  ('Rendang', 349, 5),
  ('Chicken muamba', 849, 6),
  ('Ice cream', 315, 1),
  ('Tom yum goong', 649, 2),
  ('Penam assam laksa', 319, 3),
  ('Hamburger', 259, 4),
  ('Peking duck', 1405, 5),
  ('Sushi', 609, 6),
  ('Chocolate', 399, 1),
  ('Neapolitan pizza', 579, 2),
  ('Massaman curry', 1039, 3);


INSERT INTO USER_VOTES (CHOSEN_RESTAURANT_ID, USER_ID) VALUES (6, 3),
  (5, 2);
