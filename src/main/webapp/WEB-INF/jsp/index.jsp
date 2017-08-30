<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
    For users:
    <br>GET /rest/profile/restaurants - get restaurants with menu for today
    <br>GET /rest/profile/vote - get today's user vote for current user
    <br>DELETE /rest/profile/vote - delete today's user vote for current user
    <br>PUT /rest/profile/vote - change today's user vote for current user
    <br>POST /rest/profile/vote - create today's user vote for current user
    <br><br>For admins:
    <br>GET /rest/admin/restaurants - get all restaurants
    <br>POST /rest/admin/restaurants - create new restaurant
    <br>GET /rest/admin/restaurants/{id} - get restaurant with id={id}
    <br>DELETE /rest/admin/restaurants/{id} -  delete restaurant with id={id}
    <br><br>PUT /rest/admin/restaurants/{id} -  update restaurant with id={id}
    <br>GET /rest/admin/dishes - get all dishes
    <br>POST /rest/admin/dishes - create new dish
    <br>GET /rest/admin/dishes/{id} - get dish with id={id}
    <br>DELETE /rest/admin/dishes/{id} -  delete dish with id={id}
    <br>PUT /rest/admin/dishes/{id} -  update dish with id={id}
    <br><br>GET /votes - get history of user votes
    <br>GET /votes?sort={asc}/{desc} - get history of user votes in descending or ascending order (default is descending)
    <br>GET /votes?date={date} - get history of user votes filtered by date (in format "yyyy-MM-dd")
    <br>GET /votes?restaurantId={id} - get history of user votes filtered by restaurant
    <br>GET /votes?userId={id} - get history of user votes filtered by user
  </body>
</html>
