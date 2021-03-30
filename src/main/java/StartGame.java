import java.util.Random;
import java.util.Vector;

public class StartGame {

    /* 80 numbers with row and column*/
    Vector<Node> numbers;

     StartGame() {
         generateNumbers();
     }


    /*Generate the 80 numbers with the specific row and column of each numbers*/
    public void generateNumbers() {
        numbers = new Vector<Node>();
        int num = 1;

        // rows
        for(int x = 0; x<8; x++) {
            //columns
            for (int i = 0; i < 10; i++) {
                Node newNum = new Node(num, x, i);
                numbers.add(newNum);
                num++;
            }
        }


    }// end of generateNumbers


    public Vector<Node> getRandomNumbers (int spots) {
        Vector<Node> returnVector = new Vector<Node>();
        Vector<Integer> tempVec = new Vector<Integer>();
        Random rand = new Random();
        int i = 0;

        while (i < spots) {
            /*generate numbers from 0 to 79*/
            int index = rand.nextInt(80);
            /*we pick a number of the 80 numbers stored*/
            Node newNode = numbers.get(index);
            int tempNum = newNode.getNumber();
            if (!tempVec.contains(tempNum)) {
                tempVec.add(tempNum);
                returnVector.add(newNode);
                i++;
            }
        }


        return returnVector;
    }// end of getRandomNumbers

    /*method to get the numbers that have had a match */
    public Vector<Node> getMatchNumbers (Vector<Node> randomTwenty, Vector<Node> selectedNumbers) {

        Vector<Node> returnVector = new Vector<Node>();
        Vector<Integer> twentyTemp = new Vector<Integer>();
        int size = selectedNumbers.size();
        int i = 0;

        while (i < randomTwenty.size()) {
            twentyTemp.add(randomTwenty.get(i).getNumber());
            i++;
        }

        i = 0;
        while (i < size){
            Node newNode = selectedNumbers.get(i);
            int num = newNode.getNumber();
            if (twentyTemp.contains(num)) {
                returnVector.add(newNode);
            }

            i++;
        }


        return returnVector;
    }// end of getMatchNumbers



}// end of Start Game

class Node{
    int number;
    int row;
    int column;

    Node(int num, int r, int c) {
        number = num;
        row = r;
        column = c;

    }

    int getNumber() {
        return number;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }
}// end of Node

