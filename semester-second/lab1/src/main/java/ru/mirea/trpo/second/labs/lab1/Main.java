package ru.mirea.trpo.second.labs.lab1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File file = new File(Main.class.getResource("/json/data.json").getFile());
        List<String> params = new ArrayList<>();
        params.add("current_speed");
        params.add("temperature");
        params.add("salinity");
        System.out.println(new JsonSensorDataParser(file, params).parse());
    }

}
