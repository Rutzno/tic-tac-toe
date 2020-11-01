package tictactoe;

import java.util.Scanner;

public class Main {

    static Scanner sc;
    static String coordinates;
    static int firstCoord, secondCoord;
    static char[][] fields = new char[3][3];
    static boolean xLined = false, oLined = false;
    static int numEmpty;
    static int numX = 0, numO = 0;
    static char[] players = {'X', 'O'};
    static String[] state = {"Game not finished", "Draw", "X wins", "O wins", "Impossible"};
    static String status = "";

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        StringBuilder subStr = new StringBuilder();
        // System.out.print("Enter cells: ");
        /*String input = sc.nextLine();
        char[] tabChar = input.toCharArray();
        int ci = -1;*/

        // System.out.println("Fill the cells with blank");
        for (int j = fields.length - 1; j >= 0; j--) { // row
            for (int i = 0; i < fields.length; i++) { // col
                fields[i][j] = ' ';
                /*fields[i][j] = tabChar[++ci]; // insert character in fields
                if (fields[i][j] == 'X') {
                    numX++;
                } else if (fields[i][j] == 'O') {
                    numO++;
                }*/
            }
        }
        int c = -1;
        while (!status.equals(state[1]) && !status.equals(state[2]) &&
                !status.equals(state[3])) {
            outputCells();

            if (c == 1) {
                c = -1;
            }
            selectCoordinate(players[++c]);

            outputCells();

            StringBuilder firstDiag = new StringBuilder();  // \
            StringBuilder secondDiag = new StringBuilder(); // /
            // second step for cols
            int p = -1, q = fields.length;
            for (int i = 0; i < fields.length; i++) { // col
                firstDiag.append(fields[++p][--q]);
                secondDiag.append(fields[i][i]);
                for (int j = fields.length - 1; j >= 0; j--) { // row
                    if (fields[i][j] != 'X' && fields[i][j] != 'O') {
                        numEmpty++;
                    } else {
                        subStr.append(fields[i][j]);
                    }
                }
                if (subStr.toString().equals("XXX") || firstDiag.toString().equals("XXX")
                        || secondDiag.toString().equals("XXX")) {
                    xLined = true;
                } else if (subStr.toString().equals("OOO") || firstDiag.toString().equals("OOO")
                        || secondDiag.toString().equals("OOO")) {
                    oLined = true;
                }
                subStr = new StringBuilder();
            }

            checkStatus();
        }
    }

    private static void checkStatus() {
        int difference = (numX > numO) ? numX - numO : numO - numX;

        if (((oLined) && (xLined)) || (difference >= 2)) {
            status = state[4];
            System.out.println(status); // Impossible
        } else if (oLined) {
            status = state[3];
            System.out.println(status); // O wins
        } else if (xLined) {
            status = state[2];
            System.out.println(status); // X wins
        } else if (((!oLined) && (!xLined)) && (numEmpty != 0)) {
            status = state[0];
            System.out.println(status); // Game not finished
        } else if (((!oLined) && (!xLined)) && (numEmpty == 0)) {
            status = state[1];
            System.out.println(status); // Draw
        }
    }

    private static void outputCells() {
        StringBuilder subStr = new StringBuilder();
        numEmpty = 0;
        System.out.println("---------");
        for (int j = fields.length - 1; j >= 0; j--) { // row
            System.out.print("|");
            for (int i = 0; i < fields.length; i++) { // col
                System.out.print(" "+fields[i][j]);
                if (fields[i][j] != 'X' && fields[i][j] != 'O'){
                    numEmpty++;
                } else {
                    subStr.append(fields[i][j]);
                }
            }
            if (subStr.toString().equals("XXX")){
                xLined = true;
            } else if (subStr.toString().equals("OOO")){
                oLined = true;
            }
            subStr = new StringBuilder();
            System.out.println(" |");
        }
        System.out.println("---------");
    }

    private static void selectCoordinate(char player) {
        while (true) {
            System.out.print("Enter the coordinates: ");
            coordinates = sc.nextLine();
            String[] coordTab = coordinates.split("");
            try {
                firstCoord = Integer.parseInt(coordTab[0]);
                secondCoord = Integer.parseInt(coordTab[2]);
            } catch (NumberFormatException nfe){
                System.out.println("You should enter numbers!");
                continue;
            }

            if (firstCoord <= 0 || firstCoord > 3 || secondCoord <= 0 || secondCoord > 3){
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                break;
            }
        }
        boolean okay = false;
        for (int i = 0; i < fields.length; i++) { // col
            for (int j = fields.length - 1; j >= 0; j--) { // row
                if ((i == (firstCoord - 1)) && (j == (secondCoord - 1))) {
                    if (String.valueOf(fields[i][j]).equals("_") ||
                            String.valueOf(fields[i][j]).equals(" ")) {
                        fields[i][j] = player; // affectation
                        if (fields[i][j] == 'X') {
                            numX++;
                        } else if (fields[i][j] == 'O') {
                            numO++;
                        }
                    } else {
                        System.out.println("This cell is occupied! Choose another one!");
                        selectCoordinate(player);
                    }
                    okay = true;
                    break;
                }
            }
            if (okay) {
                break;
            }
        }
    }
}
