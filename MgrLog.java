import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.*;

import static java.util.regex.Pattern.quote;

public class MgrLog {

    public String event;
    public String site;
    public String date;
    public String round;
    public String white;
    public String black;
    public String result;
    public int whiteElo;
    public int blackElo;
    public String timeControl;
    public String termination;
    private int lastMoveNumber;

    public ArrayList<String> moves = new ArrayList<>();
    public ArrayList<String> movesToShow = new ArrayList<>();

    /* Beispiel Spiel zum Exportieren
    public static void main(String[] args) {
       MgrLog mgrLog = new MgrLog();
       mgrLog.init("Offline Chess","Lokal", LocalDate.now(),"-","PlayerOne","PlayerTwo",
               0,0,"600");
       mgrLog.addMove(1, 3, 4, "0:01:01");
       mgrLog.setEnd ("1-0","PlayerOne won by checkmate");
        try {
            mgrLog.exportToCustomFormat("Test.pgn");
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
     */
    public void init(String event, String site, LocalDate date, String round, String white,
                     String black, int whiteElo, int blackElo, String timeControl){
        this.event = event;
        this.site = site;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        this.date = date.format(formatter);
        this.round = round;
        this.white = white;
        this.black = black;
        this.whiteElo = whiteElo;
        this.blackElo = blackElo;
        this.timeControl = timeControl;
    }
    public void setEnd(String result, String termination){
        this.result = result;
        this.termination = termination;
    }
    // Add Zug mit Zugnummer und Zeit aufm Timmer
    public void addMove(int moveNumber, int y, int x, String timeOnClock) {
        String move = convertToChessNotation(y, x);
        String platzhalter = "";
        if (lastMoveNumber == moveNumber) platzhalter = "..";
        String formattedMove = String.format("%d.%s %s {%%clk %s}", moveNumber, platzhalter, move, timeOnClock);
        lastMoveNumber = moveNumber;
        moves.add(formattedMove);
        movesToShow.add (moveNumber + ". " + move);
    }
    // Konvertiert von X und Y in Schachnotation (z.B. 0, 3 -> "a4")
    public String convertToChessNotation(int y, int x) {
        char chessX = (char) ('a' + x); // Konvertierung von X in Buchstaben
        int chessY = 8 - y; // Umkehrung der Y-Koordinate
        return String.format("%c%d", chessX, chessY);
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
    public void exportToCustomFormat(String outputFilename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilename))) {
            if (!moves.isEmpty ()) {
                writer.println("[Event " + "\"" + event + "\"]");
                writer.println("[Site "  + "\"" + site  + "\"]");
                writer.println("[Date "  + "\"" + date  + "\"]");
                writer.println("[Round " + "\"" + round + "\"]");
                writer.println("[White " + "\"" + white + "\"]");
                writer.println("[Black " + "\"" + black + "\"]");
                writer.println("[Result " + "\"" + result + "\"]");
                writer.println("[WhiteElo " + "\"" + Integer.toString(whiteElo) + "\"]");
                writer.println("[BlackElo " + "\"" + Integer.toString(blackElo) + "\"]");
                writer.println("[TimeControl " + "\"" + timeControl + "\"]");
                writer.println("[Termination " + "\"" + termination + "\"]");
                writer.println ("");
                for (String move : moves) {
                    writer.print (move);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}