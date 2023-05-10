package com.thatjamesemo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFile {
    private File logFile;

    public LogFile(String date, String type) throws IOException {
        this.logFile = new File("logs/yten" + date + "-" + type + ".txt");
        try {
            if (logFile.createNewFile()){
                System.out.println("New file created");
            } else {
                System.out.println("Opening existing file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logItem(String content_to_log) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(java.time.LocalTime.now()).append("} - ");
        sb.append(content_to_log).append("\n");
        String content = sb.toString();

        FileWriter fw = new FileWriter(logFile);
        fw.write(content);
        fw.close();


    }
}
