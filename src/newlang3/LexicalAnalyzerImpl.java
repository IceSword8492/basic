package newlang3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
	PushbackReader reader;

	static HashMap<String, LexicalUnit> reservedWords = new HashMap<String, LexicalUnit>();
	static HashMap<String, LexicalUnit> punctuators = new HashMap<String, LexicalUnit>();

	static {
		reservedWords.put("if", new LexicalUnit(LexicalType.IF));
		reservedWords.put("then", new LexicalUnit(LexicalType.THEN));
		reservedWords.put("else", new LexicalUnit(LexicalType.ELSE));
		reservedWords.put("elseif", new LexicalUnit(LexicalType.ELSEIF));
		reservedWords.put("endif", new LexicalUnit(LexicalType.ENDIF));
		reservedWords.put("for", new LexicalUnit(LexicalType.FOR));
		reservedWords.put("forall", new LexicalUnit(LexicalType.FORALL));
		reservedWords.put("next", new LexicalUnit(LexicalType.NEXT));
		reservedWords.put("sub", new LexicalUnit(LexicalType.SUB));
		reservedWords.put("dim", new LexicalUnit(LexicalType.DIM));
		reservedWords.put("as", new LexicalUnit(LexicalType.AS));
		reservedWords.put("end", new LexicalUnit(LexicalType.END));
		reservedWords.put("while", new LexicalUnit(LexicalType.WHILE));
		reservedWords.put("do", new LexicalUnit(LexicalType.DO));
		reservedWords.put("until", new LexicalUnit(LexicalType.UNTIL));
		reservedWords.put("loop", new LexicalUnit(LexicalType.LOOP));
		reservedWords.put("to", new LexicalUnit(LexicalType.TO));
		reservedWords.put("wend", new LexicalUnit(LexicalType.WEND));

		punctuators.put("=", new LexicalUnit(LexicalType.EQ));
		punctuators.put("<", new LexicalUnit(LexicalType.LT));
		punctuators.put(">", new LexicalUnit(LexicalType.GT));
		punctuators.put("<=", new LexicalUnit(LexicalType.LE));
		punctuators.put("=<", new LexicalUnit(LexicalType.LE));
		punctuators.put(">=", new LexicalUnit(LexicalType.GE));
		punctuators.put("=>", new LexicalUnit(LexicalType.GE));
		punctuators.put("<>", new LexicalUnit(LexicalType.NE));
		punctuators.put("\n", new LexicalUnit(LexicalType.NL));
		punctuators.put(".", new LexicalUnit(LexicalType.DOT));
		punctuators.put("+", new LexicalUnit(LexicalType.ADD));
		punctuators.put("-", new LexicalUnit(LexicalType.SUB));
		punctuators.put("*", new LexicalUnit(LexicalType.MUL));
		punctuators.put("/", new LexicalUnit(LexicalType.DIV));
		punctuators.put("(", new LexicalUnit(LexicalType.LP));
		punctuators.put(")", new LexicalUnit(LexicalType.RP));
		punctuators.put(",", new LexicalUnit(LexicalType.COMMA));
	}

	public LexicalAnalyzerImpl (String fileName) throws FileNotFoundException {
		reader = new PushbackReader(new BufferedReader(new FileReader(fileName)));
	}

	@Override
	public LexicalUnit get() throws IOException {
		char ch = (char)reader.read();

		char EOF = (char)-1;

		while (ch == ' ' || ch == '\t' || ch == '\r') {
			ch = (char)reader.read();
			if (ch == EOF) {
				return new LexicalUnit(LexicalType.EOF);
			}
		}

		if (ch == EOF) {
			return new LexicalUnit(LexicalType.EOF);
		}

		String input = String.valueOf(ch);

		reader.unread(ch);

		if (input.matches("[0-9]")) {
			return getNumber();
		}
		if (input.matches("[a-zA-Z_]")) {
			return getString();
		}
		if (input.matches("\"")) {
			return getLiteral();
		}
		return getPunctuator();
	}

	public boolean findUnit () {
		return false;
	}

	@Override
	public boolean expect(LexicalType type) throws Exception {

		return false;
	}

	@Override
	public void unget(LexicalUnit token) throws Exception {

	}

	private LexicalUnit getNumber () throws IOException {
		String target = "";

		char ch;

		boolean isDouble = false;

		while (true) {
			ch = (char)reader.read();
			if (ch < 0) break;
			if ((target + ch).matches("[0-9]+(\\.[0-9]*)?")) {
				target += ch;
				if (target.matches("[0-9]+\\.[0-9]+")) {
					isDouble = true;
				}
				continue;
			}
			reader.unread(ch);
			break;
		}

		return isDouble
		? new LexicalUnit(
			LexicalType.DOUBLEVAL,
			new ValueImpl(target, ValueType.DOUBLE)
		)
		: new LexicalUnit(
			LexicalType.INTVAL,
			new ValueImpl(target, ValueType.INTEGER)
		);
	}

	private LexicalUnit getString () throws IOException {
		String target = "";

		char ch;

		while (true) {
			ch = (char)reader.read();
			if (ch < 0) break;

			if ((target + ch).matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
				target += ch;
				continue;
			}
			reader.unread(ch);
			break;
		}

		String key = target.toLowerCase();

		LexicalUnit lu = reservedWords.get(key);

		if (lu != null) {
			return lu;
		}

		return new LexicalUnit(
			LexicalType.NAME,
			new ValueImpl(target, ValueType.STRING)
		);
	}

	private LexicalUnit getLiteral () throws IOException {
		String target = "";

		char ch;

		reader.read();

		while (true) {
			ch = (char)reader.read();
			if (ch < 0) break;
			if (ch != '"') {
				target += ch;
				continue;
			}
			break;
		}

		return new LexicalUnit(
			LexicalType.LITERAL,
			new ValueImpl(target)
		);
	}

	private LexicalUnit getPunctuator () throws IOException {
		String target = "";

		char ch;
		String str;
		LexicalUnit prev = null;

		while (true) {
			ch = (char)reader.read();
			str = String.valueOf(ch);
			if (ch < 0) break;
			if (str.matches("=|<|>|\\+|-|\\*|/|\\(|\\)|,|\\n")) {
				target += str;
				if (punctuators.get(target) != null || prev == null) {
					prev = punctuators.get(target);
					continue;
				}
			}
			reader.unread(ch);
			break;
		}
		return prev;
	}
}
