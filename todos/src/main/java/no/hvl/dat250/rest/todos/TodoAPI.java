package no.hvl.dat250.rest.todos;

import com.google.gson.Gson;

import java.util.*;

import static spark.Spark.*;

/**
 * Rest-Endpoint.
 */
public class TodoAPI {
    private static Map<Long, Todo> todos = new HashMap<>();

    public static void main(String[] args) {
        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        before("*/:id", (req, res) -> {
            String id = req.params("id");
            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                halt(400, "The id \"" + id + "\" is not a number!");
            }
        });

        after((req, res) -> res.type("application/json"));

        get("todos", (req, res) -> {
            Gson gson = new Gson();
            return gson.toJson(todos.values());
        });

        get("todos/:id", (req, res) -> {
            String id = req.params("id");
            if (todos.containsKey(Long.parseLong(id))) {
                Todo todo = todos.get(Long.parseLong(id));
                return todo.toJson();
            }
            res.status(404);
            return ("Todo with the id \"" + id + "\" not found!");
        });

        post("todos", (req, res) -> {
            Gson gson = new Gson();
            Todo todo = gson.fromJson(req.body(), Todo.class);
            if (todo.getId() != null) {
                res.status(400);
                return gson.toJson("id not allowed");
            }
            todo.setId((long) todos.size());
            todos.put(todo.getId(), todo);
            res.status(201);
            return gson.toJson(todo);
        });

        put("todos/:id", (req, res) -> {
            Gson gson = new Gson();
            String id = req.params("id");
            Todo todo = gson.fromJson(req.body(), Todo.class);
            todos.put(Long.parseLong(id), todo);
            return todo.toJson();
        });

        delete("todos/:id", (req, res) -> {
            String id = req.params("id");
            if (!todos.containsKey(Long.parseLong(id))) {
                res.status(404);
                return "Todo with the id \"" + id + "\" not found!";
            }
            todos.remove(Long.parseLong(id));
            return new Gson().toJson("deleted");
        });
    }
}
