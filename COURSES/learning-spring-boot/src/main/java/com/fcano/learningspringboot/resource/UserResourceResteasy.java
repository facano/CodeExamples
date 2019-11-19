package com.fcano.learningspringboot.resource;

import com.fcano.learningspringboot.model.User;
import com.fcano.learningspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/v1/users")
@Component
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
    public Response fetchUser(@PathParam("userUuid") UUID userUuid){
        Optional<User> userOptional = userService.getUser(userUuid);
        if (userOptional.isPresent()){
            return Response.ok(userOptional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage("user " + userUuid + " was not found."))
                .build();
    }

    @POST
    @Produces(APPLICATION_JSON)
    public Response insertNewUser(@RequestBody User user){
        int result = userService.insertUser(user);
        return getIntegerResponseEntity(result);
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response updateUser(@RequestBody User user){
        int result = userService.updateUser(user);
        return getIntegerResponseEntity(result);
    }

    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userUuid}")
    public Response deleteUser(@PathParam("userUuid") UUID userUuid){
        int result = userService.removeUser(userUuid);
        return getIntegerResponseEntity(result);
    }

    private Response getIntegerResponseEntity(int result) {
        if (result == 1) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    class ErrorMessage{
        String message;

        public ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
