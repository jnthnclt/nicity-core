/*
 * AValuesComparator.java.java
 *
 * Created on 03-12-2010 11:09:28 PM
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
package com.colt.nicity.core.comparator;

/**
 * 
 * @author Administrator
 * @param <T>
 */
abstract public class AValuesComparator<T> implements IComparator<T> {

    /**
     *
     * @param _value
     * @return
     */
    abstract public Object[] value(T _value);
    /**
     *
     */
    public static final boolean cAscending = true;
    /**
     *
     */
    public static final boolean cDescending = false;
    boolean direction = true;

    /**
     *
     * @param _direction
     */
    public AValuesComparator(boolean _direction) {
        direction = _direction;
    }

    @Override
    public int compare(T o1, T o2) {
        int c = -1;
        Object[] a = value(o1);
        Object[] b = value(o2);
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return direction ? -1 : 1;
        }
        if (b == null) {
            return direction ? 1 : -1;
        }

        int min = Math.min(a.length, b.length);
        for (int i = 0; i < min; i++) {
            c = compare(i, a, b);
            if (c == 0) {
                continue;
            }
            else {
                return c;
            }
        }
        return -1;
    }

    private int compare(int _index, Object[] _as, Object[] _bs) {
        Object a = _as[_index];
        Object b = _bs[_index];

        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return direction ? -1 : 1;
        }
        if (b == null) {
            return direction ? 1 : -1;
        }

        int c = -1;
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
        return "values comparator";
    }
}
