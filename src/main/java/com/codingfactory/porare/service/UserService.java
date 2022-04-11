package com.codingfactory.porare.service;

/*
 * UserService
 * Last update : 05/04/2022
 *
 * @author Loule95450
 * @version 1.0
 * @since 05/04/2022
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import com.codingfactory.porare.data.User;

@Service
public class UserService {

    /* Import JdbcTemplate */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>(); // Create a new list of users

        try { // Try to get all users from the database

            // Execute the query and get the result set from the database
            List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM user"); // Execute the query

            for (Map<String, Object> row : rows) { // For each row in the result set
                User user = new User(); // Create a new user
                user.setUserId(String.valueOf((int) row.get("u_id"))); // Set the user id
                user.setUsername((String) row.get("u_username")); // Set the username
                user.setEmail((String) row.get("u_email")); // Set the password

                users.add(user); // Add the user to the list
            }
        } catch (Exception e) { // Catch any exceptions
            e.printStackTrace();
        }

        return users; // Return the list of users
    }

    public List<User> getUserInformations(String token) {
        List<User> users = new ArrayList<>(); // Create a new list of users

        try {
            DecodedJWT jwt = JWT.decode(token);

            String sql = "SELECT * FROM user WHERE u_id = '" + jwt.getClaim("userId") + "'";

            // Fetch element and console log the password
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

            // Get the user password from the users list
            String password = (String) rows.get(0).get("u_password");

            // Check if the password correspond to the token
            try {
                Algorithm algorithm = Algorithm.HMAC256(password);
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("auth0")
                        .build(); //Reusable verifier instance
                DecodedJWT jwtVerifier = verifier.verify(token);

                // Good, if we get here, the token is valid
                User user = new User(); // Create a new user
                user.setUserId(String.valueOf((int) rows.get(0).get("u_id"))); // Set the user id
                user.setUsername((String) rows.get(0).get("u_username")); // Set the username
                user.setEmail((String) rows.get(0).get("u_email")); // Set the password
                users.add(user); // Add the user to the list

                return users; // Return the list of users
            } catch (JWTVerificationException exception) {
                //Invalid signature/claims
                User user = new User();
                users.add(user);

                return users;
            }
        } catch (JWTDecodeException exception) {
            //Invalid token
            User user = new User();
            users.add(user);

            return users;
        }
    }

    public int getUserCoins(String token) {
        int coins = 0;

        try {
            DecodedJWT jwt = JWT.decode(token);

            String sql = "SELECT u_coin FROM user WHERE u_id = '" + jwt.getClaim("userId") + "'";

            // Fetch element and console log the password
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

            // Get the user password from the users list
            coins = (int) rows.get(0).get("u_coin");

            return coins;
        } catch (JWTDecodeException exception) {
            //Invalid token
            return coins;
        }
    }
}
