/*
 * AValueComparator.java.java
 *
 * Created on 03-12-2010 11:09:04 PM
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
abstract public class AValueComparator<T> implements IComparator<T> {

    abstract public Object value(T value);
    public static final boolean cAscending = true;
    public static final boolean cDescending = false;
    boolean _direction = true;

    /**
     *
     * @param _direction
     */
    public AValueComparator(boolean direction) {
        _direction = direction;
    }

    public int compare(T o1, T o2) {
        int c = -1;
        Object a = value(o1);
        Object b = value(o2);
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return _direction ? -1 : 1;
        }
        if (b == null) {
            return _direction ? 1 : -1;
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
            return _direction ? c : c * -1;
        }
        if (a.getClass() == b.getClass()) {
            c = ((Comparable) a).compareTo(b);
            return _direction ? c : c * -1;
        }
        return 0;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return "value comparator";
    }
}
