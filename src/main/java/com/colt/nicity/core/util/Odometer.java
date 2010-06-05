package com.colt.nicity.core.util;

import com.colt.nicity.core.lang.UArray;
import com.colt.nicity.core.value.Value;

public class Odometer {

    public int roleOver = 0;
    private int index = 0;
    private Object[] values;
    private Odometer next;
    private Value done;

    public Odometer(Object[] _values) {
        values = _values;
    }

    public void setIndex(int _index) {
        if (_index < 0) {
            _index = 0;
        }
        if (_index >= values.length) {
            _index = values.length - 1;
        }
        index = _index;
    }

    public Odometer setNext(Odometer _odometer) {
        next = _odometer;
        return next;
    }

    public Odometer getNext() {
        return next;
    }

    public void setDone(Value _done) {
        done = _done;
    }

    public boolean inc() {
        index++;
        if (index >= values.length) {
            roleOver++;
            index = 0;
            if (next == null) {
                if (done != null) {
                    done.setValue(new Long(System.currentTimeMillis()));
                }
                return false;
            }
            return next.inc();
        }
        return true;
    }

    public Object[] toArray() {
        if (next == null) {
            return new Object[]{values[index]};
        }
        else {
            return UArray.push(next.toArray(), values[index]);
        }
    }
}
