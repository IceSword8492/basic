package newlang4;

public class PrintFunc extends Function {
    @Override
    public Value invoke (ExprListNode arg) {
        for (Node a : arg.args) {
            System.out.println(a.toString());
        }
        return null;
    }

    @Override
    public String toString () {
        return "PRINT";
    }
}
