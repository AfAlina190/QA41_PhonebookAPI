package com.phonebook.okhttp;

import com.phonebook.dto.ContactDTO;
import com.phonebook.dto.ErrorDTO;
import com.phonebook.restAssured.TestBase;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class AddContactOkhttpTests extends TestBase {

    ContactDTO contactDto = ContactDTO.builder()
            .name("Elis")
            .lastName("Wagner")
            .email("Ewag@email.com")
            .phone("1122334455")
            .address("Berlin")
            .description("Singer")
            .build();

    @Test
    public void addContactSuccessTest() {

        given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("Contact was added!"));

    }

    @Test
    public void addContactWithoutNameTest() {
        ContactDTO contactDto1 = ContactDTO.builder()

                .lastName("Wagner")
                .email("Ewag@email.com")
                .phone("1122334455")
                .address("Berlin")
                .description("Singer")
                .build();

        ErrorDTO errorDto = given()
                .header(AUTH, TOKEN)
                .body(contactDto1)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDTO.class);

        Assert.assertTrue(errorDto.getMessage().toString().contains("name=must not be blank"));

    }

    @Test
    public void addContactWithInvalidPhoneTest(){

        ContactDTO contactDto2 = ContactDTO.builder()
                .name("Elis")
                .lastName("Wagner")
                .email("Ewag@email.com")
                .phone("4455")
                .address("Berlin")
                .description("Singer")
                .build();


        given()
                .header(AUTH, TOKEN)
                .body(contactDto2)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.phone",
                        containsString("Phone number must contain only digits! And length min 10, max 15!"));


    }


}
