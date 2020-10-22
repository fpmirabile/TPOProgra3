package tpo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Based on geeksforgeeks model
 * https://www.geeksforgeeks.org/generate-passwords-given-character-set/
 *
 */
public class Password {
	final Character[] LOWER_CASE_LETTERS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	final Character[] UPPER_CASE_LETTERS;
	final Character[] DIGITS = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	final Character[] SPECIAL_CHARACTERS = { '+', '-', '*', '/', '%', '&' };

	public Password() {
		UPPER_CASE_LETTERS = new Character[LOWER_CASE_LETTERS.length];
		for (int i = 0; i < LOWER_CASE_LETTERS.length; i++) {
			UPPER_CASE_LETTERS[i] = Character.toUpperCase(LOWER_CASE_LETTERS[i]);
		}
	}
	
	/**
	 * Generates all the possible passwords Since we need to validate at least: - 1
	 * upper and lower case letter - 1 digit - a special character The password
	 * length can't be lower than 3
	 */
	public void generate(int passwordLength) {
		// How to merge multiples arrays
		// Taken from https://www.techiedelight.com/merge-multiple-arrays-java/
		Character[] totalGroup = Stream.of(LOWER_CASE_LETTERS, UPPER_CASE_LETTERS, DIGITS, SPECIAL_CHARACTERS)
				.flatMap(Stream::of).toArray(Character[]::new);

		Random rnd = new Random();
		for (int i = 0; i < totalGroup.length; i++) {
			int numberOfCharacters = 1;
			String nextPassword = "" + totalGroup[i];
			while (numberOfCharacters < passwordLength) {
				// No entiendo bien como recorrer esto
				nextPassword += totalGroup[rnd.nextInt(totalGroup.length - 1)];
				numberOfCharacters++;
				while (!isValidPassword(nextPassword, passwordLength)) {
					nextPassword = nextPassword.substring(0, nextPassword.length() - 1);
					numberOfCharacters--;
				}
			}
			
			System.out.println(nextPassword);
		}
	}

	/**
	 * Generates all the possible passwords Since we need to validate at least: - 1
	 * upper and lower case letter - 1 digit - a special character The password
	 * length can't be lower than 3
	 */
	public void generate(int i, String createdPassword, int passwordLength) {
		// How to merge multiples arrays
		// Taken from https://www.techiedelight.com/merge-multiple-arrays-java/
		Character[] totalGroup = Stream.of(LOWER_CASE_LETTERS, UPPER_CASE_LETTERS, DIGITS, SPECIAL_CHARACTERS)
				.flatMap(Stream::of).toArray(Character[]::new);

		
		// iterate through the array on each character
		for (int j = 0; j < totalGroup.length; j++) {
			char currentChar = totalGroup[j];			
			String nextPassword = createdPassword + totalGroup[j];
	
			while (!isValidPassword(createdPassword, passwordLength)) {
				
			}
		}

//		return;
	}

	public void crack(int passwordLength) {
		// call for all required lengths
		for (int i = 1; i <= passwordLength; i++) {
			generate(i, "", passwordLength);
		}
	}

	private boolean isValidPassword(String password, int passwordLength) {
		int randomValuesAllowed = passwordLength > 4 ? (passwordLength / 2) - 1 : 0;
		int upperChars = searchCharacterInString(password, UPPER_CASE_LETTERS);
		int digitsChar = searchCharacterInString(password, DIGITS);
		int lowerChars = searchCharacterInString(password, LOWER_CASE_LETTERS);
		int specialChars = searchCharacterInString(password, SPECIAL_CHARACTERS);
		if (randomValuesAllowed > 0) {
			if (upperChars > randomValuesAllowed || digitsChar > randomValuesAllowed || lowerChars > randomValuesAllowed
					|| specialChars > randomValuesAllowed) {
				return false;
			}
		}

		if (password.length() == passwordLength) {
			if (upperChars == 0 || digitsChar == 0 || lowerChars == 0 || specialChars == 0) {
				return false;
			}
		}

		return true;
	}

	private int searchCharacterInString(String password, Character[] array) {
		int totalCharacters = 0;
		for (int i = 0; i < array.length; i++) {
			char character = array[i];
			totalCharacters += password.chars().filter(ch -> ch == character).count();
		}

		return totalCharacters;
	}
}
