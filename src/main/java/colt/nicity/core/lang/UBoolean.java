/*
 * UBoolean.java.java
 *
 * Created on 12-27-2009 03:12:00 PM
 *
 * Copyright 2009 Jonathan Colt
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
package colt.nicity.core.lang;

/**
 *
 * @author Administrator
 */
public class UBoolean {
    /**
     *
     * @param src
     * @param _new
     * @return
     */
    public static boolean[] grow(boolean[] src, boolean[] _new) {
        if (src == null) {
            return null;
        }
        System.arraycopy(src, 0, _new, 0, src.length);
        return _new;
    }
    /**
     *
     * @param _src
     * @param _new
     * @param _log
     * @return
     */
    public static boolean[] log(boolean[] _src, boolean[] _new, boolean _log) {
        if (_src == null) {
            return null;
        }
        System.arraycopy(_src, 0, _new, 1, _src.length - 1);
        _new[0] = _log;
        return _new;
    }
    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static boolean[] grow(boolean[] src, int amount) {
        if (src == null) {
            return new boolean[amount];
        }
        boolean[] newSrc = new boolean[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }
    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static boolean[] copy(boolean[] src, boolean[] count) {
        return trim(src, count);
    }
    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static boolean[] trim(boolean[] src, boolean[] count) {
        System.arraycopy(src, 0, count, 0, count.length);
        return count;
    }
    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static boolean[] pregrow(boolean[] src, int amount) {
        if (src == null) {
            return new boolean[amount];
        }
        boolean[] newSrc = new boolean[src.length + amount];
        System.arraycopy(src, 0, newSrc, amount, src.length);
        return newSrc;
    }

    // _distance >= 0.0 && <= 1.0
    /**
     *
     * @param _values
     * @param _new
     * @return
     */
    public static boolean[] linearInterpolation(boolean[] _values, boolean[] _new) {
        for (double i = 0; i < _new.length; i++) {
            _new[(int) i] = linearInterpolation(_values, i / _new.length);
        }
        return _new;
    }
    // _distance >= 0.0 && <= 1.0
    /**
     *
     * @param _values
     * @param _distance
     * @return
     */
    public static boolean linearInterpolation(boolean[] _values, double _distance) {
        if (_distance < 0) {
            _distance = 0;
        }
        if (_distance > 1) {
            _distance = 1;
        }
        int l = _values.length;
        if (l == 0) {
            return false;
        }
        double distance = (l - 1) * _distance;
        int index = (int) distance;
        if (index >= l - 1) {
            return _values[l - 1];
        }
        distance -= index;

        boolean a = _values[index];
        boolean b = _values[index + 1];
        return linearInterpolation(a, b, distance);
    }
    /**
     *
     * @param _a
     * @param _b
     * @param _distance
     * @return
     */
    public static boolean linearInterpolation(boolean _a, boolean _b, double _distance) {
        return (_distance < 0.5) ? _a : _b;
    }
}
