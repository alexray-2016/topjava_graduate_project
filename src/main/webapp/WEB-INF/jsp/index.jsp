<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
    For users:
        GET /rest/profile/restaurants - get restaurants with menu for today
        GET /rest/profile/vote - get today's user vote for current user
        DELETE /rest/profile/vote - delete today's user vote for current user
        PUT /rest/profile/vote - change today's user vote for current user
        POST /rest/profile/vote - create today's user vote for current user
   For admins:
        GET /rest/admin/restaurants - get all restaurants
        POST /rest/admin/restaurants - create new restaurant
        GET /rest/admin/restaurants/{id} - get restaurant with id={id}
        DELETE /rest/admin/restaurants/{id} -  delete restaurant with id={id}
        PUT /rest/admin/restaurants/{id} -  update restaurant with id={id}
        GET /rest/admin/dishes - get all dishes
        POST /rest/admin/dishes - create new dish
        GET /rest/admin/dishes/{id} - get dish with id={id}
        DELETE /rest/admin/dishes/{id} -  delete dish with id={id}
        PUT /rest/admin/dishes/{id} -  update dish with id={id}
  </body>
</html>
