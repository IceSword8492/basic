package newlang4;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        LexicalAnalyzer lex;
        LexicalUnit	first;
        Environment	env;

        System.out.println("basic parser\n");
        try {
            lex = new LexicalAnalyzerImpl("./src/newlang4/src.txt");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        env = new Environment(lex);
        try {
            first = lex.peek();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (Program.isFirst(first)) {
            Node handler = null;
            try {
                handler = Program.getHandler(first, env);
                handler.parse();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            System.out.println(handler);
            try {
                System.out.println("value = " + handler.getValue());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            System.out.println("syntax error");
        }
    }
}
