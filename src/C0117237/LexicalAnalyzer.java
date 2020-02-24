package newlang4;

public interface LexicalAnalyzer {
    public LexicalUnit get() throws Exception;
    public LexicalUnit get(int n) throws Exception;
    public boolean expect(LexicalType type) throws Exception;
    public boolean expect(LexicalType type, int n) throws Exception;
    public void unget(LexicalUnit token) throws Exception;
    public LexicalUnit peek() throws Exception;
    public LexicalUnit peek(int n) throws Exception;
}
