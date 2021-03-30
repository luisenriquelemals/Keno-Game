import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import sun.jvm.hotspot.utilities.Assert;

import java.util.Arrays;
import java.util.Vector;

class MyTest {

	static StartGame var;
	static int i, x, y, z;
	static Vector <Node> randomNumbers1, randomNumbers2, randomNumbers3, randomNumbers4;
	static Vector <Node> matchNumbers1, matchNumbers2, matchNumbers3, matchNumbers4, matchNumbers5;

	@BeforeAll
	static void setup() {
		var = new StartGame();
		i = 0;
		x = 20;
		y = 40;
		z = 60;
		randomNumbers1 = var.getRandomNumbers(10);
		randomNumbers2 = var.getRandomNumbers(10);
		randomNumbers3 = var.getRandomNumbers(10);
		randomNumbers4 = var.getRandomNumbers(10);

		matchNumbers1 = new Vector<Node>();
		matchNumbers1.add(new Node(45,15,5));
		matchNumbers1.add(new Node(73,10,19));
		matchNumbers1.add(new Node(12,26,14));

		matchNumbers2 = new Vector<Node>();
		matchNumbers2.add(new Node(45,15,5));
		matchNumbers2.add(new Node(73,10,19));
		matchNumbers2.add(new Node(12,26,14));

		matchNumbers3 = new Vector<Node>();
		matchNumbers3.add(new Node(45,15,5));
		matchNumbers3.add(new Node(73,10,19));
		matchNumbers3.add(new Node(12,26,14));
		matchNumbers3.add(new Node(1,1,0));
		matchNumbers3.add(new Node(9,8,10));

		matchNumbers4 = new Vector<Node>();
		matchNumbers4.add(new Node(45,15,5));
		matchNumbers4.add(new Node(73,10,19));
		matchNumbers4.add(new Node(12,26,14));
		matchNumbers4.add(new Node(1,1,0));
		matchNumbers4.add(new Node(9,8,10));

		matchNumbers5 = new Vector<>();


	}

	/*Test cases for check the generation of the 80 numbers */
	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4 ,5 , 6, 7, 8, 9 , 10, 11, 12 , 13, 14, 15, 16, 17, 18, 19, 20})
	void generateNumbersTest1(int num) {

		assertEquals(num, var.numbers.get(i).getNumber(), "Not Expected value!");
		i++;
	}

	@ParameterizedTest
	@ValueSource(ints = { 21, 22, 23, 24 ,25 , 26, 27, 28, 29 , 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40})
	void generateNumbersTes2(int num) {

		assertEquals(num, var.numbers.get(x).getNumber(), "Not Expected value!");
		x++;
	}

	@ParameterizedTest
	@ValueSource(ints = {41, 42, 43, 44 ,45 , 46, 47, 48, 49 , 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60})
	void generateNumbersTest3(int num) {

		assertEquals(num, var.numbers.get(y).getNumber(), "Not Expected value!");
		y++;
	}

	@ParameterizedTest
	@ValueSource(ints = {61, 62, 63, 64 ,65 , 66, 67, 68, 69 , 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80})
	void generateNumbersTes4(int num) {

		assertEquals(num, var.numbers.get(z).getNumber(), "Not Expected value!");
		z++;
	}

	/*------------------------------------------------------------------------------------------*/

	/*Test for check the generate random number method*/

	@Test
	void randomNumbersTes1() {
		/*checking if they are not equals to know that they are no duplicate*/
		assertFalse(Arrays.equals(randomNumbers1.toArray(), randomNumbers2.toArray() ));
	}

	@Test
	void randomNumbersTes2() {
		/*checking if they are not equals to know that they are no duplicate*/
		assertFalse(Arrays.equals(randomNumbers1.toArray(), randomNumbers3.toArray() ));
	}

	@Test
	void randomNumbersTes3() {
		/*checking if they are not equals to know that they are no duplicate*/
		assertFalse(Arrays.equals(randomNumbers1.toArray(), randomNumbers4.toArray() ));
	}

	@Test
	void randomNumbersTes4() {
		/*checking if they are not equals to know that they are no duplicate*/
		assertFalse(Arrays.equals(randomNumbers2.toArray(), randomNumbers3.toArray() ));
	}

	@Test
	void randomNumbersTes5() {
		/*checking if they are not equals to know that they are no duplicate*/
		assertFalse(Arrays.equals(randomNumbers2.toArray(), randomNumbers4.toArray() ));
	}

	@Test
	void randomNumbersTes6() {
		/*checking if they are not equals to know that they are no duplicate*/
		assertFalse(Arrays.equals(randomNumbers3.toArray(), randomNumbers4.toArray() ));
	}

	/*------------------------------------------------------------------------------------------*/
	/*Test for check the match number method*/
	@Test
	void matchNumbersTes1() {
		/*checking if they are match numbers, with these two vector we should get 3 matches*/
		Vector<Node> matchNumbers = var.getMatchNumbers(matchNumbers1, matchNumbers2);

		assertEquals( 3 , matchNumbers.size(),  "Wrong Value!" );
	}

	@Test
	void matchNumbersTes2() {
		/*checking if they are match numbers, with these two vector we should get 5 matches*/
		Vector<Node> matchNumbers = var.getMatchNumbers(matchNumbers3, matchNumbers4);

		assertEquals( 5 , matchNumbers.size(),  "Wrong Value!" );
	}

	@Test
	void matchNumbersTes3() {
		/*checking if they are match numbers, with these two vector we should get 3 matches*/
		Vector<Node> matchNumbers = var.getMatchNumbers(matchNumbers1, matchNumbers3);

		assertEquals( 3 , matchNumbers.size(),  "Wrong Value!" );
	}

	@Test
	void matchNumbersTes4() {
		/*checking if they are match numbers, with these two vector we should get 3 matches*/
		Vector<Node> matchNumbers = var.getMatchNumbers(matchNumbers2, matchNumbers4);

		assertEquals( 3 , matchNumbers.size(),  "Wrong Value!" );
	}

	@Test
	void matchNumbersTes5() {
		/*checking if they are match numbers, with these two vector we should get 0 matches*/
		Vector<Node> matchNumbers = var.getMatchNumbers(matchNumbers4, matchNumbers5);

		assertEquals( 0 , matchNumbers.size(),  "Wrong Value!" );

	}





}// mend of StartGameTest
