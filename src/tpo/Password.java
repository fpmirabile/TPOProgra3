package tpo;

import java.util.stream.Stream;

/**
 * Based on geeksforgeeks model
 * https://www.geeksforgeeks.org/generate-passwords-given-character-set/
 *
 */
public class Password {
	// Can�t have less than 4 characters because of the rules 
	private final int MIN_CHARACTER_AMOUNT = 4;
	
	final Character[] LOWER_CASE_LETTERS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	final Character[] UPPER_CASE_LETTERS;
	final Character[] DIGITS = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	final Character[] SPECIAL_CHARACTERS = { '+', '-', '*', '/', '%', '&' };

	Character[] totalGroup;
	final Integer caseLimited;
	int totalCases = 0;
	
	boolean enableDebugging = false;

	public Password(Integer caseLimit, boolean enableDebug) {
		UPPER_CASE_LETTERS = new Character[LOWER_CASE_LETTERS.length];
		for (int i = 0; i < LOWER_CASE_LETTERS.length; i++) {
			UPPER_CASE_LETTERS[i] = Character.toUpperCase(LOWER_CASE_LETTERS[i]);
		}

		// How to merge multiples arrays
		// Taken from https://www.techiedelight.com/merge-multiple-arrays-java/
		totalGroup = Stream.of(LOWER_CASE_LETTERS, UPPER_CASE_LETTERS, DIGITS, SPECIAL_CHARACTERS).flatMap(Stream::of)
				.toArray(Character[]::new);
		
		this.caseLimited = caseLimit.intValue() <= 0 ? null : caseLimit;
		this.enableDebugging = enableDebug;
	}

	/**
	 * Generates all the possible passwords Since we need to validate at least: - 1
	 * upper and lower case letter - 1 digit - a special character The password
	 * length can't be lower than 3
	 */
	public void generate(int i, String createdPassword, int passwordLength) {
		// Base case
		if (createdPassword.length() == passwordLength) {
			String printStr = createdPassword + (enableDebugging ? " (Password valida)" : "");
			System.out.println(printStr);
			totalCases++;
			return;
		}

		// iterate through the array on each character
		// Worst case O(4n^4) ( or is it O(N!) ? )
		for (int x = i; x < totalGroup.length; x++) {
			// if the configuration is saying to limit a total of cases, then we stop the job.
			if (totalCases > 0 && caseLimited != null && totalCases == caseLimited.intValue()) {
				break;
			}
			
			char currentChar = totalGroup[x];
			String nextPassword = createdPassword + currentChar;
			if (isValidPassword(nextPassword, passwordLength)) { // Complexity O (4N)
				// Recursive complexity (I guess is substraction) then
				// a = 1, b = 1, k = n
				// O(n^k+1)= O(n^2)
				generate(x, nextPassword, passwordLength);
			} else {
				if (enableDebugging) {
					System.out.println("Password parcial no valida: " + nextPassword);
				}
				
				nextPassword = nextPassword.substring(0, nextPassword.length() - 1);
			}
		}
	}

	public void crack(int passwordLength) {
		if (passwordLength < MIN_CHARACTER_AMOUNT) {
			System.out.println("La cantidad de letras sugeridas no cumple con los requisitos del programa.");
			return;
		}
		
		// call for all required lengths
		// Complexity O(4n^5)
		for (int i = 0; i < totalGroup.length; i++) {
			String password = "" + totalGroup[i];
			generate(0, password, passwordLength); // Complexity O(4n^4)
		}
	}

	// Complexity in worst case is O(4N) 
	private boolean isValidPassword(String password, int passwordLength) {
		if (passwordLength >= MIN_CHARACTER_AMOUNT) {
			// This variable refers to how many coincidences of a character type we can have. 
			// For example if character length is 6, we can only have 2 type of each (2 lower, 2 digits, 1 symbol, 1 upper)
			// If we have 3 of a kind, then is a invalid password.
			int randomValuesAllowed = passwordLength > 4 ? (passwordLength / 2) - 1 : 0;
			int upperChars = searchCharacterInString(password, UPPER_CASE_LETTERS); // O(N) where N is the alphabetical uppercase (26)
			int digitsChar = searchCharacterInString(password, DIGITS); // O(N) where N is the all the digits (10)
			int lowerChars = searchCharacterInString(password, LOWER_CASE_LETTERS); // O(N) where N is the alphabetical lower case (26)
			int specialChars = searchCharacterInString(password, SPECIAL_CHARACTERS); // O(N) where N is the symbol amount (6)
			if (randomValuesAllowed > 0) {
				if (upperChars > randomValuesAllowed || digitsChar > randomValuesAllowed || lowerChars > randomValuesAllowed) {
					return false;
				}
			}
	
			if (password.length() == passwordLength) {
				if (upperChars == 0 || digitsChar == 0 || lowerChars == 0 || specialChars == 0) {
					return false;
				}

				if (specialChars > 1) {
					return false;
				}
			}
		}

		return true;
	}

	// Complexity O(N) where N is the array length
	private int searchCharacterInString(String password, Character[] array) {
		int totalCharacters = 0;
		for (int i = 0; i < array.length; i++) {
			char character = array[i];
			totalCharacters += password.chars().filter(ch -> ch == character).count();
		}

		return totalCharacters;
	}
}
