/*
 * ValueComparator.java.java
 *
 * Created on 03-12-2010 11:24:38 PM
 *
 * Copyright 2010 Jonathan Colt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package colt.nicity.core.comparator;

import colt.nicity.core.lang.ICallback;

/**
 *
 * @author Administrator
 * @param <T>
 */
public class ValueComparator<T> implements IComparator<T> {

    /**
     *
     */
    public static final boolean cAscending = true;
    /**
     *
     */
    public static final boolean cDescending = false;
    boolean direction = true;
    ICallback value;

    /**
     *
     * @param _direction
     * @param _value
     */
    public ValueComparator(boolean _direction, ICallback _value) {
        value = _value;
        direction = _direction;
    }

    /**
     *
     * @param _value
     * @return
     */
    public Object value(Object _value) {
        if (value == null) {
            return _value;
        }
        return value.callback(_value);
    }

    @Override
    public int compare(Object o1, Object o2) {
        int c = -1;
        Object a = value(o1);
        Object b = value(o2);
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return direction ? -1 : 1;
        }
        if (b == null) {
            return direction ? 1 : -1;
        }

        if (a instanceof Object[] && b instanceof Object[]) {
            Object[] as = (Object[]) a;
            Object[] bs = (Object[]) b;
            for (int i = 0; i < as.length; i++) {
                c = ((Comparable) as[i]).compareTo(bs[i]);
                if (c != 0) {
                    break;
                }
            }
            return direction ? c : c * -1;
        }
        if (a.getClass() == b.getClass()) {
            c = ((Comparable) a).compareTo(b);
            return direction ? c : c * -1;
        }
        return 0;
    }

    @Override
    public String getName() {
        return "value compare";
    }
}
