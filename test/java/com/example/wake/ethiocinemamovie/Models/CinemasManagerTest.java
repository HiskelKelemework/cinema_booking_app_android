package com.example.wake.ethiocinemamovie.Models;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.junit.Test;

import static org.junit.Assert.*;


public class CinemasManagerTest {
    @Test
    public void parseAndFillAllCinemas() throws Exception {
        String data = "{\"success\": true,\"data\": [{\"_id\": \"5b27b7fa15e87914c0382959\",\"name\": \"cinema Empire\",\"imageURL\": \"cinema_empire.jpg\",\n" +
                "            \"address\": \"mehal piasa\"\n" +
                "        }\n" +
                "    ]}";

        String mukera = "{\"name\": \"wake\"}";



        JSONObject response = new JSONObject(mukera);
        System.out.println(response.getString("name"));
/*
        System.out.println(response.getBoolean("success"));

        int expectedSize = 1;
        String expectedId = "5b27b7fa15e87914c0382959";
        String expectedName = "cinema Empire";
        String expectedImageUrl = "cinema_empire.jpg";
        String expectedAddress = "mehal piasa";

        CinemasManager manager = CinemasManager.getInstance();
        manager.parseAndFillAllCinemas(response);

        assertEquals(expectedSize, manager.getAllCinemas().size());
        assertEquals(expectedName, manager.getAllCinemas().get(0).getName());
        assertEquals(expectedId, manager.getAllCinemas().get(0).getId());
        assertEquals(expectedAddress, manager.getAllCinemas().get(0).getAddress());
        assertEquals(expectedImageUrl, manager.getAllCinemas().get(0).getImageUrl()); */
    }

    @Test
    public void parseCinemaDetail() throws Exception {
    }

    @Test
    public void parseDate() throws Exception {
    }

}