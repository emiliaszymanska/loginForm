package org.example.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.dao.UserDao;
import org.example.helpers.Parser;
import org.example.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.Collections;
import java.util.Map;

public class LoginHandler implements HttpHandler {

    private ObjectMapper mapper;

    public LoginHandler() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            Map<String, String> data = Parser.parseFormData(br.readLine());
            String email = data.get("email");
            String password = data.get("password");

            UserDao userDao = new UserDao();
            User user = userDao.getUserByEmailAndPassword(email, password);

            response = mapper.writeValueAsString(user);

            HttpCookie cookie = new HttpCookie("user", response);
            exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
            exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");

            sendResponse(exchange, response, 200);

        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
            sendResponse(exchange, response, 500);
        }
    }

    private void sendResponse(HttpExchange exchange, String response, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
        }
        exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("http://localhost:63342"));
        exchange.sendResponseHeaders(status, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
