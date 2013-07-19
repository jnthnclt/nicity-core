/*
 * ULong.java.java
 *
 * Created on 12-29-2009 07:41:00 PM
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
public class ULong {
    /**
     *
     * @param _longs
     * @return
     */
    public static byte[] longsBytes(long[] _longs) {
        int len = _longs.length;
        byte[] bytes = new byte[len * 8];
        for (int i = 0; i < len; i++) {
            longBytes(_longs[i], bytes, i * 8);
        }
        return bytes;
    }
    /**
     *
     * @param _v
     * @return
     */
    public static byte[] longBytes(long _v) {
        return longBytes(_v, new byte[8], 0);
    }
    /**
     *
     * @param v
     * @param _bytes
     * @param _offset
     * @return
     */
    public static byte[] longBytes(long v, byte[] _bytes, int _offset) {
        _bytes[_offset + 0] = (byte) (v >>> 56);
        _bytes[_offset + 1] = (byte) (v >>> 48);
        _bytes[_offset + 2] = (byte) (v >>> 40);
        _bytes[_offset + 3] = (byte) (v >>> 32);
        _bytes[_offset + 4] = (byte) (v >>> 24);
        _bytes[_offset + 5] = (byte) (v >>> 16);
        _bytes[_offset + 6] = (byte) (v >>> 8);
        _bytes[_offset + 7] = (byte) v;
        return _bytes;
    }
    /**
     *
     * @param _v
     * @param _min
     * @param _max
     * @return
     */
    public static long range(long _v, long _min, long _max) {
        if (_v < _min) {
            return _min;
        } else if (_v > _max) {
            return _max;
        }
        return _v;
    }
    /**
     *
     * @param values
     * @return
     */
    public static long toHash(long[] values) {
        long hash = 0;
        for (int i = 0; i < values.length; i++) {
            hash += values[i];
        }
        return hash;
    }
    /**
     *
     * @param values
     * @param delim
     * @return
     */
    public static String toString(long[] values, String delim) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            string.append(values[i]);
            if (i < values.length - 1) {
                string.append(delim);
            }
        }
        return string.toString();
    }
}
