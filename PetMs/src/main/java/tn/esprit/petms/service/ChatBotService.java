package tn.esprit.petms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ChatBotService {

    private final Environment environment;

    // Inject the environment to get properties like the file path
    public ChatBotService(Environment environment) {
        this.environment = environment;
    }

    // Method to load the API key from the file
    public String loadApiKey() throws IOException {
        // Get the file path from the properties
        String apiKeyFilePath = environment.getProperty("API_KEY_FILE_PATH");

        // Read the lines from the .api-keys file
        List<String> lines = Files.readAllLines(Paths.get(apiKeyFilePath));

        // Find the line that contains the OpenAI API key
        for (String line : lines) {
            if (line.startsWith("OPENAI_API_KEY=")) {
                return line.split("=")[1].trim();  // Extract the value
            }
        }
        throw new RuntimeException("API Key not found in file");
    }
}
