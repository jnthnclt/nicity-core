/*
 * MinMaxDouble.java.java
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
package com.colt.nicity.core.lang;

public class MinMaxDouble {
    public double min = Double.MAX_VALUE;
    public double max = -Double.MAX_VALUE;
    public int minIndex = -1;
    public int maxIndex = -1;
    private float sum = 0;
    private int count = 0;
    public MinMaxDouble() {
    }
    public MinMaxDouble(double _min, double _max) {
        min = _min;
        max = _max;
        count = 2;
    }
     public double std(double _value) {
        double mean = Math.pow(mean(), 2);
        double value = Math.pow((double) _value, 2);
        double variation = Math.max(mean, value) - Math.min(mean, value);
        return Math.sqrt(variation);
    }
    public boolean inclusivelyContained(double _p) {
        if (_p < min) {
            return false;
        }
        if (_p > max) {
            return false;
        }
        return true;
    }
    public double min() {
        return min;
    }
    public double max() {
        return max;
    }
    public double value(double _double) {
        sum += _double;
        if (_double > max) {
            max = _double;
            maxIndex = count;
        }
        if (_double < min) {
            min = _double;
            minIndex = count;
        }
        count++;
        return _double;
    }
    public void reset() {
        min = Double.MAX_VALUE;
        max = -Double.MAX_VALUE;

        minIndex = -1;
        maxIndex = -1;

        sum = 0;
        count = 0;
    }
    public long samples() {
        return count;
    }
    public double mean() {
        return sum / (double) count;
    }
    public double range() {
        return max - min;
    }
    public double middle() {
        return min + ((max - min) / 2);
    }
    public boolean isBetween(double _v, boolean _inclusive) {
        if (_inclusive) {
            return _v <= max && _v >= min;
        } else {
            return _v < max && _v > min;
        }
    }
    public double negativeOneToOne(double _double) {
        return (zeroToOne(_double) - 0.5d) * 2d;
    }
    public double zeroToOne(double _double) {
        return zeroToOne(min, max, _double);
    }
    final public static double zeroToOne(double _min, double _max, double _double) {
        return (_double - _min) / (_max - _min);
    }
    public double unzeroToOne(double _double) {
        return unzeroToOne(min, max, _double);
    }
    final public static double unzeroToOne(double _min, double _max, double _double) {
        return ((_max - _min) * _double) + _min;
    }
    @Override
    public String toString() {
        return "Min:" + min + " Max:" + max;
    }
}
