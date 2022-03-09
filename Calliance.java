public class Calliance extends Characters{

    public Calliance(int ap, int hp, int move, String name,int locationX,int locationY){
        super(ap,hp,move,name,locationX,locationY);

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public int getAp() {
        return super.getAp();
    }
}
