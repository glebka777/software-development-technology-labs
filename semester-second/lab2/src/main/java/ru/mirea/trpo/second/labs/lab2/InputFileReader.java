package ru.mirea.trpo.second.labs.lab2;

import org.apache.log4j.Logger;
import ru.mirea.trpo.second.labs.lab2.logs.ElevatorCall;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class InputFileReader {

    private static final Logger logger = Logger.getLogger(InputFileReader.class);

    static Map<Integer, List<ElevatorCall>> read(File file) {
        Map<Integer, List<ElevatorCall>> calls = new TreeMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] infos = line.trim().split(" ");
                int time = Integer.parseInt(infos[0]);
                int sourceLevel = Integer.parseInt(infos[1]);
                int destinationLevel = Integer.parseInt(infos[2]);
                List<ElevatorCall> list;
                if ((list = calls.get(time)) == null) {
                    list = new ArrayList<>();
                    calls.put(time, list);
                }
                list.add(new ElevatorCall(sourceLevel, destinationLevel));
            }
        } catch(FileNotFoundException e) {
            logger.error("File not found.", e);
        } catch(IOException e) {
            logger.error("Error occurred while reading file", e);
        }
        return calls;
    }

}
