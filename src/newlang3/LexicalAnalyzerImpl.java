package newlang3;

import java.io.PushbackReader;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
	private PushbackReader pr;
	private LexicalUnit unit;
	private String buf;
	public LexicalAnalyzerImpl (PushbackReader pr) {
		this.pr = pr;
	}

	@Override
	public LexicalUnit get() {
		try {
			int ch;
			do {
				ch = pr.read();
			} while ();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public boolean findUnit () {
		
	}

	@Override
	public boolean expect(LexicalType type) throws Exception {

		return false;
	}

	@Override
	public void unget(LexicalUnit token) throws Exception {

	}

}
