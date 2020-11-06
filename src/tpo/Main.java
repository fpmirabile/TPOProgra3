package tpo;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int len = enterLength();
		Integer caseLimit = enterCaseLimits();
		boolean enableDebug = enterEnableDebug();
		Password pass = new Password(caseLimit, enableDebug);
		pass.crack(len);
	}
	
	private static boolean enterEnableDebug() {
		Boolean result = null;
		do {
			System.out.println("Desea activar la vista debug? (s/n/q)");
			Scanner sc = new Scanner(System.in);
			String value = sc.next();
			if (value.equalsIgnoreCase("s") || value.equalsIgnoreCase("n")) {
				result = value.equalsIgnoreCase("s");
			} else if (value.equalsIgnoreCase("q")) {
				System.exit(0);
			} else {
				System.out.println("Valor no valido.");
			}
		} while (result == null);
		
		return result.booleanValue();
	}

	private static Integer enterCaseLimits() {
		System.out.println("A continuacion, usted puede especificar si quiere un limite de casos por letra, ingrese cualquier numero menor o igual a 0 si no desea limitar los resultados");
		Integer value = null;
		do {
			try {
				System.out.println("Ingrese un numero de casos que desea conseguir: ");
				Scanner sc = new Scanner(System.in);
				value = sc.nextInt();
			} catch (Exception ex) {
				System.out.println("No ha ingresado un numero.");
			}
		} while (value == null);
		
		return value;
	}

	private static int enterLength() {
		int len = -1;
		System.out.println("Indique la cantidad de letras que desea ingresar (Mayor a 3):");
		do {
			Scanner sc = new Scanner(System.in);
			String wroteLength = sc.next();
			if (wroteLength.equals(":q")) {
				System.exit(0);
			}
			
			try {
				len = Integer.valueOf(wroteLength);
				if (len < 4) {
					System.out.println("Numero no valido, vuelva a intentarlo.");
				}
			} catch (Exception ex) {
				System.out.println("No es valido, vuelva a intentarlo. Escriba :q para salir.");
			}
		} while (len < 4);
		
		return len;
	}
}
