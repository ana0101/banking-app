package service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String path = "audit.csv";
    private FileWriter fileWriter;
    private static final AuditService instance = new AuditService();

    private AuditService() {
        try {
            fileWriter = new FileWriter(path, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AuditService getInstance() {
        return instance;
    }

    public void log(String action) {
        try {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            fileWriter.write(action + ", " + time + "\n");
            fileWriter.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

