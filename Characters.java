public class Characters {
    private int Ap;
    private int Hp;
    private int move;
    private String name;
    private int LocationX;
    private int LocationY;
    public int Range;

    public Characters(int ap, int hp, int move, String name, int locationX, int locationY) {
        Ap = ap;
        Hp = hp;
        this.move = move;
        this.name = name;
        LocationX = locationX;
        LocationY = locationY;
    }

    public int getAp() {
        return Ap;
    }

    public void setAp(int ap) {
        Ap = ap;
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int hp) {
        Hp = hp;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocationX() {
        return LocationX;
    }

    public void setLocationX(int locationX) {
        LocationX = locationX;
    }

    public int getLocationY() {
        return LocationY;
    }

    public void setLocationY(int locationY) {
        LocationY = locationY;
    }

    public Characters() {
        super();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
