package com.rozsa.samples;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsonReader<T> {
    private String filePath;

    private Class<T> type;

    public JsonReader(String filePath, Class<T> type) {
        this.filePath = filePath;
        this.type = type;
    }

    public T read() throws IOException {
        URL url = JsonReader.class.getResource(filePath);

        ObjectMapper objectMapper = new ObjectMapper();
        T obj = objectMapper.readValue(url, type);

        return type.cast(obj);
    }

    public T readFile() throws IOException {
        File file = new File(filePath);

        //System.out.printf("Reading %s\n", filePath);

        ObjectMapper objectMapper = new ObjectMapper();
        T obj = objectMapper.readValue(file, type);

        return type.cast(obj);
    }

    public static <T> T readFrom(byte[] data, Class<T> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
