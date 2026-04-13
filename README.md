# TrainingsOrganizer
An organizing app for dog-training.


## How to get started
Install docker and run:
`docker compose up`

This starts a container with 3 services:
1. localhost:5432 -> a postgres database which gets automatically filled with some demo-data
2. localhost:8080 -> jdk-25 springboot backend
3. localhost:4200 -> an nginx server with the angular frontend

Open "localhost:4200/login" to see the application.
In the init.sql you see various logins, but as a first draft only trainer-users work properly.
Use:
```
Username: Peter
Password: Peter
```
to login.

You can now see the courses associated to `Peter`. You can view other courses and if you click on a course you can see the assigned appointments and add or delete them.

## How is security handled?
This is not a perfectly secure application. It would be way better to work with JWT, but for the sake of simplicity I implemented a simple token system with md5 and base64 encryption.
1. A user logs in and the server responds with `Set-Cookie`
2. Those cookies will be sent back with every request and also ensures that the user doesn't need to log in every time
3. The token gets checked on some endpoints with the help of an `aspect` (AuthAspect), where you also can define which role has access to which rest-endpoint
4. on the client side, there exists a guard to check if a user is logged in.
5. to have a smooth flow for the user, on initializing the angular app, an authentication request gets sent to the server and the user-settings get stored in a store

## How is the data handled?
In the background a postgres database is running, which gets passed the `init.sql` script to initialize on startup. The script defines a couple of tables, some relations and also enters some data. The backend accesses the data with hibernate. Not every case could be handled with the standard `Crud or JpaRepository`, so there are some queries. Eg.:
```java
@Query("SELECT CASE WHEN COUNT(ct) > 0 THEN true ELSE false END FROM coursetrainers ct WHERE ct.trainer.name = :name")
boolean isTrainer(@Param("name") String name);
```

The database itself has some points where performance could be optimized. You could implement some indexes to ensure faster lookups, create views for the `N:M` tables (eg.: `courseregisters` or `coursetrainers`).

## What about the frontend?
An angular application with angular material, tailwind and of course rxjs. With the help of tailwind, some easy theming is implemented, just to show how easy it can be done. The ui is rather rudimentary, to get the project up and running.

I hope this project gives you some overview of my skills.