package com.fcano.learningspringboot.resource;

import com.fcano.learningspringboot.model.User;
import com.fcano.learningspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/v1/users")
@Component
@Validated
public class UserResourceResteasy {

    private UserService userService;

    @Autowired
    public UserResourceResteasy(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<User> fetchUsers(@QueryParam("gender") String gender){
        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{userUuid}")
    public User fetchUser(@PathParam("userUuid") UUID userUuid){
        return userService
                .getUser(userUuid)
                .orElseThrow(() -> new NotFoundException(("user " + userUuid + " not found")));
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public void insertNewUser(@Valid User user){
        userService.insertUser(user);
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public void updateUser(User user){
        userService.updateUser(user);
    }

    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userUuid}")
    public void deleteUser(@PathParam("userUuid") UUID userUuid){
        userService.removeUser(userUuid);
    }
}
