package dk.lyngby.routes;

import dk.lyngby.controller.PersonController;
import dk.lyngby.model.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class PersonRoutes {

    private final PersonController personController = new PersonController();

    protected EndpointGroup getRoutes() {
        return () -> {
            path("/persons", () -> {
                post("/", personController::create, Role.RoleName.USER);
                get("/", personController::readAll, Role.RoleName.USER);
                get("/{id}", personController::read, Role.RoleName.USER);
                put("/{id}", personController::update, Role.RoleName.USER);
                delete("/{id}", personController::delete, Role.RoleName.USER);
            });
        };
    }
}
