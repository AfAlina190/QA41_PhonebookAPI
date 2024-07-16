package com.phonebook.okhttp;

import com.google.gson.Gson;
import com.phonebook.dto.AllContactsDTO;
import com.phonebook.dto.ContactDTO;
import com.phonebook.dto.ErrorDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsOkHTTPTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYWZhbDMzQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzIxMzIxOTM1LCJpYXQiOjE3MjA3MjE5MzV9.sO272lMXh5p2uQHAMMg9hf-YgmbXMZhsnuU3popdtkg";

    Gson gson = new Gson();
    OkHttpClient client= new OkHttpClient();

    @Test
    public void getAllContactsToSuccessTest() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization",token)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AllContactsDTO contactsDTO = gson.fromJson(response.body().string(), AllContactsDTO.class);
        List<ContactDTO> contacts = contactsDTO.getContacts();
        for (ContactDTO c:contacts){
//            System.out.println(c.getId());
//            System.out.println(c.getName());
//            System.out.println(c.getPhone());

        }

    }

    @Test
    public void getAllContactsWithWrongTokenTest() throws IOException {

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization", "asdjfawjf")
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDto = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDto.getError(),"Unauthorized");
    }
}
