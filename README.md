# Booky

Booky is a library management web application that allows users to create and manage reading lists, add books.
It is built with **Spring Boot** for the backend, **MySQL** as the database, and **Next.js** for the frontend.

## Features

- User registration and login with JWT authentication.
- Add books by ISBN, with book details retrieved via the Open Library API.
- Create, view, and manage reading lists.
- Paginated display of books and reading lists.

## Running the Project
The application uses Docker Compose to set up the environment. Both the backend and frontend services will run in separate containers, along with the MySQL database.

## Start the project: 
Run the following command to start the application using Docker Compose:

```bash
docker compose up
```

## Access the Application:

Backend: The backend API will be available at http://localhost:8080.

Frontend: The Next.js frontend will be available at http://localhost:80.
