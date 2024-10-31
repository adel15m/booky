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


## list of books to test this application:
- 9780064404990 – Charlotte's Web by E.B. White
- 9780142410363 – The Wind in the Willows by Kenneth Grahame
- 9780544003415 – The Secret Garden by Frances Hodgson Burnett
- 9780064400558 – Island of the Blue Dolphins by Scott O'Dell
- 9780140361223 – The Adventures of Tom Sawyer by Mark Twain
- 9780316055437 – Wonder by R.J. Palacio
- 9780140328721 – Matilda by Roald Dahl
- 9780060935467 – A Wrinkle in Time by Madeleine L'Engle
- 9780316769488 – The Catcher in the Rye by J.D. Salinger
- 9780394800165 – The Cat in the Hat by Dr. Seuss
