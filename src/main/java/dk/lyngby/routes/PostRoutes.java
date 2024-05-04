package dk.lyngby.routes;

import dk.lyngby.controller.impl.PostController;
import dk.lyngby.model.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class PostRoutes {

    private final PostController postController = new PostController();

    protected EndpointGroup getRoutes() {
        return () -> {
            path("/posts", () -> {
                post("/", postController::create, Role.RoleName.USER);
                get("/", postController::readAll, Role.RoleName.USER);
                get("/{id}", postController::read, Role.RoleName.USER);
                put("/{id}", postController::update, Role.RoleName.USER);
                delete("/{id}", postController::delete, Role.RoleName.USER);
            });
        };
    }
}
