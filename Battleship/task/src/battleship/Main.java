package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static char[][] boardA = new char[10][10];
    static char[][] boardB = new char[10][10];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String hitEnter;

        //Initialize the array to have all tildes
        for (int i=0; i < 10; i++) {
            for (int j=0; j < 10; j++) {
                boardA[i][j] = '~';
                boardB[i][j] = '~';
            }
        }

        //Place ships for player A
        System.out.println("Player 1, place your ships on the game field");
        printBoard(boardA, false);
        processPlacement(sc, boardA);

        System.out.println("Press Enter and pass the move to another player");
        hitEnter = sc.nextLine();

        //place shipts for player B
        System.out.println("Player 2, place your ships on the game field");
        processPlacement(sc, boardB);

        startGame(sc, boardA, boardB);

        //printBoard(boardA, false);


    }

    static void startGame(Scanner sc, char[][] boardA, char[][] boardB) {
        //have to alternate between player 1 and player 2.  Player changes when shot is complete.  Keep

         while (!isGameOver(boardA) && !isGameOver(boardB)) {
             System.out.println("Hit enter when Player 1 is ready!");
             sc.nextLine();

            //get Player 1 shot on boardB
            printBoard(boardB,true);
            System.out.println("---------------------");
            printBoard(boardA,false);
            System.out.println("Player 1, it's your turn:\n");
            getShot(sc, boardB);

            if (!isGameOver(boardB)) {
                System.out.println("Hit enter when Player 2 is ready!");
                sc.nextLine();

                printBoard(boardA,true);
                System.out.println("---------------------");
                printBoard(boardB,false);
                System.out.println("Player 2, it's your turn:\n");
                getShot(sc, boardA);

            }
         }

         System.out.println("You sank the last ship.  You won. Congratulations!");
    }

    static boolean isGameOver(char[][] boardA) {
        for (int i=0; i < 10; i++) {
            for (int j=0; j < 10; j++) {
                if (boardA[i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    static void getShot(Scanner sc, char[][] board) {
        String shot;
        Boolean done = false;

        System.out.println("Take a shot!");

        while (!done) {
            shot = sc.next();
            done = applyShot(shot, board);
        }

        sc.nextLine();  //clear buffer from hitting enter
    }

    static boolean applyShot(String shot, char[][] board) {
        int shotY = getLetterIndex(shot.charAt(0));
        int shotX = Integer.parseInt(shot.substring(1)) - 1;

        if (shotY >= 0 && shotY <=9 && shotX >= 0 && shotX <=9) {
            switch (board[shotY][shotX]) {
                case 'O':
                    board[shotY][shotX] = 'X';
                    printBoard(board, true);
                    if (sankShip(board, shotX, shotY)) {
                        System.out.println("You sank a ship!  Specify new target:");
                    } else {
                        System.out.println("You hit a ship!");
                    }
                    break;
                case 'M':
                    printBoard(board, true);
                    System.out.println("You missed!");
                    break;
                case 'X':
                    printBoard(board, true);
                    System.out.println("You hit!");
                    break;
                case '~':
                    board[shotY][shotX] = 'M';
                    printBoard(board, true);
                    System.out.println("You missed!");
                    break;

            }

        } else {
            System.out.println("Error!  You entered invalid coordinates!  Try again, moron");
            return false;
        }

        return true;

    }

    static boolean sankShip (char[][] board, int shotX, int shotY) {
        int hitsOnShip = 0;
        boolean shipSunk = true;

        //look up
        if (shotY != 0) {
            for (int y = (shotY - 1); y >= 0; y--) {
                switch (board[y][shotX]) {
                    case 'X':
                        hitsOnShip++;
                        break;
                    case 'O':    //if there is an unsunk part of the ship.
                        shipSunk = false;
                    default:
                        y = -10;  //tweak the variable to exit loop.  This is just ugly.  can use literal like maxindex or swomething
                }
            }
        }
        //look down
        if (shotY < 9) {
            for (int y = (shotY + 1); y <= 9; y++) {
                switch (board[y][shotX]) {
                    case 'X':
                        hitsOnShip++;
                        break;
                    case 'O':    //if there is an unsunk part of the ship.
                        shipSunk = false;
                    default:
                        y = 10;  //tweak the variable to exit loop.  This is just ugly.  can use literal like maxindex or swomething
                }
            }
        }

        //look left
        if (shotX != 0) {
            for (int x = (shotX - 1); x >= 0; x--) {
                switch (board[shotY][x]) {
                    case 'X':
                        hitsOnShip++;
                        break;
                    case 'O':    //if there is an unsunk part of the ship.
                        shipSunk = false;
                    default:
                        x = -10;  //tweak the variable to exit loop.  This is just ugly.  can use literal like maxindex or swomething
                }
            }
        }

        //look right
        if (shotX < 9) {
            for (int x = (shotX + 1); x <= 9; x++) {
                switch (board[shotY][x]) {
                    case 'X':
                        hitsOnShip++;
                        break;
                    case 'O':    //if there is an unsunk part of the ship.
                        shipSunk = false;
                    default:
                        x = 10;  //tweak the variable to exit loop.  This is just ugly.  can use literal like maxindex or swomething
                }
            }
        }
        return shipSunk;
    }

    static void processPlacement(Scanner sc, char[][] board) {

        boolean done = false;

        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells)");
        while (!done) {
            done = readPlacement(board, sc, 5);
        }
        printBoard(board, false);

        System.out.println("Enter the coordinates of the Battleship (4 cells)");
        done = false;
        while (!done) {
            done = readPlacement(board, sc, 4);
        }
        printBoard(board, false);

        done = false;
        System.out.println("Enter the coordinates of the Submarine (3 cells)");
        while (!done) {

            done = readPlacement(board, sc, 3);
        }
        printBoard(board, false);


        done = false;
        System.out.println("Enter the coordinates of the Cruiser (3 cells)");
        while (!done) {
            done = readPlacement(board, sc, 3);
        }
        printBoard(board, false);

        done = false;
        System.out.println("Enter the coordinates of the Destroyer (2 cells)");
        while (!done) {
            done = readPlacement(board, sc, 2);
        }
        printBoard(board, false);

        sc.nextLine();   //clear buffer from when uer hit enter.

    }

    static boolean readPlacement(char[][] board, Scanner sc, int shipLength) {
        int startX, startY, endX, endY;

        String token1 = sc.next().trim();
        String token2 = sc.next().trim();

        if (!isValidToken(token1) || !isValidToken(token2)) {
            System.out.println("Error! invalid input, try again");
        } else {
//  input is valid if we got here
            startY = getLetterIndex(token1.charAt(0));
            startX = Integer.parseInt(token1.substring(1)) - 1;
            endY = getLetterIndex(token2.charAt(0));
            endX = Integer.parseInt(token2.substring(1)) - 1;

            try {
                checkShip(board, startX, startY, endX, endY, shipLength);

                try {
                    placeShip(board, startX, startY, endX, endY, shipLength);
                    return true;
                } catch (IOException e) {
                    System.out.println("Error!  IO Exception: " + e.getMessage());
                }

            } catch (IOException e) {
                System.out.println("Error!  IO Exception: " + e.getMessage());
            }
        }
        return false;

    }


    static void placeShip(char[][] board, int startX, int startY, int endX, int endY, int shipLength)  throws IOException {
        int startValue = 0;

        if (startX == endX && startY != endY) {
            //We have to start with the top pointf3 f
            startValue = (startY < endY ? startY : endY);
            //if the ship is valid, add it to the board
            for (int i = startValue; i < startValue + shipLength; i++) {
                board[i][startX] = 'O';
            }

        } else if (startX != endX && startY == endY) {
            startValue = (startX < endX ? startX : endX);
            for (int i = startValue; i < startValue + shipLength; i++) {
                board[startY][i] = 'O';
            }

        }

    }


    static void checkShip(char[][] board, int startX, int startY, int endX, int endY, int shipLength)  throws IOException {
        boolean isVertical, isHorizontal;
        int length, startValue = 0;

        boolean overlapError = false;

        if (startX == endX && startY != endY) {
            //static x and changing y means the ship is aligned vertically

            if (startY < endY) {
                length = endY - startY + 1;
            } else if (startY > endY) {
                length = startY - endY + 1;
            } else {
                throw new IOException("Error!  No such boat with length zero!");
            }

            if (length != shipLength) {
                throw new IOException("Error!  Boat length is " + length + " but it must be " + shipLength);
            } else {
                //We have to start with the topmost point
                startValue = (startY < endY ? startY : endY);

                //check each point of the placement to see if it is valid.
                for (int i = startValue; i < startValue + length; i++) {
                    checkPoint(board, startX, i);
//                        throw new IOException("Error!  Boat overlap, please try again");
                }
            }

        } else if (startX != endX && startY == endY) {
            //changing x and static y means the ship is aligned horizontally

            if (startX < endX) {
                length = endX - startX + 1;
            } else if (startX > endX) {
                length = startX - endX + 1;
            } else {
                throw new IOException("Error!  No such boat with length zero!");
            }

            if (length != shipLength) {
                throw new IOException("Error!  Boat length is " + length + " but it must be " + shipLength);
            } else {
                //We have to start with the leftmost point
                startValue = (startX < endX ? startX : endX);

                //check each point of the placement to see if it is valid.
                for (int i = startValue; i < startValue + length; i++) {
                        checkPoint(board, i, startY);
                }
            }

        } else {
            throw new IOException("Error!  Placement not horizontal or vertical!");
        }

    }


    static void checkPoint(char[][] board, int x, int y) throws IOException {

        //check for immediate overlap
        if (board[y][x] != '~') {
            throw new IOException("Error!  Boat overlap, please try again");
        }

        //check left perimeter
        if (x > 0) {
            if (board[y][x - 1] != '~') {
                throw new IOException("Error!  Boat too close to another.  Try again!");
            }
        }

        //check right perimeter
        if (x < 9) {
            if (board[y][x + 1] != '~') {
                throw new IOException("Error!  Boat too close to another.  Try again!");
            }
        }

        //check top perimeter
        if (y > 0) {
            if (board[y - 1][x] != '~') {
                throw new IOException("Error!  Boat too close to another.  Try again!");
            }
        }

        //check bottom perimeter
        if (y < 9) {
            if (board[y + 1][x] != '~') {
                throw new IOException("Error!  Boat too close to another.  Try again!");
            }
        }

    }


    static boolean isValidToken(String token1) {
        String tokenPattern = "[A-Ja-j][0-9][0-9]?";

        if (token1.matches(tokenPattern)) {
            char xValue = token1.charAt(0);
            int yValue = Integer.parseInt(token1.substring(1));

            if (yValue < 1 || yValue > 10) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    static int getLetterIndex(char fromValue) {
        switch (fromValue) {
            case 'A':
            case 'a':
                return 0;
            case 'B':
            case 'b':
                return 1;
            case 'C':
            case 'c':
                return 2;
            case 'D':
            case 'd':
                return 3;
            case 'E':
            case 'e':
                return 4;
            case 'F':
            case 'f':
                return 5;
            case 'G':
            case 'g':
                return 6;
            case 'H':
            case 'h':
                return 7;
            case 'I':
            case 'i':
                return 8;
            case 'J':
            case 'j':
                return 9;
            default:
                return -1;
        }
    }

    /**
     * So I think this addition to the char is pretty clever, but it may be shit programming practice unless quite localized.
     */
    static void printBoard(char[][] board, boolean fog) {

        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char nextRow = 'A';
        for (int i=0; i < 10; i++) {
            System.out.print(nextRow + " ");
            nextRow += 1;

            for (int j=0; j < 10; j++) {
                if (fog) {
                    if (board[i][j] == 'O') {
                        System.out.print("~ ");
                    } else {
                        System.out.printf("%c ", board[i][j]);
                    }
                } else {
                    System.out.printf("%c ", board[i][j]);
                }
            }
            System.out.println();
        }
    }

    static boolean checkPlacement() {
        return true;  //all good!
    }


}

