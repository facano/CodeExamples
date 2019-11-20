package com.fcano.learningspringboot.clientproxy;

import com.fcano.learningspringboot.model.User;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public interface UserResourceV1 {
    @GET
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    List<User> fetchUsers(@QueryParam("gender") String gender);

    @GET
    @Path("{userUuid}")
    @Consumes(APPLICATION_JSON)
    User fetchUser(@PathParam("userUuid") UUID userUuid);

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    void insertNewUser(@RequestBody User user);

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    void updateUser(@RequestBody User user);

    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userUuid}")
    @Consumes(APPLICATION_JSON)
    void deleteUser(@PathParam("userUuid") UUID userUuid);
}
