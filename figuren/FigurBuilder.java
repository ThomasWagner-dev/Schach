package figuren;

public abstract class FigurBuilder {
    public static Figur createBauer(int color) {
        Figur figur = new Figur();
        figur.value = 1;
        figur.color = color;
        figur.moves = new int[][]{{0, 1}};
        return figur;
    }
}
