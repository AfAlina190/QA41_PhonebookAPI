package com.phonebook.restAssured;

import com.phonebook.dto.ContactDTO;
import com.phonebook.dto.ErrorDTO;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.containsString;

public class AddContactTests extends TestBase{

    ContactDTO contactDTO = ContactDTO.builder()
            .name("Eva")
            .lastName("Grau")
            .email("eva11@gm.com")
            .phone("1234567890")
            .address("USA")
            .description("Singer")
            .build();

    @Test
    public void addContactSuccessTest(){
        //String message =
        given()
                .header(AUTH, TOKEN)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                        .assertThat().body(containsString("Contact was added!"));
//                .extract().path("message"); вместо верхнего
        //System.out.println(message);

        //Contact was added! ID: 9c4c93ab-8c6b-45ed-80f6-e94daab5754c
    }

    @Test
    public void addContactWithoutNameTest(){
        ContactDTO contactDto1 = ContactDTO.builder()

                .lastName("Grau")
                .email("eva11@gm.com")
                .phone("1234567890")
                .address("USA")
                .description("Singer")
                .build();

        ErrorDTO errorDTO = given()
                .header(AUTH, TOKEN)
                .body(contactDto1)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDTO.class);

        Assert.assertTrue(errorDTO.getMessage().toString().contains("name=must not be blank"));
    }

   @Test
    public void addContactWithInvalidPhoneTest(){

       ContactDTO contactDTO2 = ContactDTO.builder()
               .name("Eva")
               .lastName("Grau")
               .email("eva11@gm.com")
               .phone("12345690")
               .address("USA")
               .description("Singer")
               .build();

     // ErrorDTO errorDTO =
               given()
               .header(AUTH, TOKEN)
               .body(contactDTO2)
               .contentType(ContentType.JSON)
               .when()
               .post("contacts")
               .then()
               .assertThat().statusCode(400)
                       .assertThat().body("message.phone",
                               containsString("Phone number must contain only digits! And length min 10, max 15!"));
               //.extract().response().as(ErrorDTO.class);
      // System.out.println(errorDTO.getMessage());


   }

}


