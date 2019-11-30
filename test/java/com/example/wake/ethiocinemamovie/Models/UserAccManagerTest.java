package com.example.wake.ethiocinemamovie.Models;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;


public class UserAccManagerTest {
    @Test
    public void parseUserInfo() throws Exception {
        String data = "{\n" +
                "    \"success\": true,\n" +
                "    \"data\": {\n" +
                "        \"user_name\": \"muchachos\",\n" +
                "        \"phone\": \"123456\",\n" +
                "        \"balance\": 25,\n" +
                "        \"booked_tickets\": []\n" +
                "    }\n" +
                "}";

        JSONObject resp = new JSONObject(data);

        UserAccManager.parseUserInfo(resp);
//        String expectedToken = ;


    }

    @Test
    public void parseAndFillUserDetail() throws Exception {
    }

    @Test
    public void restoreUserInfo() throws Exception {
    }

}