public class Ork extends Zorde{
    private final int orkHealPoints = Constants.orkHealPoints;
    public Ork(int AP , int HP , int Move, String Name,int locationX, int locationY){super(Constants.orkAP,200,Constants.orkExcMove,Name,locationX, locationY);
    }
    public int getOrkHealPoints() {
        return orkHealPoints;
    }
}
