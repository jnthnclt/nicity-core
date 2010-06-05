package com.colt.nicity.core.util;

/**
 *
 * @author Administrator
 */
public class Trail {

    private Object[] trail;
    private int first;
    private int last;
    private int current;
    private int max;
    private int count;

    /**
     *
     */
    public Trail() {
        this(8);
    }

    /**
     *
     * @param _max
     */
    public Trail(int _max) {
        if (_max < 1) {
            max = 8;
        }
        else {
            max = _max;
        }
        trail = new Object[max];
        first = 0;
        last = 0;
        current = 0;
        count = 0;
    }

    /**
     *
     * @return
     */
    public Object[] trail() {
        return trail;
    }

    /**
     *
     * @param _object
     */
    public void mark(Object _object) {
        if (current() == _object) {
            return;
        }
        if (count == 0) {
            trail[current] = _object;
            count++;
            return;
        }

        if (count >= max) { // could grow here, but we choose to recycle instead
            last = (max + (++last)) % max;
            first = (max + (++first)) % max;
        }
        else {
            if (current == last) {
                last = (max + (++last)) % max;
            }
            count++;
        }

        current = (max + (++current)) % max;
        trail[current] = _object;
    }

    /**
     *
     * @return
     */
    public Object current() {
        return trail[current];
    }

    /**
     *
     * @return
     */
    public Object backward() {
        if (count == 0) {
            return null;
        }
        if (count > 1) {
            current = (max + (--current)) % max;
            count--;
        }
        return trail[current];
    }

    /**
     *
     * @return
     */
    public Object forward() {
        if (count == 0) {
            return null;
        }
        if (count < max) {
            current = (max + (++current)) % max;
            count++;
        }
        return trail[current];
    }
}
