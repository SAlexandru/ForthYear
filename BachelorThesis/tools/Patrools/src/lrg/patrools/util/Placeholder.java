package lrg.patrools.util;

public class Placeholder {
	
    private Object enclosed;

    public Placeholder(Object o) {
        this.enclosed = o;
    }

    public String toString() {
        return enclosed.toString();
    }

    public Object getEnclosed() {
        return enclosed;
    }

}

