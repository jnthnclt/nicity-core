package colt.nicity.core.util;

/**
 *
 * @author Administrator
 */
public class Dent {
    
    /**
     *
     */
    public String indent = "";
    /**
     *
     */
    public int depth = 0;
    /**
     *
     */
    public char[] fill = new char[]{'\t'};
    private char[] dent = new char[0];

    /**
     *
     */
    public Dent() {
    }

    /**
     *
     * @param _fill
     */
    public Dent(char _fill) {
        this(new char[]{_fill}, 0);
    }

    /**
     *
     * @param _fill
     * @param _depth
     */
    public Dent(char _fill, int _depth) {
        this(new char[]{_fill}, _depth);
    }

    /**
     *
     * @param _fill
     */
    public Dent(char[] _fill) {
        this(_fill, 0);
    }

    /**
     *
     * @param _fill
     * @param _depth
     */
    public Dent(char[] _fill, int _depth) {
        fill = _fill;
        indent(_depth);
    }

    /**
     *
     */
    public void inc() {
        indent(depth + 1);
    }

    /**
     *
     */
    public void dec() {
        indent(depth - 1);
    }

    /**
     *
     * @param _depth
     */
    public void indent(int _depth) {
        if (_depth < 0) {
            _depth = 0;
        }
        depth = _depth;

        int l = fill.length;
        dent = new char[depth * l];

        for (int i = 0; i < depth; i++) {
            System.arraycopy(fill, 0, dent, i * l, l);
        }

        indent = new String(dent);
    }

    @Override
    public String toString() {
        return indent;
    }

    /**
     *
     * @return
     */
    public byte[] toBytes() {
        return indent.getBytes();
    }
}
