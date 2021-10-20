package battleship;

import java.util.Scanner;

public class Main {
static Scanner sc;
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        Map map = new Map();
        Ships ships = new Ships();
        Game game = new Game();

        String [][] p1Map= map.getEmptyMap();                                                                           //Player one map.
        String [][] p2Map= map.getEmptyMap();                                                                           //Player two map.
        //Map.showMap(p1Map);

        System.out.println("Player 1, place your ships on the game field");                                             //placing ships for player one

        p1Map = ships.getInputAndPlaceShips(p1Map);

        Game.pass();

        System.out.println("Player 2, place your ships on the game field");                                             //placing ships for player two

        p2Map = ships.getInputAndPlaceShips(p2Map);

        Game.pass();



        game.start(p1Map, p2Map);
    }

}

class Map {                                                                                                             //Class map can create map from String[11][11] and can print it as map

    public static String[][] getCopy (String [][] a) {                                                                  //returns new String[][] with values of map in parameters
        String [][] result = new String[11][11];

        for (int i = 0; i < 11; i++) {
            for (int y = 0; y < 11; y++) {
                result[i][y] = a[i][y];
            }
        }

        return result;
    }

    private String [][] map = new String[11][11];

    private void initialize() {                                                                                         //creates empty map with Letters and numbers
        for (int i = 0; i < 11; i++) {
            for (int y = 0; y < 11; y++) {

                if(i == 0 && y == 0) {                                                                                  // Makes top left corner empty.
                    map[i][y] = " ";
                    continue;
                }

                if(i == 0) {                                                                                            //initializing numbers
                    map[0][y] = String.valueOf(y);
                    continue;
                }

                if(y == 0) {                                                                                            //initializing letters
                    map[i][0] = String.valueOf( (char) ((char)64 + i));
                    continue;
                }

                map[i][y] = "~";                                                                                        //fog of war
            }
        }
    }

    public static void showMap(String [][] map) {                                                                       //prints arrays as map
        for (int i = 0; i < 11; i++) {
            for (int y = 0; y < 11; y++) {
                System.out.print(map[i][y]+" ");
            }
            System.out.println();
        }
    }

    public Map() {                                                                                                       //constructor calls initialize method
    initialize();
    }

    public String [][] getEmptyMap() {                                                                                  //Returns copy of initialized empty map
        return  getCopy(map);
    }
}

class Ships{

    private String wrongFormatMessage = "Please enter coordinates in according format :A1 B2\n"+"> ";

    private int startX;                                                                                                 //stores valid and converted to int user input coordinates.
    private int startY;
    private int endX;
    private int endY;

    private String [][] map;

    private String[] ShipsLocations = new String[17];

    private String [] placeMessages = new String[]{"Enter the coordinates of the Aircraft Carrier (5 cells):\n","Enter the coordinates of the Battleship (4 cells):\n","Enter the coordinates of the Submarine (3 cells):\n","Enter the coordinates of the Cruiser (3 cells):\n", "Enter the coordinates of the Destroyer (2 cells):\n"};
                                                                                                                        //Messages for each ship.
    private int [] ShipLengths = new int[]{5,4,3,3,2};                                                                  //lengths of each ship.

    public String [][] getInputAndPlaceShips( String [][] map){
        this.map = Map.getCopy(map);
        Map.showMap(this.map);
        for (int i = 0; i < 5 ; i++) {
            System.out.print(placeMessages[i] +  "\n> ");                                                               //printing Messages for each boat type.
            checkAndConvertInputAndPlaceShips(ShipLengths[i]);
            Map.showMap(this.map);
        }
        return this.map;
    }


    private void checkAndConvertInputAndPlaceShips (int length){
        while (true) {
            String[] input = Main.sc.nextLine().split(" ");                                                       //converting user input to String[].
            if (input.length != 2){                                                                                     //checking if array has 2 Strings.
                System.out.print(wrongFormatMessage);
                continue;
            }
            if (!(input [0].length() <= 3 || input [1].length() <= 3) ){                                                //checking if array String have at least 3 elements.
                System.out.print(wrongFormatMessage);
                continue;
            }
            if (input [0].equals(input [1])){                                                                           //checking if strings are not equal
                System.out.print("Coordinates should be Different!\n" + wrongFormatMessage);
                continue;
            }

            String tempLetter1 = String.valueOf(input [0].charAt(0));                                                   //converting String[] into Strings.
            String tempNumber1 = String.valueOf(input [0].charAt(1));
            String tempLetter2 = String.valueOf(input [1].charAt(0));
            String tempNumber2 = String.valueOf(input [1].charAt(1));
            if(input [0].length() == 3) {                                                                               //checking if coordinates are longer made of 3 char.
                tempNumber1 += input [0].charAt(2);                                                                     //adding char to string if String[] longer.
            }
            if(input [1].length() == 3) {                                                                               //checking if coordinates are longer made of 3 char.
                tempNumber2 += input [1].charAt(2);                                                                     //adding char to string if String[] longer.
            }

                                                                                                                        //checking if input parameters are in accepted format.
            if ((tempLetter1.equals("A") || tempLetter1.equals("B") || tempLetter1.equals("C") || tempLetter1.equals("D") || tempLetter1.equals("E") || tempLetter1.equals("F") || tempLetter1.equals("G") || tempLetter1.equals("H") || tempLetter1.equals("I") || tempLetter1.equals("J"))
                &&
                (tempNumber1.equals("1") || tempNumber1.equals("2") || tempNumber1.equals("3") || tempNumber1.equals("4") || tempNumber1.equals("5") || tempNumber1.equals("6") || tempNumber1.equals("7") || tempNumber1.equals("8") ||tempNumber1.equals("9") ||tempNumber1.equals("10"))
                &&
                (tempLetter2.equals("A") || tempLetter2.equals("B") || tempLetter2.equals("C") || tempLetter2.equals("D") || tempLetter2.equals("E") || tempLetter2.equals("F") || tempLetter2.equals("G") || tempLetter2.equals("H") || tempLetter2.equals("I") || tempLetter2.equals("J"))
                &&
                (tempNumber2.equals("1") || tempNumber2.equals("2") || tempNumber2.equals("3") || tempNumber2.equals("4") || tempNumber2.equals("5") || tempNumber2.equals("6") || tempNumber2.equals("7") || tempNumber2.equals("8") ||tempNumber2.equals("9") ||tempNumber2.equals("10"))
                )
            {
                convertInputToCoordinates(tempLetter1, Integer.valueOf(tempNumber1), tempLetter2, Integer.valueOf(tempNumber2));                              //converting coordinates values to ints

            } else {
                System.out.print(wrongFormatMessage);
                continue;
            }
            reverseOrder();                                                                                             //if input is in reverse order, reversing values. So start equals to the smallest value.

            if (wrongLength(length)){                                                                                   //checks if length between coordinates equals to the length of ship.
                System.out.print("Error! Wrong length of the Submarine! Try again:\n\n> ");
                continue;
            }

            if (wrongLocation()){                                                                                       //checks if ships are placed only horizontally or vertically.
                System.out.print("Error! Wrong ship location! Try again:\n\n> ");
                continue;
            }

            if(placeShip(length)){                                                                                      //sets ships and checks if they have a gap between. (One field vertically and one horizontally on each side.)
                System.out.print("Error! You placed it too close to another one. Try again:\n\n> ");
                continue;
            }
            break;
        }
    }

    private void convertInputToCoordinates(String a, int b, String c, int e){
        startX = b;

        startY = convertLettersToInt(a);

        endX = e;

        endY = convertLettersToInt(c);
    }

    public static int convertLettersToInt(String str) {
        switch (str){
            case "A":
            return 1;

            case "B":
            return 2;


            case "C":
            return 3;


            case "D":
            return 4;


            case "E":
            return 5;


            case "F":
            return 6;


            case "G":
            return 7;


            case "H":
            return 8;


            case "I":
            return 9;


            case "J":
            return 10;

        }
        return 0;
    }

    private void reverseOrder(){
        if(endX < startX) {
            int temp= startX;
            startX = endX;
            endX = temp;
        }

        if(endY < startY) {
            int temp= startY;
            startY = endY;
            endY = temp;
        }
    }

    private boolean wrongLength(int length){                                                                            //returns true for wrong length.

        if(startY == endY && (startX + length-1) != endX){                                                              //checking horizontal values.
            return true;
        }

        if(startX == endX && (startY + length-1) != endY){                                                              //checking vertical values.
            return true;
        }

        return false;
    }

    private boolean wrongLocation(){                                                                                    //returns true if ships are placed diagonally.
        if( startY != endY && startX != endX) {
            return true;
        }

        return false;
    }




    private Boolean placeShip(int ShipLength) {
        String[][]tempMap = Map.getCopy(map);
        if(startY == endY) {                             //horizontal placement
            for(int i = 0; i < ShipLength; i++) {                                                                       //checking neighboring elements for fist cell

                    if (i == 0) {
                        if(startX>0) {
                            if (tempMap[startY][startX - 1].equals("O")) {
                                return true;
                            }
                        }
                        if(startY>0) {
                            if (tempMap[startY - 1][startX].equals("O")) {
                                return true;
                            }
                        }
                        if(startY<10) {
                            if (tempMap[startY + 1][startX].equals("O")) {
                                return true;
                            }
                        }
                    }
                                                                                                                        //checking neighboring elements for cells in the middle
                if(i > 0 && i < ShipLength-1) {

                    if(startY > 0 && startX+i <= 10) {
                        if (tempMap[startY-1][startX+i].equals("O")) {
                            return true;
                        }
                    }

                    if(startY < 10 && startX+i <= 10) {
                        if (tempMap[startY+1][startX+i].equals("O")) {
                            return true;
                        }
                    }

                }                                                                                 //checking neighboring elements for last cell

                if(i == ShipLength-1) {

                    if(startX+i+1 <= 10) {
                        if (tempMap[startY][startX+i+1].equals("O")) {
                            return true;
                        }
                    }

                    if(startY > 0 && startX+i <= 10) {
                        if (tempMap[startY-1][startX+i].equals("O")) {
                            return true;
                        }
                    }

                    if(startY < 10 && startX+i <= 10) {
                        if (tempMap[startY+1][startX+i].equals("O")) {
                            return true;
                        }
                    }
                }
                tempMap[startY][startX+i]="O";
            }

        } else {                                                                                                        //vertical placement
            for(int i = 0; i < ShipLength; i++){                                                                        //checking neighboring elements for fist cell

                    if (i == 0) {

                        if(startY>0) {
                            if (tempMap[startY-1][startX].equals("O")) {
                                return true;
                            }
                        }
                        if(startX>0) {
                            if (tempMap[startY][startX- 1].equals("O")) {
                                return true;
                            }
                        }
                        if(startX<10) {
                            if (tempMap[startY][startX+ 1].equals("O")) {
                                return true;
                            }
                        }

                    }



                    if(i > 0 && i < ShipLength-1){                                                                      //checking neighboring elements for cells in the middle

                        if(startX > 0 && startY+i <= 10) {
                            if (tempMap[startY+i][startX-1].equals("O")) {
                                return true;
                            }
                        }

                        if(startX < 10 && startY+i <= 10) {
                            if (tempMap[startY+i][startX+1].equals("O")) {
                                return true;
                            }
                        }

                    }




                    if(i == ShipLength-1){                                                             //checking neighboring elements for last cell

                        if(startY+i+1 <= 10) {
                            if (tempMap[startY+i+1][startX].equals("O")) {
                                return true;
                            }
                        }

                        if(startX > 0 && startY+i <= 10) {
                            if (tempMap[startY+i][startX-1].equals("O")) {
                                return true;
                            }
                        }

                        if(startX < 10 && startY+i <= 10) {
                            if (tempMap[startY+i][startX+1].equals("O")) {
                                return true;
                            }
                        }

                    }

                tempMap[startY+i][startX]="O";
            }
        }
     map=tempMap;

     return false;
    }

}

class Game {
    private int x;
    private int y;

    String[][] p1map;
    String[][] p2map;

    String [][] fogwarP1Map = new Map().getEmptyMap();
    String [][] fogwarP2Map = new Map().getEmptyMap();

    private String coordinates;

    public static void pass(){
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        Main.sc.nextLine();
    }

    private boolean allShipsAreDown(String [][] map) {                                                                  //check if all ships are sunk.
        for(int i = 0; i < 11; i++) {
            for(int y = 0; y < 11; y++) {
                if(map[i][y].equals("O")) {
                    return false;
                }
            }
        }
        System.out.println("You sank the last ship. You won. Congratulations!");

        return true;
    }

    public void start (String [][] p1mapArg ,String [][] p2mapArg) {                                                                               //Starts game.
        p1map = p1mapArg;
        p2map = p2mapArg;

        System.out.println("\nThe game starts!\n");

        while (true){                                                                                  //iterating while there are some O cells.

            Map.showMap(fogwarP1Map);
            System.out.println("---------------------");
            Map.showMap(p1map);

            System.out.print("\nPlayer 1, it's your turn:\n"+"> ");
            coordinates = Main.sc.nextLine();                                                                           //passing coordinates.

            if(CheckandCconvertCoordintes()) {                                                                          //checking coordinates.
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            shoot(p2map, fogwarP1Map);
            if(allShipsAreDown(p2map)){
                break;
            }

            pass();

            Map.showMap(fogwarP2Map);
            System.out.println("---------------------");
            Map.showMap(p2map);

            System.out.print("\nPlayer 2, it's your turn:\n"+"> ");
            coordinates = Main.sc.nextLine();                                                                           //passing coordinates.

            if(CheckandCconvertCoordintes()) {                                                                          //checking coordinates.
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            shoot(p1map, fogwarP2Map);
            if(allShipsAreDown(p1map)){
                break;
            }

            pass();

        }
    }

    private boolean CheckandCconvertCoordintes () {                                                                     //Checking and converting coordinates to int values.
        boolean result = true;
        for (int i = 0; i < 10; i ++) {                                                                                 //if first char from coordinates is char from 'A' to 'J'.
           if (coordinates.charAt(0) == 65+i){
               result = false;                                                                                          // sets result flag to false.
           }
        }

        if(result){                                                                                                     // if iteration did not change flag, execution of method will stop with true value, that will print error message.
            return true;
        }

        y = Ships.convertLettersToInt(String.valueOf(coordinates.charAt(0)));                                           //converting Letters to int.
        String tempX = String.valueOf(coordinates.charAt(1));                                                           // converting int coordinates.
        if ( coordinates.length() == 3){                                                                                // if int coordinate is 10 adding 0 to int coordinates.
            tempX += coordinates.charAt(2);
        }
        x = Integer.valueOf(tempX);                                                                                     //final conversion of int coordinate.

        if (x < 0 || x > 10) {                                                                                          //checking if int is within acceptable range of values.

            return true;
        }

        return false;
    }

    private void shoot (String[][] map, String[][] fogwar) {                                                                               //changing values of user input coordinates.

        if (!map[y][x].equals("~")){                                                                                     //if cell equals O.
            map[y][x]="X";
            fogwar[y][x]="X";
            if (ShipIsSunk(map)){                                                                                     //checking if ship is sunk
                System.out.println("You sank a ship!");
            }
            System.out.println("\nYou hit a ship!");
        } else {
            map[y][x]="M";                                                                                              //if cell is not O.
            fogwar[y][x]="M";
            System.out.println("You missed!");
        }
    }

    private boolean ShipIsSunk(String [][] map){

        if ( (x + 1 < 11) && (x - 1 >= 0) && ((map[y][x+1].equals("X") || map[y][x+1].equals("O")) || (map[y][x-1].equals("X") || map[y][x-1].equals("O")))  ) {                 //checking if ship is horizontal
            int horizontalForward = 1;

            while ((x + horizontalForward < 11) && (!map[y][x + horizontalForward].equals("~"))) {                      //checking forward
                if (map[y][x + horizontalForward].equals("O")) {
                    return false;
                }
                horizontalForward++;
            }


            int horizontalBackward = 1;
            while ((x - horizontalBackward >= 0) && (map[y][x - horizontalForward].equals("~"))) {                      //checking backward
                if (map[y][x - horizontalForward].equals("O")) {
                    return false;
                }
                horizontalBackward++;
            }

        }


        else {                                                                                                                              //if ship is vertical
            int verticalForward = 1;
            while ((y + verticalForward < 11) && !map[y + verticalForward][x].equals("~")) {                            //checking forward
                if (map[y + verticalForward][x].equals("O")) {
                    return false;
                }
                verticalForward++;
            }


            int verticalBackward = 1;
            while ((y - verticalBackward >= 0) && !map[y - verticalForward][x].equals("~")) {                           //checking backward
                if (map[y - verticalBackward][x].equals("O")) {

                    return false;
                }
                verticalBackward++;
            }
        }


        return true;
    }

}