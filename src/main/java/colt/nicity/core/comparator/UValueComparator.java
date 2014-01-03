/*
 * UValueComparator.java.java
 *
 * Created on 03-12-2010 11:11:54 PM
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

import colt.nicity.core.collection.IHaveCount;

/**
 * 
 * @author Administrator
 */
public class UValueComparator {

    /**
     *
     * @param <T>
     * @param _direction
     * @return
     */
    public static <T> AValueComparator<T> value(boolean _direction) {
        return new AValueComparator<T>(_direction) {

            @Override
            public Object value(T _value) {
                if (_value == null) {
                    return null;
                }
                return _value;
            }
        };
    }

    /**
     *
     * @param <T>
     * @param _direction
     * @return
     */
    public static <T> AValueComparator<T> toString(boolean _direction) {
        return new AValueComparator<T>(_direction) {

            @Override
            public Object value(T _value) {
                if (_value == null) {
                    return null;
                }
                return _value.toString();
            }
        };
    }

    /**
     *
     * @param <T>
     * @param _direction
     * @return
     */
    public static <T> AValueComparator<T> lowercase(boolean _direction) {
        return new AValueComparator<T>(_direction) {

            @Override
            public Object value(T _value) {
                if (_value == null) {
                    return null;
                }
                return _value.toString().toLowerCase();
            }
        };
    }

    /**
     *
     * @param <T>
     * @param _direction
     * @return
     */
    public static <T> AValueComparator<T> uppercase(boolean _direction) {
        return new AValueComparator<T>(_direction) {

            @Override
            public Object value(T _value) {
                if (_value == null) {
                    return null;
                }
                return _value.toString().toUpperCase();
            }
        };
    }

    /**
     *
     * @param <T>
     * @param _direction
     * @return
     */
    public static <T> AValueComparator<T> identity(boolean _direction) {
        return new AValueComparator<T>(_direction) {

            @Override
            public Object value(T _value) {
                if (_value == null) {
                    return null;
                }
                return System.identityHashCode(_value);
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param _direction
     * @return
     */
    public static <T> AValueComparator<T> haveCount(boolean _direction) {
        return new AValueComparator<T>(_direction) {

            @Override
            public Object value(T _value) {
                if (_value instanceof IHaveCount) {
                    return ((IHaveCount) _value).getCount();
                }
                return null;
            }
        };
    }
}
