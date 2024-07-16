package com.phonebook.restAssured;

import com.phonebook.dto.AuthRequestDTO;
import com.phonebook.dto.AuthResponseDTO;
import com.phonebook.dto.ErrorDTO;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTests extends  TestBase{
    AuthRequestDTO requestDTO = AuthRequestDTO.builder()
            .username("afal33@gmail.com")
            .password("Alina2024$")
            .build();


    @Test
    public void loginSuccessTest(){
        AuthResponseDTO dto = given()
                .contentType("application/json")
                .body(requestDTO)
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDTO.class);
        System.out.println(dto.getToken());
    }

    @Test
    public void loginSuccessTest2(){
        String responseToken = given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .extract().path("token");
        System.out.println(responseToken);
    }

    @Test
    public void loginWrongPasswordTest(){
        ErrorDTO errorDTO = given()
                .body(AuthRequestDTO.builder()
                        .username("afal33@gmail.com")
                        .password("Alina20242$").build())
                .contentType(ContentType.JSON)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDTO.class);

    }

    @Test
    public void loginWrongPasswordPerfectTest(){
        given()
                .body(AuthRequestDTO.builder()
                        .username("afal33@gmail.com")
                        .password("Alina20242$").build())
                .contentType(ContentType.JSON)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("error",equalTo("Unauthorized"));

    }







}
