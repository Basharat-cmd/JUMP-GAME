import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MYFILES {

    private String filePath;

    // Constructor takes relative file path
    public MYFILES(String relativePath) {
        this.filePath = relativePath;
    }

    public void write(String content) throws IOException {
    File file = new File(filePath);

    // Create the file if it doesn't exist
    if (!file.exists()) {
        file.createNewFile();
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        writer.write(content);
    }
}

    // Read entire content from file and return as String
    public String read() throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }

        return (content.toString().trim() != "") ? content.toString().trim() : "0"; // Trim to remove trailing newline
    }
}
