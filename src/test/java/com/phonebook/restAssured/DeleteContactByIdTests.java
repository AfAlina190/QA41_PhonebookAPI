package com.phonebook.restAssured;

import com.phonebook.dto.ContactDTO;
import com.phonebook.dto.ErrorDTO;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdTests extends TestBase{

    String id;

    @BeforeMethod
    public void precondition(){
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Eva")
                .lastName("Grau")
                .email("eva11@gm.com")
                .phone("1234567890")
                .address("USA")
                .description("Singer")
                .build();


        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");

        String[] split = message.split(": ");
        id = split[1];
        //System.out.println(message);   9c4c93ab-8c6b-45ed-80f6-e94daab5754c
    }

    @Test
    public void deleteContactByIdSucccesTest(){

        //String message =
                given()
                .header(AUTH, TOKEN)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                        .assertThat().body("message",equalTo("Contact was deleted!"));
       // System.out.println(message); //Contact was deleted!

    }

    @Test
    public void deleteContactByWrongIdTest(){
        //ErrorDTO errorDTO =
                given()
                .header(AUTH, TOKEN)
                .when()
                .delete("contacts/9c4c93ab-8c6b-45ed-80f6-e94daab5754z")
                .then()
                .assertThat().statusCode(400)
                                .assertThat().body("message",containsString("not found in your contacts!"));
        //System.out.println(errorDTO.getMessage()); //Contact with id: елаегадг not found in your contacts!
    }

}
