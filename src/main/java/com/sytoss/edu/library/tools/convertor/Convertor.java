package com.sytoss.edu.library.tools.convertor;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;

public class Convertor {

    public void convert(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            String json = content.toString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonIssues = mapper.readTree(json);
            String result = jsonIssues.get("result").toString();

            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(result);
            reader.close();
            writer.close();
            createDocument(path,result);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void createDocument(String path, String result) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(path));
        StringBuilder content = new StringBuilder();
        content.append(result.replace("\\n", "\n")
                        .replace("\\\"", "\""))
                .append("\n");
        content.replace(content.length() - 2, content.length() - 1, "");
        writer.write(content.toString());
        writer.close();
    }
}


