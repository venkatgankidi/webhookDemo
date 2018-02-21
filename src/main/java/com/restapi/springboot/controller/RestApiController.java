package com.restapi.springboot.controller;

import java.io.IOException;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.restapi.springboot.model.User;
import com.restapi.springboot.service.UserService;
import com.restapi.springboot.util.CustomErrorType;
import sun.misc.BASE64Decoder;

@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    UserService userService ;//Service which will do all data retrieval/manipulation work

    // -------------------Retrieve All Users---------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers(@RequestHeader HttpHeaders header) {

        String authInfo= header.getFirst("authorization");

        header.add("ERROR_MESSAGE","8006:The user name: tabletzd2ws07, supplied in the request is not the same as the logged in user: ciqa");


        if(!isUserAuthenticated(authInfo)){
            return new ResponseEntity ("unautorized to access ",header,HttpStatus.UNAUTHORIZED);}
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    @RequestMapping(value = "/listen", method = RequestMethod.POST)
    public ResponseEntity listenData(@RequestBody String body) {
        System.out.print("*********");
        System.out.print(body);
        ResponseEntity res= new ResponseEntity(HttpStatus.OK);
        return res;
    }

    // -------------------Retrieve Single User------------------------------------------

//    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
//    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
//        logger.info("Fetching User with id {}", id);
//        User user = userService.findById(id);
//        if (user == null) {
//            logger.error("User with id {} not found.", id);
//            return new ResponseEntity(new CustomErrorType("User with id " + id
//                    + " not found"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<User>(user, HttpStatus.OK);
//    }
//
//    // -------------------Create a User-------------------------------------------
//
//    @RequestMapping(value = "/user/", method = RequestMethod.POST)
//    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
//        logger.info("Creating User : {}", user);
//
//        if (userService.isUserExist(user)) {
//            logger.error("Unable to create. A User with name {} already exist", user.getName());
//            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
//                    user.getName() + " already exist."),HttpStatus.CONFLICT);
//        }
//        userService.saveUser(user);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
//        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
//    }
//
//    // ------------------- Update a User ------------------------------------------------
//
//    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
//        logger.info("Updating User with id {}", id);
//
//        User currentUser = userService.findById(id);
//
//        if (currentUser == null) {
//            logger.error("Unable to update. User with id {} not found.", id);
//            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
//                    HttpStatus.NOT_FOUND);
//        }
//
//        currentUser.setName(user.getName());
//        currentUser.setAge(user.getAge());
//        currentUser.setSalary(user.getSalary());
//
//        userService.updateUser(currentUser);
//        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
//    }
//
//    // ------------------- Delete a User-----------------------------------------
//
//    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
//        logger.info("Fetching & Deleting User with id {}", id);
//
//        User user = userService.findById(id);
//        if (user == null) {
//            logger.error("Unable to delete. User with id {} not found.", id);
//            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
//                    HttpStatus.NOT_FOUND);
//        }
//        userService.deleteUserById(id);
//        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//    }
//
//    // ------------------- Delete All Users-----------------------------
//
//    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
//    public ResponseEntity<User> deleteAllUsers() {
//        logger.info("Deleting All Users");
//
//        userService.deleteAllUsers();
//        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//    }

    private boolean isUserAuthenticated(String authString){

    String decodedAuth = "";
    // Header is in the format "Basic 5tyc0uiDat4"
    // We need to extract data before decoding it back to original string
    String[] authParts = authString.split("\\s+");
    String authInfo = authParts[1];
    // Decode the data back to original string
    byte[] bytes = null;
    try {
    bytes = new BASE64Decoder().decodeBuffer(authInfo);
    } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }
    decodedAuth = new String(bytes);
    String auth[]=decodedAuth.split(":");
    logger.info("length info "+auth.length);
    String username=auth[0];
    String password=auth[1];

    logger.info("username "+ username + " password "+password);
    if(username.equals("tabletzd2ws07") && password.equals("Password1"))
    return true;

    return false;
    }
}