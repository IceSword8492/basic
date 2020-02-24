package newlang4;

import java.util.Hashtable;
import java.util.Map;

public class Environment {
	   LexicalAnalyzer input;
	   Map<String, Function> library;
	   Map<String, VariableNode> var_table;

	    public Environment(LexicalAnalyzer my_input) {
	        input = my_input;
	        library = new Hashtable<>();
	        library.put("PRINT", new PrintFunc());
	        var_table = new Hashtable<>();
	    }

	    public LexicalAnalyzer getInput() {
	        return input;
	    }
	    public Function getFunction(String fname) {
	        return (Function) library.get(fname);
	    }

	    public VariableNode getVariable(String vname) {
	        VariableNode v;
	        v = (VariableNode) var_table.get(vname);
	        if (v == null) {
	            v = new VariableNode(vname);
	            var_table.put(vname, v);
	        }
	        return v;
	    }
}
