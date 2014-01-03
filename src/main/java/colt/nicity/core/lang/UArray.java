/*
 * UArray.java.java
 *
 * Created on 12-28-2009 08:32:00 PM
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

import java.lang.reflect.Array;

/**
 *
 * @author Administrator
 */
public final class UArray {

    // Object array helpers
    /**
     *
     * @param src
     * @param instance
     * @return
     */
    public static final byte[] push(byte[] src, byte instance) {
        if (src == null) {
            src = new byte[0];
        }
        byte[] newSrc = new byte[src.length + 1];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param src
     * @param instance
     * @return
     */
    public static final char[] push(char[] src, char instance) {
        if (src == null) {
            src = new char[0];
        }
        char[] newSrc = new char[src.length + 1];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param src
     * @param instance
     * @return
     */
    public static final int[] push(int[] src, int instance) {
        if (src == null) {
            src = new int[0];
        }
        int[] newSrc = new int[src.length + 1];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param src
     * @param instance
     * @return
     */
    public static final float[] push(float[] src, int instance) {
        if (src == null) {
            src = new float[0];
        }
        float[] newSrc = new float[src.length + 1];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param src
     * @param instance
     * @return
     */
    public static final long[] push(long[] src, long instance) {
        if (src == null) {
            src = new long[0];
        }
        long[] newSrc = new long[src.length + 1];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param src
     * @param instance
     * @return
     */
    public static final double[] push(double[] src, double instance) {
        if (src == null) {
            src = new double[0];
        }
        double[] newSrc = new double[src.length + 1];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param src
     * @param instance
     * @return
     */
    public static final Object[] push(Object[] src, Object instance) {
        if (src == null) {
            src = new Object[0];
        }
        Object[] newSrc = new Object[src.length + 1];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param src
     * @param instance
     * @return
     */
    public static final String[] push(String[] src, String instance) {
        if (src == null) {
            src = new String[0];
        }
        String[] newSrc = new String[src.length + 1];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param instance
     * @param arrayClass
     * @return
     */
    public static final <E> E[] push(E[] src, E instance, Class<? extends E> arrayClass) {
        if (src == null) {
            src = (E[]) Array.newInstance(arrayClass, 0);
        }
        E[] newSrc = (E[]) Array.newInstance(arrayClass, src.length + 1);
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param instance
     * @param newSrc
     * @return
     */
    public static final <E> E[] push(E[] src, E instance, E[] newSrc) {
        if (src == null)
            return newSrc;
        System.arraycopy(src, 0, newSrc, 0, src.length);
        newSrc[src.length] = instance;
        return newSrc;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param array
     * @param newSrc
     * @return
     */
    public static final <E> E[] push(E[] src, E[] array, E[] newSrc) {
        return join(src, array, newSrc);
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final byte[] pregrow(byte[] src, int amount) {
        if (src == null) {
            return new byte[amount];
        }
        byte[] newSrc = new byte[src.length + amount];
        System.arraycopy(src, 0, newSrc, amount, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final char[] pregrow(char[] src, int amount) {
        if (src == null) {
            return new char[amount];
        }
        char[] newSrc = new char[src.length + amount];
        System.arraycopy(src, 0, newSrc, amount, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final int[] pregrow(int[] src, int amount) {
        if (src == null) {
            return new int[amount];
        }
        int[] newSrc = new int[src.length + amount];
        System.arraycopy(src, 0, newSrc, amount, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final long[] pregrow(long[] src, int amount) {
        if (src == null) {
            return new long[amount];
        }
        long[] newSrc = new long[src.length + amount];
        System.arraycopy(src, 0, newSrc, amount, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final float[] pregrow(float[] src, int amount) {
        if (src == null) {
            return new float[amount];
        }
        float[] newSrc = new float[src.length + amount];
        System.arraycopy(src, 0, newSrc, amount, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final double[] pregrow(double[] src, int amount) {
        if (src == null) {
            return new double[amount];
        }
        double[] newSrc = new double[src.length + amount];
        System.arraycopy(src, 0, newSrc, amount, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final String[] pregrow(String[] src, int amount) {
        if (src == null) {
            return new String[amount];
        }
        String[] newSrc = new String[src.length + amount];
        System.arraycopy(src, 0, newSrc, amount, src.length);
        return newSrc;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param size
     * @param arrayClass
     * @return
     */
    public static final <E> E[] grow(E[] src, int size, Class<? extends E> arrayClass) {
        if (src == null) {
            return (E[]) Array.newInstance(arrayClass, size);
        }
        E[] newSrc = (E[]) Array.newInstance(arrayClass, src.length + size);
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param _new
     * @return
     */
    public static final <E> E[] grow(E[] src, E[] _new) {
        if (src == null) {
            return null;
        }
        System.arraycopy(src, 0, _new, 0, src.length);
        return _new;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final Object[] grow(Object[] src, int amount) {
        if (src == null) {
            return new Object[amount];
        }
        Object[] newSrc = new Object[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final byte[] grow(byte[] src, int amount) {
        if (src == null) {
            return new byte[amount];
        }
        byte[] newSrc = new byte[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final char[] grow(char[] src, int amount) {
        if (src == null) {
            return new char[amount];
        }
        char[] newSrc = new char[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final int[] grow(int[] src, int amount) {
        if (src == null) {
            return new int[amount];
        }
        int[] newSrc = new int[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final long[] grow(long[] src, int amount) {
        if (src == null) {
            return new long[amount];
        }
        long[] newSrc = new long[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final float[] grow(float[] src, int amount) {
        if (src == null) {
            return new float[amount];
        }
        float[] newSrc = new float[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final double[] grow(double[] src, int amount) {
        if (src == null) {
            return new double[amount];
        }
        double[] newSrc = new double[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param amount
     * @return
     */
    public static final String[] grow(String[] src, int amount) {
        if (src == null) {
            return new String[amount];
        }
        String[] newSrc = new String[src.length + amount];
        System.arraycopy(src, 0, newSrc, 0, src.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param _new
     * @return
     */
    public static final long[] grow(long[] src, long[] _new) {
        if (src == null) {
            return null;
        }
        System.arraycopy(src, 0, _new, 0, src.length);
        return _new;
    }

    /**
     *
     * @param src
     * @param _new
     * @return
     */
    public static final double[] grow(double[] src, double[] _new) {
        if (src == null) {
            return null;
        }
        System.arraycopy(src, 0, _new, 0, src.length);
        return _new;
    }

    /**
     *
     * @param src
     * @param srcStart
     * @param dst
     * @param dstStart
     * @param count
     */
    public static final void copy(byte[] src, int srcStart, byte[] dst, int dstStart, int count) {
        System.arraycopy(src, srcStart, dst, dstStart, count);
    }

    /**
     *
     * @param src
     * @return
     */
    public static final double[] copy(double[] src) {
        return grow(src, 0);
    }

    /**
     *
     * @param src
     * @param _start
     * @param _length
     * @return
     */
    public static final byte[] copy(byte[] src, int _start, int _length) {
        byte[] copy = new byte[_length];
        System.arraycopy(src, _start, copy, 0, _length);
        return copy;
    }
    
    /**
     *
     * @param src
     * @param _start
     * @param _length
     * @return
     */
    public static final double[] copy(double[] src, int _start, int _length) {
        double[] copy = new double[_length];
        System.arraycopy(src, _start, copy, 0, _length);
        return copy;
    }

    /**
     *
     * @param src
     * @param _start
     * @param _length
     * @return
     */
    public static final char[] copy(char[] src, int _start, int _length) {
        char[] copy = new char[_length];
        System.arraycopy(src, _start, copy, 0, _length);
        return copy;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final float[] copy(float[] src, float[] count) {
        int l = count.length;
        System.arraycopy(src, 0, count, 0, l);
        return count;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final byte[] copy(byte[] src, byte[] count) {
        return trim(src, count);
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final char[] copy(char[] src, char[] count) {
        return trim(src, count);
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final long[] copy(long[] src, long[] count) {
        return trim(src, count);
    }

    /**
     *
     * @param src
     * @return
     */
    public static final boolean[] copy(boolean[] src) {
        return trim(src, new boolean[src.length]);
    }

    /**
     *
     * @param src
     * @return
     */
    public static final byte[] copy(byte[] src) {
        return trim(src, new byte[src.length]);
    }

    /**
     *
     * @param src
     * @return
     */
    public static final char[] copy(char[] src) {
        return trim(src, new char[src.length]);
    }

    /**
     *
     * @param src
     * @return
     */
    public static final int[] copy(int[] src) {
        return trim(src, new int[src.length]);
    }

    /**
     *
     * @param src
     * @return
     */
    public static final float[] copy(float[] src) {
        return trim(src, new float[src.length]);
    }

  
    /**
     *
     * @param src
     * @return
     */
    public static final long[] copy(long[] src) {
        return trim(src, new long[src.length]);
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final double[] copy(double[] src, double[] count) {
        return trim(src, count);
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final String[] copy(String[] src, String[] count) {
        return trim(src, count);
    }

   

    /**
     *
     * @param src
     * @param dst
     * @return
     */
    public static final int[] copy(int[] src, int[] dst) {
        if (dst == null) {
            dst = new int[src.length];
        }
        return trim(src, dst);
    }

    /**
     *
     * @param _src
     * @param _new
     * @param _unshift
     * @return
     */
    public static final double[] unshift(double[] _src, double[] _new, double _unshift) {
        if (_src == null) {
            return null;
        }
        System.arraycopy(_src, 0, _new, 1, _src.length);
        _new[0] = _unshift;
        return _new;
    }

    /**
     *
     * @param _src
     * @return
     */
    public static final long[] shift(long[] _src) {
        if (_src == null) {
            return null;
        }
        long[] _new = new long[_src.length - 1];
        System.arraycopy(_src, 1, _new, 0, _src.length - 1);
        return _new;
    }

    /**
     *
     * @param _src
     * @return
     */
    public static final Object[] shift(Object[] _src) {
        if (_src == null) {
            return null;
        }
        Object[] _new = new Object[_src.length - 1];
        System.arraycopy(_src, 1, _new, 0, _src.length - 1);
        return _new;
    }

    /**
     *
     * @param <E>
     * @param _src
     * @param arrayClass
     * @return
     */
    public static final <E> E[] shift(E[] _src, Class<? extends E> arrayClass) {
        if (_src == null) {
            return null;
        }
        if (_src.length == 1) {
            return (E[]) Array.newInstance(arrayClass, 0);
        }
        E[] _new = (E[]) Array.newInstance(arrayClass, _src.length - 1);
        System.arraycopy(_src, 1, _new, 0, _src.length - 1);
        return _new;
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _skip
     * @return
     */
    public static final Object[] skip(double[] _a, double[] _b, double _skip) {
        double[] _newA = new double[_a.length];
        double[] _newB = new double[_a.length];
        int c = 0;
        for (int i = 0; i < _a.length; i++) {
            if (_a[i] == _skip || _b[i] == _skip) {
                continue;
            }
            _newA[c] = _a[i];
            _newB[c] = _b[i];
            c++;
        }
        if (c != _a.length) {
            _newA = trim(_newA, new double[c]);
            _newB = trim(_newB, new double[c]);
        }
        return new Object[]{_newA, _newB};
    }

    /**
     *
     * @param _src
     * @param _new
     * @param _log
     * @return
     */
    public static final double[] log(double[] _src, double[] _new, double _log) {
        if (_src == null) {
            return null;
        }
        System.arraycopy(_src, 1, _new, 0, _src.length - 1);
        _new[_new.length - 1] = _log;
        return _new;
    }

    /**
     *
     * @param _src
     * @param _new
     * @param _log
     * @return
     */
    public static final long[] log(long[] _src, long[] _new, long _log) {
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
     * @param _new
     * @return
     */
    public static final int[] clone(int[] src, int[] _new) {
        if (src == null) {
            return null;
        }
        System.arraycopy(src, 0, _new, 0, src.length);
        return _new;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final boolean[] trim(boolean[] src, boolean[] count) {
        int l = count.length;
        System.arraycopy(src, 0, count, 0, l);
        return count;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final byte[] trim(byte[] src, byte[] count) {
        int l = count.length;
        System.arraycopy(src, 0, count, 0, l);
        return count;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final char[] trim(char[] src, char[] count) {
        int l = count.length;
        System.arraycopy(src, 0, count, 0, l);
        return count;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final int[] trim(int[] src, int[] count) {
        int l = count.length;
        System.arraycopy(src, 0, count, 0, l);
        return count;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final long[] trim(long[] src, long[] count) {
        int l = count.length;
        System.arraycopy(src, 0, count, 0, l);
        return count;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final float[] trim(float[] src, float[] count) {
        int l = count.length;
        System.arraycopy(src, 0, count, 0, l);
        return count;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final double[] trim(double[] src, double[] count) {
        int l = count.length;
        System.arraycopy(src, 0, count, 0, l);
        return count;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final String[] trim(String[] src, String[] count) {
        int l = count.length;
        System.arraycopy(src, 0, count, 0, l);
        return count;
    }

    /**
     *
     * @param a
     * @return
     */
    public static final Object[] flip(Object[] a) {
        if (a == null) {
            return new Object[0];
        }
        Object[] flip = new Object[a.length];
        for (int i = 0; i < a.length; i++) {
            flip[a.length - (i + 1)] = a[i];
        }
        return flip;
    }

    /**
     *
     * @param a
     * @return
     */
    public static final double[] flip(double[] a) {
        if (a == null) {
            return new double[0];
        }
        double[] flip = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            flip[a.length - (i + 1)] = a[i];
        }
        return flip;
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static final byte[] join(byte[] a, byte[] b, byte[] c) {
        byte[] newSrc = new byte[a.length + b.length +c.length];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        System.arraycopy(c, 0, newSrc, a.length+b.length, c.length);
        return newSrc;
    }
    
    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static final byte[] join(byte[] a, byte[] b) {
        byte[] newSrc = new byte[a.length + b.length];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        return newSrc;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static final int[] join(int[] a, int[] b) {
        int[] newSrc = new int[a.length + b.length];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        return newSrc;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static final long[] join(long[] a, long[] b) {
        long[] newSrc = new long[a.length + b.length];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        return newSrc;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static final double[] join(double[] a, double[] b) {
        double[] newSrc = new double[a.length + b.length];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        return newSrc;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static final Object[] join(Object[] a, Object[] b) {
        if (a == null && b == null) {
            return new Object[0];
        }
        if (a == null) {
            Object[] newSrc = new Object[b.length];
            System.arraycopy(b, 0, newSrc, 0, b.length);
            return newSrc;
        }
        else if (b == null) {
            Object[] newSrc = new Object[a.length];
            System.arraycopy(a, 0, newSrc, 0, a.length);
            return newSrc;
        }
        else {
            Object[] newSrc = new Object[a.length + b.length];
            System.arraycopy(a, 0, newSrc, 0, a.length);
            System.arraycopy(b, 0, newSrc, a.length, b.length);
            return newSrc;
        }
    }

    /**
     *
     * @param <E>
     * @param a
     * @param b
     * @param newSrc
     * @return
     */
    public static final <E> E[] join(E[] a, E[] b, E[] newSrc) {
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        return newSrc;
    }

    /**
     *
     * @param <E>
     * @param a
     * @param b
     * @param arrayClass
     * @return
     */
    public static final <E> E[] join(E[] a, E[] b, Class<? extends E> arrayClass) {
        if (a == null || a.length == 0) {
            return b;
        }
        if (b == null || b.length == 0) {
            return a;
        }
        E[] newSrc = (E[]) Array.newInstance(arrayClass, a.length + b.length);
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        return newSrc;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static final String[] join(String[] a, String[] b) {
        String[] newSrc = new String[a.length + b.length];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final long[] trim(long[] src, int count) {
        long[] newSrc = new long[count];
        System.arraycopy(src, 0, newSrc, 0, count);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final Long[] trim(Long[] src, int count) {
        Long[] newSrc = new Long[count];
        System.arraycopy(src, 0, newSrc, 0, count);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final Object[] trim(Object[] src, int count) {
        Object[] newSrc = new Object[count];
        System.arraycopy(src, 0, newSrc, 0, count);
        return newSrc;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param dst
     * @return
     */
    public static final <E> E[] trim(E[] src, E[] dst) {
        int count = dst.length;
        System.arraycopy(src, 0, dst, 0, count);
        return dst;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param count
     * @param arrayClass
     * @return
     */
    public static final <E> E[] trim(E[] src, int count, Class<? extends E> arrayClass) {
        if (src == null) {
            return (E[]) Array.newInstance(arrayClass, count);
        }
        E[] newSrc = (E[]) Array.newInstance(arrayClass, count);
        System.arraycopy(src, 0, newSrc, 0, count);
        return newSrc;
    }
    // int array helpers

    /**
     *
     * @param src
     * @param count
     * @return
     */
    public static final int[] trim(int[] src, int count) {
        int[] newSrc = new int[count];
        System.arraycopy(src, 0, newSrc, 0, count);
        return newSrc;
    }

    /**
     *
     * @param a
     * @param b
     * @param _count
     * @return
     */
    public static final int[] push(int[] a, int[] b, int _count) {
        int[] newSrc = new int[a.length + _count];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, _count);
        return newSrc;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static final char[] join(char[] a, char[] b) {
        char[] newSrc = new char[a.length + b.length];
        System.arraycopy(a, 0, newSrc, 0, a.length);
        System.arraycopy(b, 0, newSrc, a.length, b.length);
        return newSrc;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static final Object[] push(Object[] a, Object[] b) {
        return join(a, b);
    }

    /**
     *
     * @param src
     * @return
     */
    public static final long[] removeZeros(long[] src) {
        int count = 0;
        int len = src.length;
        long[] array = new long[len];
        for (int i = 0; i < len; i++) {
            if (src[i] == 0) {
                continue;
            }
            array[count++] = src[i];
        }
        if (count < len) {
            array = trim(array, count);
        }
        return array;
    }

    /**
     *
     * @param src
     * @return
     */
    public static final double[] removeZeros(double[] src) {
        int count = 0;
        int len = src.length;
        double[] array = new double[len];
        for (int i = 0; i < len; i++) {
            if (src[i] == 0) {
                continue;
            }
            array[count++] = src[i];
        }
        if (count < len) {
            array = trim(array, new double[count]);
        }
        return array;
    }

    /**
     *
     * @param src
     * @return
     */
    public static final Object[] removeNulls(Object[] src) {
        int count = 0;
        int len = src.length;
        Object[] array = new Object[len];
        for (int i = 0; i < len; i++) {
            if (src[i] == null) {
                continue;
            }
            array[count++] = src[i];
        }
        if (count < len) {
            array = trim(array, count);
        }
        return array;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param _class
     * @return
     */
    public static final <E> E[] removeNulls(E[] src, Class<? extends E> _class) {
        int count = 0;
        int len = src.length;
        E[] array = (E[]) Array.newInstance(_class, len);
        for (int i = 0; i < len; i++) {
            if (src[i] == null) {
                continue;
            }
            array[count++] = src[i];
        }
        if (count < len) {
            array = trim(array, count, _class);
        }
        return array;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param _class
     * @return
     */
    public static final <E> E[] cast(E[] src, Class<? extends E> _class) {
        int count = 0;
        int len = src.length;
        E[] array = (E[]) Array.newInstance(_class, len);
        for (int i = 0; i < len; i++) {
            if (!_class.isInstance(src[i])) {
                continue;
            }
            array[count++] = src[i];
        }
        if (count < len) {
            array = trim(array, count, _class);
        }
        return array;
    }

    /**
     *
     * @param src
     * @param _skip
     * @return
     */
    public static final Object[] removeNulls(Object[] src, Object _skip) {
        int count = 0;
        int len = src.length;
        Object[] array = new Object[len];
        for (int i = 0; i < len; i++) {
            if (src[i] == null || src[i] == _skip) {
                continue;
            }
            array[count++] = src[i];
        }
        if (count < len) {
            array = trim(array, count);
        }
        return array;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param _skip
     * @param _class
     * @return
     */
    public static final <E> E[] removeNulls(E[] src, Object _skip, Class<? extends E> _class) {
        int count = 0;
        int len = src.length;
        E[] array = (E[]) Array.newInstance(_class, len);
        for (int i = 0; i < len; i++) {
            if (src[i] == null || src[i] == _skip) {
                continue;
            }
            array[count++] = src[i];
        }
        if (count < len) {
            array = trim(array, count, _class);
        }
        return array;
    }

    

    /**
     *
     * @param src
     * @return
     */
    public static final Object[] copy(Object[] src) {
        if (src == null) {
            return null;
        }
        Object[] dst = new Object[src.length];
        int count = dst.length;
        System.arraycopy(src, 0, dst, 0, count);
        return dst;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param arrayClass
     * @return
     */
    public static final <E> E[] copy(E[] src, Class<? extends E> arrayClass) {
        if (src == null) {
            return null;
        }
        E[] dst = (E[]) Array.newInstance(arrayClass, src.length);
        System.arraycopy(src, 0, dst, 0, src.length);
        return dst;
    }

    /**
     *
     * @param <E>
     * @param src
     * @param dst
     * @return
     */
    public static final <E> E[] copy(E[] src, E[] dst) {
        int count = dst.length;
        System.arraycopy(src, 0, dst, 0, count);
        return dst;
    }

    /**
     *
     * @param src
     * @param start
     * @param dst
     * @return
     */
    public static final Object[] copy(Object[] src, int start, Object[] dst) {
        System.arraycopy(src, start, dst, 0, dst.length);
        return dst;
    }

    /**
     *
     * @param src
     * @param start
     * @param count
     * @return
     */
    public static final String[] copy(String[] src, int start, int count) {
        String[] dst = new String[count];
        System.arraycopy(src, start, dst, 0, count);
        return dst;
    }

    /**
     *
     * @param src
     * @param start
     * @param count
     * @return
     */
    public static final Object[] copy(Object[] src, int start, int count) {
        Object[] dst = new Object[count];
        System.arraycopy(src, start, dst, 0, count);
        return dst;
    }

    /**
     *
     * @param src
     * @param _shuffle
     */
    public static final void shuffle(boolean[] src, int _shuffle) {
        for (int i = 0; i < _shuffle; i++) {
            int a = URandom.rand(src.length);
            int b = URandom.rand(src.length);
            boolean swap = src[a];
            src[a] = src[b];
            src[b] = swap;
        }
    }

    /**
     *
     * @param src
     * @param _shuffle
     */
    public static final void shuffle(byte[] src, int _shuffle) {
        for (int i = 0; i < _shuffle; i++) {
            int a = URandom.rand(src.length);
            int b = URandom.rand(src.length);
            byte swap = src[a];
            src[a] = src[b];
            src[b] = swap;
        }
    }

    /**
     *
     * @param src
     * @param _shuffle
     */
    public static final void shuffle(char[] src, int _shuffle) {
        for (int i = 0; i < _shuffle; i++) {
            int a = URandom.rand(src.length);
            int b = URandom.rand(src.length);
            char swap = src[a];
            src[a] = src[b];
            src[b] = swap;
        }
    }

    /**
     *
     * @param src
     * @param _shuffle
     */
    public static final void shuffle(int[] src, int _shuffle) {
        for (int i = 0; i < _shuffle; i++) {
            int a = URandom.rand(src.length);
            int b = URandom.rand(src.length);
            int swap = src[a];
            src[a] = src[b];
            src[b] = swap;
        }
    }

    /**
     *
     * @param src
     * @param _shuffle
     */
    public static final void shuffle(float[] src, int _shuffle) {
        for (int i = 0; i < _shuffle; i++) {
            int a = URandom.rand(src.length);
            int b = URandom.rand(src.length);
            float swap = src[a];
            src[a] = src[b];
            src[b] = swap;
        }
    }

    /**
     *
     * @param src
     * @param _shuffle
     */
    public static final void shuffle(long[] src, int _shuffle) {
        for (int i = 0; i < _shuffle; i++) {
            int a = URandom.rand(src.length);
            int b = URandom.rand(src.length);
            long swap = src[a];
            src[a] = src[b];
            src[b] = swap;
        }
    }

    /**
     *
     * @param src
     * @param _shuffle
     */
    public static final void shuffle(double[] src, int _shuffle) {
        for (int i = 0; i < _shuffle; i++) {
            int a = URandom.rand(src.length);
            int b = URandom.rand(src.length);
            double swap = src[a];
            src[a] = src[b];
            src[b] = swap;
        }
    }

    /**
     *
     * @param <E>
     * @param src
     * @param _shuffle
     */
    public static final <E> void shuffle(E[] src, int _shuffle) {
        for (int i = 0; i < _shuffle; i++) {
            int a = URandom.rand(src.length);
            int b = URandom.rand(src.length);
            E swap = src[a];
            src[a] = src[b];
            src[b] = swap;
        }
    }

    // Finds middle of an int array of counts
    // the middle position should be included in the first half
    /**
     *
     * @param _ints
     * @return
     */
    public static int middle(int[] _ints) {
        int l = _ints.length;
        long difference = Long.MAX_VALUE;
        int middle = l / 2;
        for (int i = 1; i < l; i++) {
            long fh = 0;
            for (int f = 0; f < i; f++) {
                fh += _ints[f];
            }
            long sh = 0;
            for (int s = i + 1; s < l; s++) {
                sh += _ints[s];
            }
            if (Math.abs(fh - sh) < difference) {
                difference = Math.abs(fh - sh);
                middle = i;
            }
        }
        return middle;
    }

    /**
     *
     * @param presorted
     * @return
     */
    public static final boolean[] centerSort(boolean[] presorted) {
        boolean[] result = new boolean[presorted.length];
        int center = presorted.length / 2;
        for (int i = 0, j = center; i < presorted.length; i += 2, j++) {
            result[j] = presorted[i];
        }
        for (int i = 1, j = center - 1; i < presorted.length; i += 2, j--) {
            result[j] = presorted[i];
        }
        return result;
    }

    /**
     *
     * @param presorted
     * @return
     */
    public static final byte[] centerSort(byte[] presorted) {
        byte[] result = new byte[presorted.length];
        int center = presorted.length / 2;
        for (int i = 0, j = center; i < presorted.length; i += 2, j++) {
            result[j] = presorted[i];
        }
        for (int i = 1, j = center - 1; i < presorted.length; i += 2, j--) {
            result[j] = presorted[i];
        }
        return result;
    }

    /**
     *
     * @param presorted
     * @return
     */
    public static final char[] centerSort(char[] presorted) {
        char[] result = new char[presorted.length];
        int center = presorted.length / 2;
        for (int i = 0, j = center; i < presorted.length; i += 2, j++) {
            result[j] = presorted[i];
        }
        for (int i = 1, j = center - 1; i < presorted.length; i += 2, j--) {
            result[j] = presorted[i];
        }
        return result;
    }

    /**
     *
     * @param presorted
     * @return
     */
    public static final int[] centerSort(int[] presorted) {
        int[] result = new int[presorted.length];
        int center = presorted.length / 2;
        for (int i = 0, j = center; i < presorted.length; i += 2, j++) {
            result[j] = presorted[i];
        }
        for (int i = 1, j = center - 1; i < presorted.length; i += 2, j--) {
            result[j] = presorted[i];
        }
        return result;
    }

    /**
     *
     * @param presorted
     * @return
     */
    public static final float[] centerSort(float[] presorted) {
        float[] result = new float[presorted.length];
        int center = presorted.length / 2;
        for (int i = 0, j = center; i < presorted.length; i += 2, j++) {
            result[j] = presorted[i];
        }
        for (int i = 1, j = center - 1; i < presorted.length; i += 2, j--) {
            result[j] = presorted[i];
        }
        return result;
    }

    /**
     *
     * @param presorted
     * @return
     */
    public static final double[] centerSort(double[] presorted) {
        double[] result = new double[presorted.length];
        int center = presorted.length / 2;
        for (int i = 0, j = center; i < presorted.length; i += 2, j++) {
            result[j] = presorted[i];
        }
        for (int i = 1, j = center - 1; i < presorted.length; i += 2, j--) {
            result[j] = presorted[i];
        }
        return result;
    }

    /**
     *
     * @param presorted
     * @return
     */
    public static final long[] centerSort(long[] presorted) {
        long[] result = new long[presorted.length];
        int center = presorted.length / 2;
        for (int i = 0, j = center; i < presorted.length; i += 2, j++) {
            result[j] = presorted[i];
        }
        for (int i = 1, j = center - 1; i < presorted.length; i += 2, j--) {
            result[j] = presorted[i];
        }
        return result;
    }

    /**
     *
     * @param presorted
     * @return
     */
    public static final Object[] centerSort(Object[] presorted) {
        Object[] result = new Object[presorted.length];
        int center = presorted.length / 2;
        for (int i = 0, j = center; i < presorted.length; i += 2, j++) {
            result[j] = presorted[i];
        }
        for (int i = 1, j = center - 1; i < presorted.length; i += 2, j--) {
            result[j] = presorted[i];
        }
        return result;
    }



    

    

    /**
     *
     * @param src
     * @param a
     * @param b
     * @return
     */
    public static final boolean[] remove(boolean[] src, int a, int b) {
        int l = src.length;
        if (l == 0)
            return src;
        if (a < 0)
            a = 0;
        if (b > l)
            b = l - 1;

        int s_a = a - 0;
        int b_e = l - b;

        boolean[] newSrc = new boolean[s_a + b_e];
        if (s_a > 0)
            System.arraycopy(src, 0, newSrc, 0, s_a);
        if (b_e > 0)
            System.arraycopy(src, b, newSrc, s_a, b_e);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param b
     * @return
     */
    public static final byte[] remove(byte[] src, int a, int b) {
        int l = src.length;
        if (l == 0)
            return src;
        if (a < 0)
            a = 0;
        if (b > l)
            b = l - 1;

        int s_a = a - 0;
        int b_e = l - b;

        byte[] newSrc = new byte[s_a + b_e];
        if (s_a > 0)
            System.arraycopy(src, 0, newSrc, 0, s_a);
        if (b_e > 0)
            System.arraycopy(src, b, newSrc, s_a, b_e);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param b
     * @return
     */
    public static final char[] remove(char[] src, int a, int b) {
        int l = src.length;
        if (l == 0)
            return src;
        if (a < 0)
            a = 0;
        if (b > l)
            b = l - 1;

        int s_a = a - 0;
        int b_e = l - b;

        char[] newSrc = new char[s_a + b_e];
        if (s_a > 0)
            System.arraycopy(src, 0, newSrc, 0, s_a);
        if (b_e > 0)
            System.arraycopy(src, b, newSrc, s_a, b_e);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param b
     * @return
     */
    public static final int[] remove(int[] src, int a, int b) {
        int l = src.length;
        if (l == 0)
            return src;
        if (a < 0)
            a = 0;
        if (b > l)
            b = l - 1;

        int s_a = a - 0;
        int b_e = l - b;

        int[] newSrc = new int[s_a + b_e];
        if (s_a > 0)
            System.arraycopy(src, 0, newSrc, 0, s_a);
        if (b_e > 0)
            System.arraycopy(src, b, newSrc, s_a, b_e);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param b
     * @return
     */
    public static final float[] remove(float[] src, int a, int b) {
        int l = src.length;
        if (l == 0)
            return src;
        if (a < 0)
            a = 0;
        if (b > l)
            b = l - 1;

        int s_a = a - 0;
        int b_e = l - b;

        float[] newSrc = new float[s_a + b_e];
        if (s_a > 0)
            System.arraycopy(src, 0, newSrc, 0, s_a);
        if (b_e > 0)
            System.arraycopy(src, b, newSrc, s_a, b_e);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param b
     * @return
     */
    public static final long[] remove(long[] src, int a, int b) {
        int l = src.length;
        if (l == 0)
            return src;
        if (a < 0)
            a = 0;
        if (b > l)
            b = l - 1;

        int s_a = a - 0;
        int b_e = l - b;

        long[] newSrc = new long[s_a + b_e];
        if (s_a > 0)
            System.arraycopy(src, 0, newSrc, 0, s_a);
        if (b_e > 0)
            System.arraycopy(src, b, newSrc, s_a, b_e);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param b
     * @return
     */
    public static final double[] remove(double[] src, int a, int b) {
        int l = src.length;
        if (l == 0)
            return src;
        if (a < 0)
            a = 0;
        if (b > l)
            b = l - 1;

        int s_a = a - 0;
        int b_e = l - b;

        double[] newSrc = new double[s_a + b_e];
        if (s_a > 0)
            System.arraycopy(src, 0, newSrc, 0, s_a);
        if (b_e > 0)
            System.arraycopy(src, b, newSrc, s_a, b_e);
        return newSrc;
    }
    
    /**
     *
     * @param src
     * @param a
     * @param b
     * @return
     */
    public static final String[] remove(String[] src, int a, int b) {
        int l = src.length;
        if (l == 0)
            return src;
        if (a < 0)
            a = 0;
        if (b > l)
            b = l - 1;

        int s_a = a - 0;
        int b_e = l - b;

        String[] newSrc = new String[s_a + b_e];
        if (s_a > 0)
            System.arraycopy(src, 0, newSrc, 0, s_a);
        if (b_e > 0)
            System.arraycopy(src, b, newSrc, s_a, b_e);
        return newSrc;
    }



    

    /**
     *
     * @param c
     * @param at
     * @param src
     * @return
     */
    public static final boolean[] insert(boolean c, int at, boolean[] src) {
        if (src == null) {
            return new boolean[]{c};
        }
        int l = src.length;
        if (at > l) {
            at = l;
        }
        if (at < 0) {
            at = 0;
        }

        boolean[] newSrc = new boolean[l + 1];

        if (at == 0) {
            newSrc[at] = c;
            System.arraycopy(src, 0, newSrc, 1, l);
            return newSrc;
        }

        if (at == l) {
            System.arraycopy(src, 0, newSrc, 0, l);
            newSrc[at] = c;
            return newSrc;
        }

        System.arraycopy(src, 0, newSrc, 0, at);
        newSrc[at] = c;
        System.arraycopy(src, at, newSrc, at + 1, l - at);
        return newSrc;
    }

    /**
     *
     * @param c
     * @param at
     * @param src
     * @return
     */
    public static final byte[] insert(byte c, int at, byte[] src) {
        if (src == null) {
            return new byte[]{c};
        }
        int l = src.length;
        if (at > l) {
            at = l;
        }
        if (at < 0) {
            at = 0;
        }

        byte[] newSrc = new byte[l + 1];

        if (at == 0) {
            newSrc[at] = c;
            System.arraycopy(src, 0, newSrc, 1, l);
            return newSrc;
        }

        if (at == l) {
            System.arraycopy(src, 0, newSrc, 0, l);
            newSrc[at] = c;
            return newSrc;
        }

        System.arraycopy(src, 0, newSrc, 0, at);
        newSrc[at] = c;
        System.arraycopy(src, at, newSrc, at + 1, l - at);
        return newSrc;
    }
    /**
     *
     * @param c
     * @param at
     * @param src
     * @return
     */
    public static final char[] insert(char c, int at, char[] src) {
        if (src == null) {
            return new char[]{c};
        }
        int l = src.length;
        if (at > l) {
            at = l;
        }
        if (at < 0) {
            at = 0;
        }

        char[] newSrc = new char[l + 1];

        if (at == 0) {
            newSrc[at] = c;
            System.arraycopy(src, 0, newSrc, 1, l);
            return newSrc;
        }

        if (at == l) {
            System.arraycopy(src, 0, newSrc, 0, l);
            newSrc[at] = c;
            return newSrc;
        }

        System.arraycopy(src, 0, newSrc, 0, at);
        newSrc[at] = c;
        System.arraycopy(src, at, newSrc, at + 1, l - at);
        return newSrc;
    }

    /**
     *
     * @param c
     * @param at
     * @param src
     * @return
     */
    public static final int[] insert(int c, int at, int[] src) {
        if (src == null) {
            return new int[]{c};
        }
        int l = src.length;
        if (at > l) {
            at = l;
        }
        if (at < 0) {
            at = 0;
        }

        int[] newSrc = new int[l + 1];

        if (at == 0) {
            newSrc[at] = c;
            System.arraycopy(src, 0, newSrc, 1, l);
            return newSrc;
        }

        if (at == l) {
            System.arraycopy(src, 0, newSrc, 0, l);
            newSrc[at] = c;
            return newSrc;
        }

        System.arraycopy(src, 0, newSrc, 0, at);
        newSrc[at] = c;
        System.arraycopy(src, at, newSrc, at + 1, l - at);
        return newSrc;
    }

    /**
     *
     * @param c
     * @param at
     * @param src
     * @return
     */
    public static final float[] insert(float c, int at, float[] src) {
        if (src == null) {
            return new float[]{c};
        }
        int l = src.length;
        if (at > l) {
            at = l;
        }
        if (at < 0) {
            at = 0;
        }

        float[] newSrc = new float[l + 1];

        if (at == 0) {
            newSrc[at] = c;
            System.arraycopy(src, 0, newSrc, 1, l);
            return newSrc;
        }

        if (at == l) {
            System.arraycopy(src, 0, newSrc, 0, l);
            newSrc[at] = c;
            return newSrc;
        }

        System.arraycopy(src, 0, newSrc, 0, at);
        newSrc[at] = c;
        System.arraycopy(src, at, newSrc, at + 1, l - at);
        return newSrc;
    }

    /**
     *
     * @param c
     * @param at
     * @param src
     * @return
     */
    public static final double[] insert(double c, int at, double[] src) {
        if (src == null) {
            return new double[]{c};
        }
        int l = src.length;
        if (at > l) {
            at = l;
        }
        if (at < 0) {
            at = 0;
        }

        double[] newSrc = new double[l + 1];

        if (at == 0) {
            newSrc[at] = c;
            System.arraycopy(src, 0, newSrc, 1, l);
            return newSrc;
        }

        if (at == l) {
            System.arraycopy(src, 0, newSrc, 0, l);
            newSrc[at] = c;
            return newSrc;
        }

        System.arraycopy(src, 0, newSrc, 0, at);
        newSrc[at] = c;
        System.arraycopy(src, at, newSrc, at + 1, l - at);
        return newSrc;
    }

    /**
     *
     * @param c
     * @param at
     * @param src
     * @return
     */
    public static final long[] insert(long c, int at, long[] src) {
        if (src == null) {
            return new long[]{c};
        }
        int l = src.length;
        if (at > l) {
            at = l;
        }
        if (at < 0) {
            at = 0;
        }

        long[] newSrc = new long[l + 1];

        if (at == 0) {
            newSrc[at] = c;
            System.arraycopy(src, 0, newSrc, 1, l);
            return newSrc;
        }

        if (at == l) {
            System.arraycopy(src, 0, newSrc, 0, l);
            newSrc[at] = c;
            return newSrc;
        }

        System.arraycopy(src, 0, newSrc, 0, at);
        newSrc[at] = c;
        System.arraycopy(src, at, newSrc, at + 1, l - at);
        return newSrc;
    }

    /**
     *
     * @param c
     * @param at
     * @param src
     * @return
     */
    public static final String[] insert(String c, int at, String[] src) {
        if (src == null) {
            return new String[]{c};
        }
        int l = src.length;
        if (at > l) {
            at = l;
        }
        if (at < 0) {
            at = 0;
        }

        String[] newSrc = new String[l + 1];

        if (at == 0) {
            newSrc[at] = c;
            System.arraycopy(src, 0, newSrc, 1, l);
            return newSrc;
        }

        if (at == l) {
            System.arraycopy(src, 0, newSrc, 0, l);
            newSrc[at] = c;
            return newSrc;
        }

        System.arraycopy(src, 0, newSrc, 0, at);
        newSrc[at] = c;
        System.arraycopy(src, at, newSrc, at + 1, l - at);
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param add
     * @param b
     * @return
     */
    public static final boolean[] add(boolean[] src, int a, boolean[] add, int b) {
        int l = add.length;

        int s_a = a - 0;
        int b_e = src.length - b;

        boolean[] newSrc = new boolean[s_a + l + b_e];
        if (s_a > 0) {
            System.arraycopy(src, 0, newSrc, 0, s_a);
        }
        if (l > 0 && a >= 0) {
            System.arraycopy(add, 0, newSrc, a, l);
        }
        if (b_e > 0) {
            System.arraycopy(src, b, newSrc, s_a + l, b_e);
        }
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param add
     * @param b
     * @return
     */
    public static final byte[] add(byte[] src, int a, byte[] add, int b) {
        int l = add.length;

        int s_a = a - 0;
        int b_e = src.length - b;

        byte[] newSrc = new byte[s_a + l + b_e];
        if (s_a > 0) {
            System.arraycopy(src, 0, newSrc, 0, s_a);
        }
        if (l > 0 && a >= 0) {
            System.arraycopy(add, 0, newSrc, a, l);
        }
        if (b_e > 0) {
            System.arraycopy(src, b, newSrc, s_a + l, b_e);
        }
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param add
     * @param b
     * @return
     */
    public static final char[] add(char[] src, int a, char[] add, int b) {
        int l = add.length;

        int s_a = a - 0;
        int b_e = src.length - b;

        char[] newSrc = new char[s_a + l + b_e];
        if (s_a > 0) {
            System.arraycopy(src, 0, newSrc, 0, s_a);
        }
        if (l > 0 && a >= 0) {
            System.arraycopy(add, 0, newSrc, a, l);
        }
        if (b_e > 0) {
            System.arraycopy(src, b, newSrc, s_a + l, b_e);
        }
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param add
     * @param b
     * @return
     */
    public static final float[] add(float[] src, int a, float[] add, int b) {
        int l = add.length;

        int s_a = a - 0;
        int b_e = src.length - b;

        float[] newSrc = new float[s_a + l + b_e];
        if (s_a > 0) {
            System.arraycopy(src, 0, newSrc, 0, s_a);
        }
        if (l > 0 && a >= 0) {
            System.arraycopy(add, 0, newSrc, a, l);
        }
        if (b_e > 0) {
            System.arraycopy(src, b, newSrc, s_a + l, b_e);
        }
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param add
     * @param b
     * @return
     */
    public static final int[] add(int[] src, int a, int[] add, int b) {
        int l = add.length;

        int s_a = a - 0;
        int b_e = src.length - b;

        int[] newSrc = new int[s_a + l + b_e];
        if (s_a > 0) {
            System.arraycopy(src, 0, newSrc, 0, s_a);
        }
        if (l > 0 && a >= 0) {
            System.arraycopy(add, 0, newSrc, a, l);
        }
        if (b_e > 0) {
            System.arraycopy(src, b, newSrc, s_a + l, b_e);
        }
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param add
     * @param b
     * @return
     */
    public static final double[] add(double[] src, int a, double[] add, int b) {
        int l = add.length;

        int s_a = a - 0;
        int b_e = src.length - b;

        double[] newSrc = new double[s_a + l + b_e];
        if (s_a > 0) {
            System.arraycopy(src, 0, newSrc, 0, s_a);
        }
        if (l > 0 && a >= 0) {
            System.arraycopy(add, 0, newSrc, a, l);
        }
        if (b_e > 0) {
            System.arraycopy(src, b, newSrc, s_a + l, b_e);
        }
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param add
     * @param b
     * @return
     */
    public static final long[] add(long[] src, int a, long[] add, int b) {
        int l = add.length;

        int s_a = a - 0;
        int b_e = src.length - b;

        long[] newSrc = new long[s_a + l + b_e];
        if (s_a > 0) {
            System.arraycopy(src, 0, newSrc, 0, s_a);
        }
        if (l > 0 && a >= 0) {
            System.arraycopy(add, 0, newSrc, a, l);
        }
        if (b_e > 0) {
            System.arraycopy(src, b, newSrc, s_a + l, b_e);
        }
        return newSrc;
    }

    /**
     *
     * @param src
     * @param a
     * @param add
     * @param b
     * @return
     */
    public static final String[] add(String[] src, int a, String[] add, int b) {
        int l = add.length;

        int s_a = a - 0;
        int b_e = src.length - b;

        String[] newSrc = new String[s_a + l + b_e];
        if (s_a > 0) {
            System.arraycopy(src, 0, newSrc, 0, s_a);
        }
        if (l > 0 && a >= 0) {
            System.arraycopy(add, 0, newSrc, a, l);
        }
        if (b_e > 0) {
            System.arraycopy(src, b, newSrc, s_a + l, b_e);
        }
        return newSrc;
    }

}

