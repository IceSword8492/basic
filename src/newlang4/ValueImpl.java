package newlang4;

public class ValueImpl extends Value {

	private ValueType type;
	private String valString;
	private int valInteger;
	private double valDouble;
	private boolean valBoolean;

	public ValueImpl (String s) {
		super(s);
		valString = s;
		type = ValueType.STRING;
	}
    public ValueImpl (int i) {
    	super(i);
    	valInteger = i;
    	type = ValueType.INTEGER;
    }
    public ValueImpl (double d) {
    	super(d);
    	valDouble = d;
    	type = ValueType.DOUBLE;
    }
    public ValueImpl (boolean b) {
    	super(b);
    	valBoolean = b;
    	type = ValueType.BOOL;
    }
    public ValueImpl (String s, ValueType t) {
    	super(s, t);
    	switch(t) {
		case STRING:
			valString = s;
			break;
		case INTEGER:
			valInteger = Integer.parseInt(s);
			break;
		case DOUBLE:
			valDouble = Double.parseDouble(s);
			break;
		case BOOL:
			valBoolean = Boolean.parseBoolean(s);
			break;
		case VOID:
			break;
		}
		type = t;
    }

	@Override
	public String get_sValue() {
		return getSValue();
	}

	@Override
	public String getSValue() {
		switch(type) {
		case STRING:
			return valString;
		case INTEGER:
			return String.valueOf(valInteger);
		case DOUBLE:
			return String.valueOf(valDouble);
		case BOOL:
			return String.valueOf(valBoolean);
		default:
			return null;
		}
	}

	@Override
	public int getIValue() {
		switch(type) {
		case STRING:
			return Integer.parseInt(valString);
		case INTEGER:
			return valInteger;
		case DOUBLE:
			return (int)valDouble;
		case BOOL:
			return valBoolean ? 1 : 0;
		default:
			return 0;
		}
	}

	@Override
	public double getDValue() {
		switch(type) {
		case STRING:
			return Double.parseDouble(valString);
		case INTEGER:
			return (double)valInteger;
		case DOUBLE:
			return valDouble;
		case BOOL:
			return valBoolean ? 1.0 : 0.0;
		default:
			return 0.0;
		}
	}

	@Override
	public boolean getBValue() {
		switch(type) {
		case STRING:
			return Boolean.parseBoolean(valString);
		case INTEGER:
			return (valInteger != 0) ? true : false;
		case DOUBLE:
			return (valDouble != 0) ? true : false;
		case BOOL:
			return valBoolean;
		default:
			return false;
		}
	}

	@Override
	public ValueType getType() {
		return type;
	}

}
