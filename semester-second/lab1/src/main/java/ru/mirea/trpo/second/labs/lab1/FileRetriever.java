package ru.mirea.trpo.second.labs.lab1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

class FileRetriever {

    static File retrieveFile(String urlString) {
        File file;
        try {
            Path path = Files.createTempFile("trpo-json", "");
            file = path.toFile();
            FileUtils.copyURLToFile(new URL(urlString), file);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

}
