package newlang3;

public class ValueImpl extends Value {

	public ValueImpl (String s) {
		super(s);
	}
    public ValueImpl (int i) {
    	super(i);
    }
    public ValueImpl (double d) {
    	super(d);
    }
    public ValueImpl (boolean b) {
    	super(b);
    }
    public ValueImpl (String s, ValueType t) {
    	super(s, t);
    }

	@Override
	public String get_sValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getSValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getIValue() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public double getDValue() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public boolean getBValue() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public ValueType getType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
