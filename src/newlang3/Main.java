package newlang3;


public class Main {
	public static void main (String[] args){
		String fileName = "./src/newlang3/src.txt";
		LexicalAnalyzer la;
		try {
			la = new LexicalAnalyzerImpl(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		LexicalUnit lu;

		try {
			while ((lu = la.get()).getType() != LexicalType.EOF) {
				System.out.println(lu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
