package tpo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		int len = 6;
		Password pass = new Password();
		pass.crack(len);
	}
}
