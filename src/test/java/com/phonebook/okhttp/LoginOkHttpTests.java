package com.phonebook.okhttp;

import com.google.gson.Gson;
import com.phonebook.dto.AuthRequestDTO;
import com.phonebook.dto.AuthResponseDTO;
import okhttp3.*;
import org.jetbrains.annotations.TestOnly;
import org.testng.annotations.Test;

import java.io.IOException;


public class LoginOkHttpTests {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginSuccessTest() throws IOException {
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("afal33@gmail.com")
                .password("Alina2024$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();

        AuthResponseDTO dto = gson.fromJson(responseJson, AuthResponseDTO.class);
        System.out.println(dto.getToken());

    }






}
