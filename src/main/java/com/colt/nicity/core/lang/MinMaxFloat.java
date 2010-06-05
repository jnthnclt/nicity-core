/*
 * MinMaxFloat.java.java
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

public class MinMaxFloat {
    public float min = Float.MAX_VALUE;
    public float max = -Float.MAX_VALUE;
    public int minIndex = -1;
    public int maxIndex = -1;
    private float sum = 0;
    private int count = 0;
    public MinMaxFloat() {
    }
    public MinMaxFloat(float _min, float _max) {
        min = _min;
        max = _max;
        count = 2;
    }

    public double std(float _value) {
        double mean = Math.pow(mean(), 2);
        double value = Math.pow((double) _value, 2);
        double variation = Math.max(mean, value) - Math.min(mean, value);
        return Math.sqrt(variation);
    }
    public boolean inclusivelyContained(float _p) {
        if (_p < min) {
            return false;
        }
        if (_p > max) {
            return false;
        }
        return true;
    }
    public float min() {
        return min;
    }
    public float max() {
        return max;
    }
    public float value(float _float) {
        sum += _float;
        if (_float > max) {
            max = _float;
            maxIndex = count;
        }
        if (_float < min) {
            min = _float;
            minIndex = count;
        }
        count++;
        return _float;
    }
    public void reset() {
        min = Float.MAX_VALUE;
        max = -Float.MAX_VALUE;

        minIndex = -1;
        maxIndex = -1;

        sum = 0;
        count = 0;
    }
    public long samples() {
        return count;
    }
    public float mean() {
        return sum / (float) count;
    }
    public float range() {
        return max - min;
    }
    public float middle() {
        return min + ((max - min) / 2);
    }
    public boolean isBetween(float _v, boolean _inclusive) {
        if (_inclusive) {
            return _v <= max && _v >= min;
        } else {
            return _v < max && _v > min;
        }
    }
    public float negativeOneToOne(float _float) {
        return (zeroToOne(_float) - 0.5f) * 2f;
    }
    public float zeroToOne(float _float) {
        return zeroToOne(min, max, _float);
    }
    final public static float zeroToOne(float _min, float _max, float _float) {
        return (_float - _min) / (_max - _min);
    }
    public float unzeroToOne(float _float) {
        return unzeroToOne(min, max, _float);
    }
    final public static float unzeroToOne(float _min, float _max, float _float) {
        return ((_max - _min) * _float) + _min;
    }
    @Override
    public String toString() {
        return "Min:" + min + " Max:" + max;
    }
}
