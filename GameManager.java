public class GameManager {
    public Board board;
    public Timer timer;
    public SchachGUI gui;
    public GameManager() {
        this.board = new Board();
        this.board.buildBoard();

    }
}
