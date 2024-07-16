package com.phonebook.restAssured;

import com.phonebook.dto.ContactDTO;
import com.phonebook.dto.UpdateContactDTO;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class PutContactsTests extends TestBase{
    UpdateContactDTO dto;

    @BeforeMethod
    public void precondition() {
        ContactDTO contactDto = ContactDTO.builder()
                .name("Elis")
                .lastName("Wagner")
                .email("Ewag@email.com")
                .phone("4455335588")
                .address("Berlin")
                .description("Singer")
                .build();

        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");
        String[] split = message.split(": ");
        String id = split[1];

        dto = UpdateContactDTO.builder()
                .id(id)
                .name("Elis")
                .lastName("Wagner")
                .email("Ewag@email.com")
                .phone("4455335588")
                .address("Berlin")
                .description("Singer")
                .build();

    }

    @Test
    public void updateContactSuccessTest() {

//        String message =
        given()
                .header(AUTH, TOKEN)
                .body(dto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("Contact was updated"));
//                .extract().path("message");

//        System.out.println(message);

    }

    @Test
    public void updateContactWithWrongIdTest() {

        UpdateContactDTO dtoWithWrongId = UpdateContactDTO.builder()
                .id("11937767-a6e6-4066-9292-6e802edb1567")
                .name("Elis")
                .lastName("Wagner")
                .email("Ewag@email.com")
                .phone("4455667799")
                .address("Berlin")
                .description("Singer")
                .build();

//        ErrorDto errorDto =
        given()
                .header(AUTH, TOKEN)
                .body(dtoWithWrongId)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", containsString("not found in your contacts!"));
//                .extract().response().as(ErrorDto.class);

    }


    @Test
    public void updateContactWithWrongTokenTest() {

//        ErrorDto errorDto =
        given()
                .header(AUTH, "ghghghgh")
                .body(dto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("error", containsString("Unauthorized"));
//                .extract().response().as(ErrorDto.class);
    }
}
