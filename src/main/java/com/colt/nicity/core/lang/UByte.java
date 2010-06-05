/*
 * UByte.java.java
 *
 * Created on 12-29-2009 07:39:00 PM
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
package com.colt.nicity.core.lang;

public class UByte {
    public static final byte[] push(byte[] src, byte instance) {
        if (src == null) {
            src = new byte[0];
        }
        byte[] newSrc = new byte[src.length + 1];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }
    public static final byte[] pregrow(byte[] src, int amount) {
        if (src == null) {
            return new byte[amount];
        }
        byte[] newSrc = new byte[src.length + amount];
        System.arraycopy(src, 0, newSrc, amount, src.length);
        return newSrc;
    }
    public static final byte[] grow(byte[] src, byte[] _new) {
        if (src == null) {
            return null;
        }
        System.arraycopy(src, 0, _new, 0, src.length);
        return _new;
    }
    public static final byte[] grow(byte[] src, int amount) {
        if (src == null) {
            return new byte[amount];
        }
        byte[] newSrc = new byte[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }
    public static final byte[] copy(byte[] src, byte[] count) {
        return trim(src, count);
    }
    public static final byte[] copy(byte[] src) {
        return trim(src, new byte[src.length]);
    }
    public static final byte[] join(byte[] a, byte[] b) {
        byte[] newSrc = new byte[a.length + b.length];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        return newSrc;
    }
    public static final byte[] join(byte[] a, byte[] b, byte[] c) {
        byte[] newSrc = new byte[a.length + b.length + c.length];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        System.arraycopy(c, 0, newSrc, a.length + b.length, c.length);
        return newSrc;
    }
    public static final byte[] copy(byte[] src, int start, int count) {
        byte[] dst = new byte[count];
        System.arraycopy(src, start, dst, 0, count);
        return dst;
    }
    public static final void copy(byte[] src, int srcStart, byte[] dst, int dstStart, int count) {
        System.arraycopy(src, srcStart, dst, dstStart, count);
    }
    public static final byte[] trim(byte[] src, int count) {
        byte[] newSrc = new byte[count];
        System.arraycopy(src, 0, newSrc, 0, count);
        return newSrc;
    }
    public static final byte[] trim(byte[] src, byte[] count) {
        System.arraycopy(src, 0, count, 0, count.length);
        return count;
    }
    public static long hash(byte[] key, long _modulo) throws Exception {

        //!! don't change this function unless testing *proves* you have a better one! this one is the best so far!
        //!! changing this function invalidates all old databases !!
        long hash = 0;
        for (int i = 0; i < key.length; i++) {
            if (key[i] != 0) {
                hash += key[i] * 251;
            } else {
                hash++;
            }
        }
        hash *= hash << 1;
        if (hash < 0) {
            hash = -hash;
        }
        return hash % (_modulo - 1); //!! the -1 increases likelihood of a prime, avoids 2 as a factor, and works better than not subtracting.
    }
    public static boolean keysEqual(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (!(a instanceof byte[]) || !(b instanceof byte[])) {
            return false;
        }
        return keysEqual((byte[]) a, (byte[]) b);
    }
    public static boolean keysEqual(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        int l = a.length;
        for (int i = l - 1; i > -1; i--) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
    public static final long checkSum(byte[] _bytes) {
        if (_bytes == null) {
            return -1;
        }
        long sum = 0;
        for (int i = 0; i < _bytes.length; i++) {
            sum += _bytes[i];
        }
        return sum;
    }
    public static final int toUnsignedInt(byte _byte) {
        int v = (int) _byte;
        v += 128;
        return v;
    }
    public static final float toUnsignedFloat(byte _byte) {
        int v = (int) _byte;
        v += 128;
        return v / 256f;
    }
    public static final float toSignedFloat(byte _byte) {
        int v = (int) _byte;
        return v / 128f;
    }
    public static final double toUnsignedDouble(byte _byte) {
        int v = (int) _byte;
        v += 128;
        return v / 256d;
    }
    public static final double toSignedDouble(byte _byte) {
        int v = (int) _byte;
        return v / 128d;
    }
    public static final float[] toUnsignedFloats(byte[] _bytes) {
        if (_bytes == null) {
            return null;
        }
        float[] floats = new float[_bytes.length];
        for (int i = 0; i < _bytes.length; i++) {
            floats[i] = toUnsignedFloat(_bytes[i]);
        }
        return floats;
    }
    public static final float[] toSignedFloats(byte[] _bytes) {
        if (_bytes == null) {
            return null;
        }
        float[] floats = new float[_bytes.length];
        for (int i = 0; i < _bytes.length; i++) {
            floats[i] = toSignedFloat(_bytes[i]);
        }
        return floats;
    }
    public static final double[] toUnsignedDoubles(byte[] _bytes) {
        if (_bytes == null) {
            return null;
        }
        double[] doubles = new double[_bytes.length];
        for (int i = 0; i < _bytes.length; i++) {
            doubles[i] = toUnsignedDouble(_bytes[i]);
        }
        return doubles;
    }
    public static final double[] toSignedDoubles(byte[] _bytes) {
        if (_bytes == null) {
            return null;
        }
        double[] doubles = new double[_bytes.length];
        for (int i = 0; i < _bytes.length; i++) {
            doubles[i] = toSignedDouble(_bytes[i]);
        }
        return doubles;
    }
    public static final byte range(byte _v, byte _min, byte _max) {
        if (_v < _min) {
            return _min;
        } else if (_v > _max) {
            return _max;
        }
        return _v;
    }
    public static boolean equals(byte[] a, byte[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.length != b.length) {
            return false;
        }
        int l = a.length;
        for (int i = 0; i < l; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
    public static boolean equals(byte[] a, int _astart, byte[] b, int _bstart, int _length) {
        if (a == b) {
            return true;
        }

        if ((a == null) || (b == null)) {
            return false;
        }

        for (int i = 0; i < _length; i++) {
            if (a[_astart + i] != b[_bstart + i]) {
                return false;
            }
        }

        return true;
    }
    public static byte[] scale(byte[] _array, byte _min, byte _max) {// rename to scale
        int l = _array.length;
        MinMaxDouble minMax = new MinMaxDouble();
        for (int i = 0; i < l; i++) {
            minMax.value(_array[i]);
        }

        double min = minMax.min();
        double range = minMax.range();
        double _range = _max - _min;
        byte[] array = new byte[l];
        if (range == 0) {
            return copy(_array, array);
        }
        for (int i = 0; i < l; i++) {
            double v = _min + (_range * ((Math.abs(_array[i] - min)) / range));
            array[i] = (byte) v;
        }
        return array;
    }
    // _distance >= 0.0 && <= 1.0
    public static byte[] linearInterpolation(byte[] _values, byte[] _new) {
        for (double i = 0; i < _new.length; i++) {
            _new[(int) i] = linearInterpolation(_values, i / _new.length);
        }
        return _new;
    }
    // _distance >= 0.0 && <= 1.0
    public static byte linearInterpolation(byte[] _values, double _distance) {
        if (_distance < 0) {
            _distance = 0;
        }
        if (_distance > 1) {
            _distance = 1;
        }
        int l = _values.length;
        if (l == 0) {
            return 0;
        }
        double distance = (l - 1) * _distance;
        int index = (int) distance;
        if (index >= l - 1) {
            return _values[l - 1];
        }
        distance -= index;

        byte a = _values[index];
        byte b = _values[index + 1];
        return linearInterpolation(a, b, distance);
        //return _values[index];
    }
    public static byte linearInterpolation(byte[] _values, double _distance, byte _offValue) {
        if (_distance < 0) {
            _distance = 0;
        }
        if (_distance > 1) {
            _distance = 1;
        }
        int l = _values.length;
        if (l == 0) {
            return 0;
        }
        double distance = (l - 1) * _distance;
        int index = (int) distance;
        if (index >= l - 1) {
            return _values[l - 1];
        }
        distance -= index;

        byte a = _values[index];
        byte b = _values[index + 1];
        if (a == _offValue) {
            a = b;
        }
        if (b == _offValue) {
            b = a;
        }
        return linearInterpolation(a, b, distance);
        //return _values[index];
    }
    public static byte distance(byte _x1, byte _y1, byte _x2, byte _y2) {
        double a = (_x1 - _x2) * (_x1 - _x2);
        double b = (_y1 - _y2) * (_y1 - _y2);
        return (byte) Math.sqrt(a + b);
    }
    public static byte linearInterpolation(byte _a, byte _b, double _distance) {
        double v = _a + ((_b - _a) * _distance);
        return (byte) v;
    }
    public static byte linearDelta(byte _a, byte _b, double _distance) {
        return (byte) ((_b - _a) * _distance);
    }
    public static String toString(byte[] values, String delim) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            string.append(values[i]);
            if (i < values.length - 1) {
                string.append(delim);
            }
        }
        return string.toString();
    }
    public static String toHexString(byte[] values, String delim) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            int v = (int) values[i];
            if (v < 0) {
                v += 256;
            }
            String hex = Integer.toString(v, 16).toUpperCase();
            if (hex.length() == 1) {
                string.append("0");
            }
            string.append(hex);
            if (i < values.length - 1) {
                string.append(delim);
            }
        }
        return string.toString();
    }
    
}
