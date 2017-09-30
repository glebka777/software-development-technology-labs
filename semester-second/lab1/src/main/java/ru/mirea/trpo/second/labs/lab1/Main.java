package ru.mirea.trpo.second.labs.lab1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String URL = "http://www.neracoos.org/erddap/tabledap/E05_aanderaa_all.json?station," +
            "mooring_site_desc,water_depth,time,current_speed,current_speed_qc,current_direction,current_direction_qc,current_u,current_u_qc,current_v,current_v_qc,temperature,temperature_qc,conductivity,conductivity_qc,salinity,salinity_qc,sigma_t,sigma_t_qc,time_created,time_modified,longitude,latitude";

    public static void main(String[] args) {
        File file = FileRetriever.retrieveFile(URL);
        if (file == null) return;
        List<String> params = new ArrayList<>();
        params.add("current_speed");
        params.add("temperature");
        params.add("salinity");
        System.out.println(new JsonSensorDataParser(file, params).parse());
        file.deleteOnExit();
    }

}
