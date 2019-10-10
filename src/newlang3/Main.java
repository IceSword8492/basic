package newlang3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PushbackReader;

public class Main {
	public static void main (String[] args) throws Exception{
		String fileName = "./src/newlang3/src.txt";
		PushbackReader pr = new PushbackReader(new BufferedReader(new FileReader(fileName)));
		int ich;
		char ch;
		while (true) {
			ich = pr.read();
			if (ich < 0) {
				break;
			}
			ch = (char)ich;
			System.out.println(ch);
		}
		pr.close();
	}
}
