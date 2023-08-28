import java.io.*;
import java.util.*;

public class MgrLog {

    public String event;
    public String site;
    public String date;
    public ArrayList<String> moves = new ArrayList<>();

    // Add Zug mit Zugnummer und Zeit auf der Uhr
    public void addMove(int moveNumber, String move, String timeOnClock) {
        String formattedMove = String.format("%d. %s {%%clk %s}", moveNumber, move, timeOnClock);
        moves.add(formattedMove);
    }
    // Import Game in PGN Format aus einer Datei
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
                                // Entfernen von Zeitstempeln und Hinzufügen bereinigter Züge zur Liste
                                String sanitizedMove = move.replaceAll("\\{[^}]*\\}", "").trim();
                                this.moves.add(sanitizedMove);
                            }
                        }
                    }
                }
            }
        }
    }
    public void addMove(String move){

    }

    public void exportToCustomFormat(String outputFilename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilename))) {
            if (!moves.isEmpty ()) {
                writer.println ("Event: " + event);
                writer.println ("Site: " + site);
                writer.println ("Date: " + date);
                writer.println ("Moves:");
                for (String move : moves) {
                    writer.println (move);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}