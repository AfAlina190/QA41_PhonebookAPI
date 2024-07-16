package com.phonebook.restAssured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYWZhbDMzQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzIxNDAwMDAxLCJpYXQiOjE3MjA4MDAwMDF9.FZWZfsx45LG_xetE3xF6zcsfd-nscM57lMH_q1vbpU4";
    public static final String AUTH = "Authorization";
    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }
}
