package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class ConstNode extends Node {
	public static final Set<LexicalType> FIRST_SET = EnumSet.of(
		LexicalType.INTVAL,
		LexicalType.DOUBLEVAL,
		LexicalType.LITERAL
	);

	private ConstNode (Environment env) {
		super(env);
	}

	public static boolean isFirst (LexicalUnit unit) {
		return FIRST_SET.contains(unit.getType());
	}
}
