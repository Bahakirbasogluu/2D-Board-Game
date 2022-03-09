public class Elf extends Calliance{
    private final int elfRangedAp = Constants.elfRangedAP;
    public Elf(int AP , int HP , int Move, String Name,int locationX,int locationY){super(Constants.elfAP,70,Constants.elfExcMove,Name,locationX,locationY);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void setAp(int ap) {
        super.setAp(ap);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
