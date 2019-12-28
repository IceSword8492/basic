package newlang5;

public class Main {
    /**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		LexicalAnalyzer lex;
		LexicalUnit		first;
		Environment		env;

		System.out.println("basic parser");
		lex = new LexicalAnalyzerImpl("./src/newlang3/src.txt");
		env = new Environment(lex);
		first = lex.peek();

		if (Program.isFirst(first)) {
			Node handler = Program.getHandler(first, env);
			handler.parse();
			System.out.println(handler);
			System.out.println("value = " + handler.getValue());
		}
		else System.out.println("syntax error");
	}
}
