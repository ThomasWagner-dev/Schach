import java.io.*;
import java.util.*;

public class MgrLog {

    public String event;
    public String site;
    public String date;
    public List<String> moves = new ArrayList<>();

    public void importPGNGame(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("[")) {
                    String[] parts = line.split("\"");
                    if (parts.length >= 2) {
                        String tag = parts[0].substring(1);
                        String value = parts[1];
                        if (tag.equals("Event")) {
                            event = value;
                        } else if (tag.equals("Site")) {
                            site = value;
                        } else if (tag.equals("Date")) {
                            date = value;
                        }

                    }
                } else {
                    if (!line.isEmpty()) {
                        String[] moves = line.split("\\d+\\.");
                        for (String move : moves) {
                            move = move.trim();
                            if (!move.isEmpty()) {
                                String sanitizedMove = move.replaceAll("\\{[^}]*\\}", "").trim();
                                this.moves.add(sanitizedMove);
                            }
                        }
                    }
                }
            }
        }
    }

    public void exportToCustomFormat(String outputFilename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilename))) {
            writer.println("Event: " + event);
            writer.println("Site: "  + site);
            writer.println("Date: "  + date);
            writer.println("Moves:");
            for (String move : moves) {
                writer.println(move);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}