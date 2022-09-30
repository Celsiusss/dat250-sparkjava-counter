# Expass 4

I opened the project in Intellij and it auto imported all Maven dependencies for me.

Testing the experiment 1 API endpoints worked as expected in postman.

In experiment 2, I created the necessary endpoints with Spark according to the tests.
I used the `before` filter to catch and respond with an error if the *id* was not
valid to avoid duplicate code. I also implemented a setter for *id* in `Todo` to make
it easier to set an id instead of instantiating a new Todo.

I did not encounter any big issues, but I decided to use a HashMap to store the Todos,
since it made it easier to implement than using lists.
