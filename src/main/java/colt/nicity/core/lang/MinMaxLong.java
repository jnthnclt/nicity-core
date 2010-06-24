/*
 * MinMaxLong.java.java
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
public class MinMaxLong {
    /**
     *
     */
    public long min = Long.MAX_VALUE;
    /**
     *
     */
    public long max = -Long.MAX_VALUE;
    /**
     *
     */
    public int minIndex = -1;
    /**
     *
     */
    public int maxIndex = -1;
    private float sum = 0;
    private int count = 0;
    /**
     *
     */
    public MinMaxLong() {
    }
    /**
     *
     * @param _min
     * @param _max
     */
    public MinMaxLong(long _min, long _max) {
        min = _min;
        max = _max;
    }
    /**
     *
     * @return
     */
    public long min() {
        return min;
    }
    /**
     *
     * @return
     */
    public long max() {
        return max;
    }
    /**
     *
     * @param _long
     * @return
     */
    public long value(long _long) {
        sum += _long;
        if (_long > max) {
            max = _long;
            maxIndex = count;
        }
        if (_long < min) {
            min = _long;
            minIndex = count;
        }
        count++;
        return _long;
    }
    /**
     *
     */
    public void reset() {
        min = Long.MAX_VALUE;
        max = -Long.MAX_VALUE;

        minIndex = -1;
        maxIndex = -1;

        sum = 0;
        count = 0;
    }
    /**
     *
     * @param _value
     * @return
     */
    public boolean inclusivelyContained(long _value) {
        if (count == 0) {
            return false;
        }
        if (_value < min) {
            return false;
        }
        if (_value > max) {
            return false;
        }
        return true;
    }
    /**
     *
     * @param _value
     * @return
     */
    public double std(long _value) {
        double mean = Math.pow(mean(), 2);
        double value = Math.pow((double) _value, 2);
        double variation = Math.max(mean, value) - Math.min(mean, value);
        return Math.sqrt(variation);
    }
    /**
     *
     * @return
     */
    public long samples() {
        return count;
    }
    /**
     *
     * @return
     */
    public double mean() {
        return sum / (long) count;
    }
    /**
     *
     * @return
     */
    public long range() {
        return max() - min();
    }
    /**
     *
     * @return
     */
    public long middle() {
        return min() + (range() / 2);
    }
    /**
     *
     * @param _long
     * @return
     */
    public double zeroToOne(long _long) {
        return zeroToOne(min, max, _long);
    }
    /**
     *
     * @param _long
     * @return
     */
    public long unzeroToOne(double _long) {
        return unzeroToOne(min, max, _long);
    }
    @Override
    public String toString() {
        return "Min:" + min + " Max:" + max;
    }
    /**
     *
     * @param _min
     * @param _max
     * @param _long
     * @return
     */
    public static double zeroToOne(long _min, long _max, long _long) {
        if (_max == _min) {
            if (_long == _min) {
                return 0;
            }
            if (_long > _max) {
                return Double.MAX_VALUE;
            }
            return -Double.MAX_VALUE;
        }
        return (double) (_long - _min) / (double) (_max - _min);
    }
    /**
     *
     * @param _min
     * @param _max
     * @param _p
     * @return
     */
    public static long unzeroToOne(long _min, long _max, double _p) {
        long d = _max - _min;
        long pd = (long) (d * _p);
        return _min + pd;
    }
}
