//****************************************************************************
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//****************************************************************************
public class Main {
    // these are my public constants for using in every method without taking parameter.
    public static final List<String> Zordes = Arrays.asList("G","O","T");
    public static final List<String> Calliance = Arrays.asList("H","D","E");
    public static final List<String> everyStepAttack = Arrays.asList("G","D","E");
    public static int rows;
    public static ArrayList<Characters> AllIn = new ArrayList<>();

    //in this method Ork gives heal all of friends and himself before moving
    public static void OrkHealing(Characters ork){
       ArrayList<Characters> friends= NeighborCellControlBeforeMove(ork);
        for ( Characters friend : friends ) {
            switch (friend.getName().substring(0, 1)) {
                case ("T"): {
                    if (friend.getHp() < Constants.TrollHp) friend.setHp(friend.getHp() + Constants.orkHealPoints);
                    if (friend.getHp() > Constants.TrollHp) friend.setHp(Constants.TrollHp);
                    break;
                }
                case ("O"): {
                    if (friend.getHp() < Constants.OrkHp) friend.setHp(friend.getHp() + Constants.orkHealPoints);
                    if (friend.getHp() > Constants.OrkHp) friend.setHp(Constants.OrkHp);
                    break;
                }
                case ("G"): {
                    if (friend.getHp() < Constants.GoblinHp) friend.setHp(friend.getHp() + Constants.orkHealPoints);
                    if (friend.getHp() > Constants.GoblinHp) friend.setHp(Constants.GoblinHp);
                    break;
                }
            }
        }
    }

    //in this method the characters attacks the enemy character
    public static void Attack(Characters character){
        ArrayList<Characters> enemies = NeighborCellControlAfterMove(character);
        for ( Characters enemy : enemies ) {
            enemy.setHp(enemy.getHp() - character.getAp());
            if (enemy.getHp() <= 0) {
                AllIn.remove(enemy);
            }
        }
    }

    //in this method Elf attacks 2 cells around
    public static void ElfFinalRangedAttack(Characters character){
        ArrayList<Characters> enemies = ElfTwoRangedNeighborCellControlAfterFinalStep(character);
        for ( Characters enemy : enemies ) {
            enemy.setHp(enemy.getHp() - Constants.elfRangedAP);
            if (enemy.getHp() <= 0) {
                AllIn.remove(enemy);
            }
        }
    }

    //in this method we can check 2 cells around of our char (elf)
    public static ArrayList<Characters> ElfTwoRangedNeighborCellControlAfterFinalStep(Characters character){
        ArrayList<Characters> enemyCharacters = new ArrayList<>();
        boolean whoIam=Calliance.contains(character.getName().substring(0,1));
        boolean whoAreYou;
        int currentX=character.getLocationY();
        int currentY=character.getLocationX();
        for(int i = -2 ; i<3 ; i++){
            for(int j = -2 ; j<3 ; j++ ){
                if(i == 0 && j == 0 ){ continue; }
                else{
                    for ( Characters characters : AllIn ) {
                        if (characters.getLocationY() == currentX + j && characters.getLocationX() == currentY + i) {
                            whoAreYou = Zordes.contains(characters.getName().substring(0, 1));
                            if (whoIam == whoAreYou) {
                                enemyCharacters.add(characters);
                            }
                        }

                    }
                }
            }
        }
        return  enemyCharacters;
    }

    //in this method looks around before moving
    public static ArrayList<Characters> NeighborCellControlBeforeMove(Characters character){
        //its for orks cause it returns ork's friends
        ArrayList<Characters> friendCharacters = new ArrayList<>();
        boolean whoIam=Zordes.contains(character.getName().substring(0,1));
        boolean whoAreYou;
        int currentX=character.getLocationY();
        int currentY=character.getLocationX();
        for(int i = -1 ; i<2 ; i++){
            for(int j = -1 ; j<2 ; j++ ){
                for ( Characters characters : AllIn ) {
                    if (characters.getLocationY() == currentX + j && characters.getLocationX() == currentY + i) {
                        whoAreYou = Zordes.contains(characters.getName().substring(0, 1));
                        if (whoIam == whoAreYou) {
                            friendCharacters.add(characters);
                        }
                    }

                }

            }
        }
        return friendCharacters;
    }

    //in this method looks around after moving
    public static ArrayList<Characters> NeighborCellControlAfterMove(Characters character){
        ArrayList<Characters> enemyCharacters = new ArrayList<>();
        boolean whoIam=Zordes.contains(character.getName().substring(0,1));
        boolean whoAreYou;
        int currentX=character.getLocationY();
        int currentY=character.getLocationX();
        for(int i = -1 ; i<2 ; i++){
            for(int j = -1 ; j<2 ; j++ ){
                if(i == 0 && j == 0 ){ continue; }
                else{
                    for ( Characters characters : AllIn ) {
                        if (characters.getLocationY() == currentX + j && characters.getLocationX() == currentY + i) {
                            whoAreYou = Zordes.contains(characters.getName().substring(0, 1));
                            if (whoIam != whoAreYou) {
                                enemyCharacters.add(characters);
                            }
                        }

                    }
                }
            }
        }
        return enemyCharacters;
    }

    // This method does control if there is firendly or allien on the location
    public static boolean CellCheckAndMove(int stepX, int stepY,int ArrayOrder){
        boolean fightToDeathCheck = false;
        boolean flag=true;
        boolean whoIam=Zordes.contains(AllIn.get(ArrayOrder).getName().substring(0,1));
        boolean whoAreYou;
        int locX = AllIn.get(ArrayOrder).getLocationY()+stepX;
        int locY = AllIn.get(ArrayOrder).getLocationX()+stepY;
        for(int i = 0 ; i<AllIn.size() ; i++){
            whoAreYou=Zordes.contains(AllIn.get(i).getName().substring(0,1));
                if(!(AllIn.get(i).getLocationY() == locX) || !(AllIn.get(i).getLocationX() == locY )) flag=true;
                else
                {
                    if(whoAreYou==whoIam) flag=false;
                    else
                    {
                        flag=fightToDeath(AllIn.get(ArrayOrder),AllIn.get(i));
                        fightToDeathCheck =true;
                        if(flag && ArrayOrder>i){
                            ArrayOrder-=1;
                        }
                        break;
                    }
                }
        }
        if(flag)
        {
            AllIn.get(ArrayOrder).setLocationY(locX);
            AllIn.get(ArrayOrder).setLocationX(locY);
        }
        return fightToDeathCheck;
    }

    // this method does the fighttodeath thing
    public static boolean fightToDeath(Characters me, Characters enemy) {
        int myHp = me.getHp();
        int myAp = me.getAp();
        int enemyHp = enemy.getHp() ;
        enemyHp -= myAp;
        enemy.setHp(enemyHp);
        while(myHp>0 && enemyHp>0)
        {
            if(myHp==enemyHp){
                enemyHp=0;
                myHp=0;
                me.setHp(myHp);
                enemy.setHp(enemyHp);
            }
            else if (myHp>enemyHp){
                myHp=myHp-enemyHp;
                me.setHp(myHp);
                enemyHp=0;
            }
            else {
                enemyHp = enemyHp - myHp;
                enemy.setHp(enemyHp);
                myHp = 0;
            }
        }
     if(myHp<=0 && enemyHp>0){
         AllIn.remove(me);
         return false;
     }
     else if (myHp>0){
         AllIn.remove(enemy);
         return true;
     }
     else {
         AllIn.remove(me);
         AllIn.remove(enemy);
         return false;
     }
    }

    // This method does if there is a problem while moving on the board like exceeding.
    public static boolean BoardExceedCheck(int locX, int locY,  boolean board_flag, int j, FileWriter writer) throws IOException {
        try {

                if (!(locX >= 0 && locY>=0) || !(locX < rows && locY < rows)) {
                    throw new Boundary_Check();
        }
        }
        catch (Exception e){
            if(board_flag && j!=0){PrintBoard(writer);CharacterPrinter(AllIn,writer);}
            writer.write("\n"+"Error : Game board boundaries are exceeded. Input line ignored.\n"+"\n");
            return true;
        }
        return false;
    }

    // Reading the initials and separatng line by line and creating the board
    public static void ReadInitials(String args){
        String[] command_lines1 = ReadFromFile.readFile(args);
        assert command_lines1 != null;
        for (String line:command_lines1){
            String[] words = line.split(" ");
            // if there is multiple sign in txt line then create the board as requested size
            if(words[0].contains("x")){
                String[] final_line = line.split("x");
                rows = Integer.parseInt(final_line[0]);
            }
            // This switch cases makes a new obje and fill the matris with given initial location in txt.
            switch (words[0]){
                case "ELF":{
                    AllIn.add(new Elf(Constants.elfAP,70,Constants.elfExcMove,words[1],Integer.parseInt(words[3]),Integer.parseInt(words[2])));
                    break;
                }
                case "DWARF" :{
                    AllIn.add(new Dwarf(Constants.dwarfAP,120,Constants.dwarfExcMove,words[1],Integer.parseInt(words[3]),Integer.parseInt(words[2])));
                    break;
                }
                case "HUMAN" :{
                    AllIn.add(new Human(Constants.humanAP,100,Constants.humanExcMove,words[1],Integer.parseInt(words[3]),Integer.parseInt(words[2])));
                    break;
                }
                case "GOBLIN" :{
                    AllIn.add(new Goblin(Constants.goblinAP,80,Constants.goblinExcMove,words[1],Integer.parseInt(words[3]),Integer.parseInt(words[2])));
                    break;
                }
                case "TROLL" :{
                    AllIn.add(new Troll(Constants.trollAP,150,Constants.trollExcMove,words[1],Integer.parseInt(words[3]),Integer.parseInt(words[2])));
                    break;
                }
                case "ORK" :{
                    AllIn.add(new Ork(Constants.orkAP,200,Constants.orkExcMove,words[1],Integer.parseInt(words[3]),Integer.parseInt(words[2])));
                    break;
                }
            }
        }

    }

    //Making moves for a requested commandsx.txt
    public static void Movement(String words, String[] moves, FileWriter writer) throws IOException {
        boolean board_flag = true;
        int stepCount=moves.length/2;
        for(int i = 0 ; i<AllIn.size() ; i++){
            if (AllIn.get(i).getName().contains(words)) {
                try{
                    if(AllIn.get(i).getMove()==stepCount) {
                        //in this method the location of chars will be changed.
                        for ( int j = 0; j < moves.length; j = j+2 ) {
                            int locX = AllIn.get(i).getLocationY()+Integer.parseInt(moves[j]);
                            int locY = AllIn.get(i).getLocationX()+Integer.parseInt(moves[j+1]);
                            //movements and methods
                            if(AllIn.get(i).getName().contains("O")){
                                if((locX >= 0 && locY>=0) || (locX < rows && locY < rows)){
                                    OrkHealing(AllIn.get(i));
                                }
                                 }
                            if(BoardExceedCheck(locX,locY,board_flag,j,writer)) {board_flag=false; break;}
                            if(CellCheckAndMove(Integer.parseInt(moves[j]),Integer.parseInt(moves[j+1]),i)) continue;
                            if(j+2 == moves.length){
                               if ("E".equals(AllIn.get(i).getName().substring(0, 1))) {
                                    ElfFinalRangedAttack(AllIn.get(i));
                                } else {
                                    Attack(AllIn.get(i));
                                }
                            }
                            else{
                                if(everyStepAttack.contains(AllIn.get(i).getName().substring(0,1))){
                                    Attack(AllIn.get(i));
                                }
                            }
                            }
                        if(board_flag){
                            PrintBoard(writer);
                            CharacterPrinter(AllIn, writer);
                        }
                    }
                    else{ throw new Move_Count_Check(); }
                }
                catch (Exception e ){
                    writer.write("\n"+"Error : Move sequence contains wrong number of move steps. Input line ignored."+"\n");
                    break;
                }
            }
        }
    }

    //Moving error
    static class Move_Count_Check extends Exception{
        Move_Count_Check(){ }
    }

    // Boundary error
    static class Boundary_Check extends Exception{
        Boundary_Check(){ }
    }

    //to print the stars to output like in sample
    public static void printStar(int n, FileWriter writer) throws IOException {
        if(n > 0){
            writer.write("*");
            printStar(n-1, writer);
        }
    }

    // to print the characters and requested feature
    public static void CharacterPrinter(ArrayList<Characters> CharArray, FileWriter writer) throws IOException {
        writer.write("\n");
        for ( Characters characters : CharArray ) {
            if (characters.getName().contains("E")) {
                if (String.valueOf(characters.getHp()).length() == 1) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "      (" + Constants.getElfHp() + ")");
                } else if (String.valueOf(characters.getHp()).length() == 2) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "     (" + Constants.getElfHp() + ")");
                } else {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "    (" + Constants.getElfHp() + ")");
                }
            } else if (characters.getName().contains("D")) {
                if (String.valueOf(characters.getHp()).length() == 1) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "      (" + Constants.getDwarfHp() + ")");
                } else if (String.valueOf(characters.getHp()).length() == 2) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "     (" + Constants.getDwarfHp() + ")");
                } else {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "    (" + Constants.getDwarfHp() + ")");
                }
            } else if (characters.getName().contains("H")) {
                if (String.valueOf(characters.getHp()).length() == 1) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "      (" + Constants.getHumanHp() + ")");
                } else if (String.valueOf(characters.getHp()).length() == 2) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "     (" + Constants.getHumanHp() + ")");
                } else {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "    (" + Constants.getHumanHp() + ")");
                }
            } else if (characters.getName().contains("T")) {
                if (String.valueOf(characters.getHp()).length() == 1) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "      (" + Constants.getTrollHp() + ")");
                } else if (String.valueOf(characters.getHp()).length() == 2) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "     (" + Constants.getTrollHp() + ")");
                } else {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "    (" + Constants.getTrollHp() + ")");
                }
            } else if (characters.getName().contains("G")) {
                if (String.valueOf(characters.getHp()).length() == 1) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "      (" + Constants.getGoblinHp() + ")");
                } else if (String.valueOf(characters.getHp()).length() == 2) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "     (" + Constants.getGoblinHp() + ")");
                } else {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "    (" + Constants.getGoblinHp() + ")");
                }
            }
            if (characters.getName().contains("O")) {
                if (String.valueOf(characters.getHp()).length() == 1) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "      (" + Constants.getOrkHp() + ")");
                } else if (String.valueOf(characters.getHp()).length() == 2) {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "     (" + Constants.getOrkHp() + ")");
                } else {
                    writer.write("\n"+characters.getName() + "    " + characters.getHp() + "    (" + Constants.getOrkHp() + ")");
                }
            }
        }
        writer.write("\n"+"\n");
    }

    // Method for printing NxN the board which is given in initials1.txt
    public static void PrintBoard(FileWriter writer) throws IOException {
        int columns=rows;
        String CharName = "";
        for(int i =0;i<rows+2;i++){
            if(i==0){
                printStar(rows*2+2,writer);
            }
            for(int j = 0;j<columns+2;j++){
                if(j==0&& i!=0 && i!=rows+1){
                    writer.write("*");
                }
                if(j<columns && i!=rows+1 && i!=0) {
                    for ( Characters characters : AllIn ) {
                        if (characters.getLocationX() == i - 1 && characters.getLocationY() == j) {
                            CharName = characters.getName();
                        }
                    }
                    if(CharName.equals("")){
                        writer.write("  ");
                    }
                    else{
                        writer.write(CharName);
                    }
                    CharName = "";
                }
                if(j==columns+1&& i!=0 && i!=rows+1){
                    writer.write("*");
                }
            }
            if(i!=rows+1) {
                writer.write("\n");
            }
            else{
                printStar(rows*2+2, writer);
            }
        }
        writer.write("\n");
    }

    // Method for finalizing the game if there is a winner prints it.
    public static void whoWonCheck(FileWriter writer) throws IOException {
        writer.write("\n"+"Game Finished");
        if(Zordes.contains(AllIn.get(0).getName().substring(0,1))){
            writer.write("\n"+"Zorde wins");
        }
        else { writer.write("\n"+"Calliance wins"); }
    }

//****************************************************************************************
    public static void main(String[] args) throws IOException{

    //****************************************************************************
    // Reading files from txt
        ReadInitials(args[0]);
        String[] command_lines2 = ReadFromFile.readFile(args[1]);
        // Writing files to txt
        FileWriter writer = new FileWriter(args[2]);
    //*****************************************************************************
        //sorted for requested output.
        AllIn.sort(new Sorter());
        // first version of board
        PrintBoard(writer);
        CharacterPrinter(AllIn, writer);
        // Reads commands and separate the moves ; by ; after than makes the moves with rules.
        assert command_lines2 != null;
        for (String line:command_lines2){
            String[] words = line.split(" ");
            String[] moves = words[1].split(";");
            Movement(words[0],moves,writer);
        }
        // At the finale this checks for who won the game
        whoWonCheck(writer);
        writer.close();}}


