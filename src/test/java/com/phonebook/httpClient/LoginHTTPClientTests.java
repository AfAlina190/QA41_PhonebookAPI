package com.phonebook.httpClient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.phonebook.dto.AuthRequestDTO;
import com.phonebook.dto.AuthResponseDTO;
import com.phonebook.dto.ErrorDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginHTTPClientTests {
    @Test
    public void loginSuccessTest() throws IOException {

        Response response = Request.Post("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .bodyString("{\n" +
                        "  \"username\": \"afal33@gmail.com\",\n" +
                        "  \"password\": \"Alina2024$\"\n" +
                        "}", ContentType.APPLICATION_JSON)
                .execute();
        //System.out.println(response);

        String responseJson = response.returnContent().asString();
        System.out.println(responseJson);

        JsonElement element = JsonParser.parseString(responseJson);
        JsonElement token = element.getAsJsonObject().get("token");
        System.out.println(token.getAsString());
    }

    @Test
    public void loginSuccessTestWithDTO() throws IOException {

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("afal33@gmail.com")
                .password("Alina2024$")
                .build();

        Gson gson = new Gson();

        Response response = Request.Post("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .bodyString(gson.toJson(requestDTO), ContentType.APPLICATION_JSON)
                .execute();

        //HttpResponse httpResponse  = response.returnResponse();
        //        System.out.println(httpResponse.getStatusLine().getStatusCode());

        String responceJson = response.returnContent().toString();
        AuthResponseDTO dto = gson.fromJson(responceJson, AuthResponseDTO.class);
        System.out.println(dto.getToken());
    }

    @Test
    public void loginErrorTestWithDTO() throws IOException {
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("afal33gmail.com")
                .password("Alina2024$")
                .build();

        Gson gson = new Gson();

        Response response = Request.Post("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .bodyString(gson.toJson(requestDTO), ContentType.APPLICATION_JSON)
                .execute();

        HttpResponse httpResponse  = response.returnResponse();
        System.out.println(httpResponse.getStatusLine().getStatusCode());

        InputStream inputStream = httpResponse.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine())!=null){
            sb.append(line);
        }

        ErrorDTO errorDTO = gson.fromJson(sb.toString(),ErrorDTO.class);
        System.out.println(errorDTO.getTimestamp() + "***" + errorDTO.getStatus() + "***" + errorDTO.getError()
                + "***" + errorDTO.getMessage() + "***" + errorDTO.getPath());

        Assert.assertEquals(errorDTO.getStatus(),401);
    }













}
