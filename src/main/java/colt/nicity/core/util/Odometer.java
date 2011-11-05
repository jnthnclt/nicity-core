package colt.nicity.core.util;

import colt.nicity.core.lang.UArray;
import colt.nicity.core.value.Value;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class Odometer {

    /**
     *
     */
    public int roleOver = 0;
    private int index = 0;
    private Object[] values;
    private Odometer next;
    private Value done;

    /**
     *
     * @param _values
     */
    public Odometer(Object[] _values) {
        values = Arrays.copyOf(_values, _values.length);
    }

    /**
     *
     * @param _index
     */
    public void setIndex(int _index) {
        if (_index < 0) {
            _index = 0;
        }
        if (_index >= values.length) {
            _index = values.length - 1;
        }
        index = _index;
    }

    /**
     *
     * @param _odometer
     * @return
     */
    public Odometer setNext(Odometer _odometer) {
        next = _odometer;
        return next;
    }

    /**
     *
     * @return
     */
    public Odometer getNext() {
        return next;
    }

    /**
     *
     * @param _done
     */
    public void setDone(Value _done) {
        done = _done;
    }

    /**
     *
     * @return
     */
    public boolean inc() {
        index++;
        if (index >= values.length) {
            roleOver++;
            index = 0;
            if (next == null) {
                if (done != null) {
                    done.setValue(Long.valueOf(System.currentTimeMillis()));
                }
                return false;
            }
            return next.inc();
        }
        return true;
    }

    /**
     *
     * @return
     */
    public Object[] toArray() {
        if (next == null) {
            return new Object[]{values[index]};
        }
        else {
            return UArray.push(next.toArray(), values[index]);
        }
    }
}
