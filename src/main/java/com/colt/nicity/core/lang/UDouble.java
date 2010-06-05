/*
 * UDouble.java.java
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

/**
 *
 * @author Administrator
 */
public class UDouble {

    /**
     *
     * @param _v
     * @param _spread
     * @return
     */
    public static int spreadIndex(double _v, double[] _spread) {
        for (int i = 0; i < _spread.length - 1; i++) {
            if (_v <= _spread[i + 1]) {
                return i;
            }
        }
        return _spread.length - 1;
    }

    /**
     *
     * @param _index
     * @param _spread
     * @return
     */
    public static double indexSpread(int _index, double[] _spread) {
        if (_index == _spread.length - 1) {
            _index -= 1;
        }
        return (_spread[_index] + _spread[_index + 1]) / 2d;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(double[] a, double[] b) {
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

    /**
     *
     * @param _sa
     * @param _ea
     * @param _sb
     * @param _eb
     * @return
     */
    public static boolean overlap(double _sa, double _ea, double _sb, double _eb) {
        if (_sa + _ea < _sb) {
            return false;
        }
        if (_sa > _sb + _eb) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param _values
     * @return
     */
    public static int[] ints(double[] _values) {
        int[] values = new int[_values.length];
        for (int i = 0; i < _values.length; i++) {
            values[i] = (int) _values[i];
        }
        return values;
    }

    /**
     *
     * @param _values
     */
    public static void unsign(double[] _values) {
        for (int i = 0; i < _values.length; i++) {
            _values[i] = Math.abs(_values[i]);
        }
    }

    /**
     *
     * @param _values
     * @param _amount
     */
    public static void decay(double[] _values, double _amount) {
        for (int i = 0; i < _values.length; i++) {
            _values[i] *= _amount;
        }
    }

    /**
     *
     * @param _a
     * @param _b
     */
    public static void max(double[] _a, double[] _b) {
        for (int i = 0; i < _a.length; i++) {
            _a[i] = Math.max(_a[i], _b[i]);
        }
    }

    // !!Note: returns an array one entry shorter than input
    /**
     *
     * @param _values
     * @return
     */
    public static double[] toDeltas(double[] _values) {
        return toDeltas(_values, 0, _values.length);
    }

    /**
     *
     * @param _values
     * @param _offset
     * @param _length
     * @return
     */
    public static double[] toDeltas(double[] _values, int _offset, int _length) {
        double[] deltas = new double[_length - 1];
        for (int i = _offset; i < _offset + _length - 1; i++) {
            deltas[i - _offset] = _values[i + 1] - _values[i];
        }
        return deltas;
    }

    /**
     *
     * @param _values
     * @return
     */
    public static double mean(double[] _values) {
        return mean(_values, 0, _values.length);
    }

    /**
     *
     * @param _values
     * @param _offset
     * @param _length
     * @return
     */
    public static double mean(double[] _values, int _offset, int _length) {
        double mean = 0;
        for (int i = _offset; i < _offset + _length; i++) {
            mean += _values[i];
        }
        mean /= _length;
        return mean;
    }

    // Specific to zero to one
    /**
     *
     * @param _value
     * @return
     */
    static public double roleOver(double _value) {
        _value %= 1;
        return _value;
    }

    /**
     *
     * @param _hits
     * @param _ave
     * @param _smoothing
     * @param _anticipate
     * @return
     */
    static public double[] movingAverage(double[] _hits, int _ave, int _smoothing, boolean _anticipate) {
        // _hits are any values >= zero; _ave defines the window for the moving average.

        _anticipate = true;//!! until movingAve2 works
        if (_anticipate) {
            for (int i = 0; i < _smoothing; i++) {
                _hits = movingAve(_hits, _ave);
            }
        }
        else {
            for (int i = 0; i < _smoothing; i++) {
                _hits = movingAve2(_hits, _ave);
            }
        }
        return _hits;
    }

    /**
     *
     * @param _hits
     * @param _ave
     * @return
     */
    static public double[] movingAve(double[] _hits, int _ave) {
        // computes moving average for _ave hits

        double[] result = new double[_hits.length];
        for (int i = 0; i < _hits.length; i++) {
            double sum = 0;
            for (int j = -(_ave / 2); j < _ave / 2; j++) {
                int k = i + j;
                if (k < 0) {
                    continue;
                }
                if (k > _hits.length - 1) {
                    continue;
                }
                double h = _hits[k];
                if (h <= 1 && h >= 0) {
                    sum += h;
                }
            }
            result[i] = sum / _ave;
        }
        return result;
    }

    /**
     *
     * @param _hits
     * @param _ave
     * @return
     */
    static public double[] movingAve2(double[] _hits, int _ave) {
        // same as movingAve, but does NOT anticipate future

        double[] result = new double[_hits.length];
        for (int i = 0; i < _hits.length; i++) {
            double sum = 0;
            for (int j = -_ave; j < i; j++) {
                int k = i + j;
                if (k < 0) {
                    continue;
                }
                if (k > _hits.length - 1) {
                    continue;
                }
                double h = _hits[k];
                if (h <= 1 && h >= 0) {
                    sum += h;
                }
            }
            result[i] = sum / _ave;
        }
        return result;
    }

    /**
     *
     * @param _v
     * @param _min
     * @param _max
     * @return
     */
    public static final double clamp(double _v, double _min, double _max) {
        if (_v < _min) {
            return _min;
        }
        else if (_v > _max) {
            return _max;
        }
        return _v;
    }

    /**
     *
     * @param _v
     * @param _min
     * @param _max
     * @return
     */
    public static final double range(double _v, double _min, double _max) {
        if (_v < _min) {
            return _min;
        }
        else if (_v > _max) {
            return _max;
        }
        return _v;
    }

    /**
     *
     * @param _v
     * @param _min
     * @param _max
     * @return
     */
    public static final double constrain(double _v, double _min, double _max) {
        if (_v < _min) {
            return _min;
        }
        else if (_v > _max) {
            return _max;
        }
        return _v;
    }

    /**
     *
     * @param _v
     * @param _min
     * @param _max
     * @return
     */
    public static final double limit(double _v, double _min, double _max) {
        if (_v < _min) {
            return _min;
        }
        else if (_v > _max) {
            return _max;
        }
        return _v;
    }

    /**
     *
     * @param source
     * @param alternate
     * @return
     */
    static public double check(double source, double alternate) {
        if (source == Double.POSITIVE_INFINITY
            || source == Double.NEGATIVE_INFINITY
            || source != source) {
            return alternate;
        }
        return source;
    }

    /**
     *
     * @param _array
     * @param _min
     * @param _max
     * @return
     */
    public static double[] bound(double[] _array, double _min, double _max) {// rename to scale
        int l = _array.length;
        if (l == 0) {
            return new double[0];
        }
        MinMaxDouble minMax = new MinMaxDouble();
        for (int i = 0; i < l; i++) {
            minMax.value(_array[i]);
        }
        return bound(_array, minMax.min(), minMax.max(), _min, _max);
    }

    /**
     *
     * @param _array
     * @param _inMin
     * @param _inMax
     * @param _outMin
     * @param _outMax
     * @return
     */
    public static double[] bound(
        double[] _array, double _inMin, double _inMax,
        double _outMin, double _outMax) {// rename to scale
        int l = _array.length;
        double inRange = _inMax - _inMin;
        double outRange = _outMax - _outMin;
        double[] array = new double[l];
        if (inRange == 0) {
            return UArray.copy(_array, array);
        }
        for (int i = 0; i < l; i++) {
            double before = _array[i];
            array[i] = _outMin + (outRange * ((Math.abs(_array[i] - _inMin)) / inRange));
            if (_outMin == 0 && _outMax == 1 && (array[i] < 0 || array[i] > 1)) {
                System.out.println("*******" + array[i]);
                System.out.println(_outMin + " , " + outRange + " , " + inRange + " , " + _inMin + " , " + before);
            }
        }
        return array;
    }

    /*
    public static void bound(
    Object[] _array, double _inMin, double _inMax,
    double _outMin, double _outMax) {// rename to scale
    int l = _array.length;
    double inRange = _inMax - _inMin;
    double outRange = _outMax - _outMin;
    if (inRange == 0) {
    return;
    }
    for (int i = 0; i < l; i++) {
    double v = _outMin + (outRange * ((Math.abs(((IHaveDouble) _array[i]).getDouble() - _inMin)) / inRange));
    ((IHaveDouble) _array[i]).setDouble(v);
    }
    }*/
    /**
     *
     * @param _array
     * @return
     */
    public static double[] bridgeZeros(double[] _array) {
        return bridgeZeros(_array, -1);
    }

    // if array contains
    /**
     *
     * @param _array
     * @param _decay
     * @return
     */
    public static double[] bridgeZeros(double[] _array, int _decay) {
        int start = nextNon(_array, 0, 0);
        if (start == -1) {
            return _array;
        }
        start = lastNotNon(_array, start, 0);
        if (start == -1) {
            return _array;
        }
        while (start < _array.length - 1) {
            int end = nextNon(_array, start + 1, 0);
            if (end == -1) {
                return _array;
            }
            int _end = end;
            if (_decay > 0 && start + _decay < end) {
                _end = start + _decay;
            }
            for (int i = start + 1; i < _end; i++) {
                double p = ((i - start) / (double) (_end - start));
                _array[i] = linearInterpolation(_array[start], _array[_end], p);
            }
            start = lastNotNon(_array, end, 0);
            if (start == -1) {
                return _array;
            }
        }
        return _array;
    }

    final private static int nextNon(double[] _array, int _s, double _value) {
        for (int i = _s; i < _array.length; i++) {
            if (_array[i] == _value) {
                continue;
            }
            return i;
        }
        return -1;
    }

    final private static int lastNotNon(double[] _array, int _s, double _value) {
        for (int i = _s; i < _array.length - 1; i++) {
            if (_array[i] != _value && _array[i + 1] == _value) {
                return i;
            }
        }
        return -1;
    }

    // _distance >= 0.0 && <= 1.0
    /**
     *
     * @param _values
     * @param _new
     * @return
     */
    public static double[] linearInterpolation(double[] _values, double[] _new) {
        for (double i = 0; i < _new.length; i++) {
            _new[(int) i] = linearInterpolation(_values, i / (_new.length - 1));//dg
        }
        return _new;
    }

    // _distance >= 0.0 && <= 1.0
    /**
     *
     * @param _values
     * @param _value
     * @param _fp
     * @param _tp
     * @return
     */
    public static double[] fill(double[] _values, double _value, double _fp, double _tp) {
        int l = _values.length;
        if (l == 1) {
            _values[0] = _value;
            return _values;
        }
        if (_fp > _tp) {
            return _values;
        }
        int from = index(_values, _fp);
        if (from == l) {
            return _values;
        }
        if (from < 0) {
            from = 0;
        }
        int to = index(_values, _tp);
        if (to >= _values.length) {
            to = _values.length;
        }
        if (to < 0) {
            return _values;
        }
        if (from == to) {
            _values[from] = _value;
        }
        else {
            for (int i = from; i < to; i++) {
                _values[i] = _value;
            }
        }
        return _values;
    }

    /**
     *
     * @param _values
     * @param _p
     * @return
     */
    public static int index(double[] _values, double _p) {
        int l = _values.length;
        int index = (int) (l * _p);
        if (index >= l) {
            return l;
        }
        return index;
    }

    // good for simple morphs
    /**
     *
     * @param _from
     * @param _to
     * @param _p
     * @return
     */
    public static double[] linearInterpolation(double[] _from, double[] _to, double _p) {
        if (Double.isNaN(_p)) {
            _p = 0;
        }
        if (Double.isInfinite(_p)) {
            _p = 1;
        }
        int maxLength = Math.max(_from.length, _to.length);
        double[] result = new double[maxLength];
        for (int i = 0; i < maxLength; i++) {
            double p = (double) i / (double) (maxLength - 1);
            double f = UDouble.linearInterpolation(_from, p);
            double t = UDouble.linearInterpolation(_to, p);
            result[i] = UDouble.linearInterpolation(f, t, _p);
        }
        return result;
    }

    //
    /**
     *
     * @param _xs
     * @param _ys
     * @param _p
     * @param _length
     * @return
     */
    public static double[] lengthInterpolation(double[] _xs, double[] _ys, double _p, double _length) {
        double totalLength = 0;
        int l = _xs.length;
        for (int i = 1; i < l; i++) {
            double length = UMath.distance(_xs[i - 1], _ys[i - 1], _xs[i], _ys[i]);
            if ((totalLength + length) / _length < _p) {
                totalLength += length;
            }
            else {
                double lp = totalLength / _length;
                double p = (_p - lp) / (length / _length);
                double x = linearInterpolation(_xs[i - 1], _xs[i], p);
                double y = linearInterpolation(_ys[i - 1], _ys[i], p);
                return new double[]{x, y};
            }
        }
        return new double[]{_xs[_xs.length - 1], _ys[_ys.length - 1]};
    }

    /**
     *
     * @param _xs
     * @param _ys
     * @return
     */
    public static double length(double[] _xs, double[] _ys) {
        double totalLength = 0;
        int l = _xs.length;
        for (int i = 1; i < l; i++) {
            totalLength += UMath.distance(_xs[i - 1], _ys[i - 1], _xs[i], _ys[i]);
        }
        return totalLength;
    }

    // _distance >= 0.0 && <= 1.0
    /**
     *
     * @param _values
     * @param _distance
     * @return
     */
    public static double linearInterpolation(double[] _values, double _distance) {
        if (_values == null) {
            return 0;
        }
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
        if (l == 1) {
            return _values[0];
        }
        double distance = (l - 1) * _distance;
        int index = (int) distance;
        if (index >= l - 1) {
            return _values[l - 1];
        }
        distance -= index;// turns distance into remainder
        double a = _values[index];
        double b = _values[index + 1];
        return linearInterpolation(a, b, distance);
    }

    // _distance >= 0.0 && <= 1.0
    /**
     *
     * @param _values
     * @return
     */
    public static boolean containsNaN(double[] _values) {
        for (int i = 0; i < _values.length; i++) {
            if (Double.isNaN(_values[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _distance
     * @return
     */
    public static double linearInterpolation(double _a, double _b, double _distance) {
        return _a + ((_b - _a) * _distance);
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _distance
     * @return
     */
    public static float linearInterpolation(int _a, int _b, float _distance) {
        return _a + ((_b - _a) * _distance);
    }

    /**
     *
     * @param _values
     * @param _distance
     * @param _offValue
     * @return
     */
    public static double linearInterpolation(double[] _values, double _distance, double _offValue) {
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
        if (l == 1) {
            return _values[0];
        }

        double distance = (l - 1) * _distance;
        int index = (int) distance;
        if (index >= l - 1) {
            return _values[l - 1];
        }
        distance -= index;

        double a = _values[index];
        double b = _values[index + 1];
        if (a == _offValue) {
            a = b;
        }
        if (b == _offValue) {
            b = a;
        }
        return linearInterpolation(a, b, distance);
        //return _values[index];
    }

    /**
     *
     * @param _x1
     * @param _y1
     * @param _x2
     * @param _y2
     * @return
     */
    public static double distance(double _x1, double _y1, double _x2, double _y2) {
        double a = (_x1 - _x2) * (_x1 - _x2);
        double b = (_y1 - _y2) * (_y1 - _y2);
        return Math.sqrt(a + b);
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _distance
     * @return
     */
    public static double linearDelta(double _a, double _b, double _distance) {
        return (_b - _a) * _distance;
    }

    /**
     *
     * @param _d
     * @param _precision
     * @return
     */
    public static double trimPrecision(double _d, double _precision) {
        if (_precision == 0) {
            return (double) (long) _d;
        }
        long l = (long) (_d * (10 * _precision));
        return l / (10 * _precision);
    }

    /**
     *
     * @param _zeroToOne
     * @param _count
     * @return
     */
    public static final double centerSort(double _zeroToOne, int _count) {
        double step = 1d / (double) _count;
        double v = _zeroToOne * _count;
        if ((int) v % 2 == 0) {
            return 0.5d + ((v / 2) * step);
        }
        else {
            return 0.5d - ((v / 2) * step);
        }
    }

    /**
     *
     * @param position
     * @param count
     * @return
     */
    public static final double centerSortPercent(int position, int count) {
        // 0 <= position <= count
        // position=0 returns .5
        // position=count returns 0 or 1 depending on whether count is even or odd
        // all other positions alternate and span out from .5
        double factor = 0;
        if (position % 2 == 0) { // even
            factor = position / 2;
            factor /= count;
            return .5d - factor;
        }
        else { // odd
            factor = (position + 1) / 2;
            factor /= count;
            return .5d + factor;
        }
    }

    /**
     *
     * @param _value
     * @param _min
     * @param _max
     * @return
     */
    public static double percent(double _value, double _min, double _max) {
        if (_value < _min) {
            return 0 - Double.MIN_VALUE;
        }
        if (_value > _max) {
            return 1 + Double.MIN_VALUE;
        }
        return (_value - _min) / (_max - _min);
    }
    /*
    If factor ==0, perfectly linear, and result == _percent.
    If factor ==+1, perfectly exponential, with fast at start and slow at end
    If factor ==-1, perfectly exponential, with slow at start and fast at end
    Fractional factors between -1 and 1 will modify the speed.
     */

    /**
     *
     * @param _percent
     * @param _factor
     * @return
     */
    public static double exponential(double _percent, double _factor) {
        return exponential(_percent, _factor, 1);
    }

    /**
     *
     * @param _percent
     * @param _factor
     * @param _power
     * @return
     */
    public static double exponential(double _percent, double _factor, int _power) {
        // 0 <= _percent <=1; -1 <= _factor <=1; 0 <= result <=1
        // If factor == 0, perfectly linear, and result == _percent.
        // If factor == +1, exponential squared, with fast at start and slow at end
        // If factor == -1, exponential squared, with slow at start and fast at end
        // Fractional factors between -1 and 1 will modify the speed.
        // for major adjustments to speed, set _power to some small number; try 2,3,4,5; more than 6 is extreme!
        double result = _percent;
        for (int i = 0; i < _power; i++) {
            result = result + ((1.0d - result) * result * _factor);
        }
        return result;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static double correlate(double[] _a, double[] _b) {
        if (_a.length == _b.length) {
            return unorderedEqualCorrelate(_a, _b);
        }
        return unorderedUnequalCorrelate(_a, _b);
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static double unorderedUnequalCorrelate(double[] _a, double[] _b) {
        int maxLength = Math.max(_a.length, _b.length);
        double rank = 0;
        for (int i = 0; i < maxLength; i++) {
            double p = (double) i / (double) (maxLength - 1);
            double a = UDouble.linearInterpolation(_a, p);
            double b = UDouble.linearInterpolation(_b, p);
            rank += (1d - Math.abs(a - b)) * ((a + b) / 2);
        }
        rank /= maxLength;
        return rank;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static double unorderedEqualCorrelate(double[] _a, double[] _b) {
        if (_a.length != _b.length) {
            throw new RuntimeException("requires args of same length");
        }
        double rank = 0;
        for (int i = 0; i < _a.length; i++) {
            double a = _a[i];
            double b = _b[i];
            rank += (1d - Math.abs(a - b)) * ((a + b) / 2);
        }
        rank /= _a.length;
        return rank;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public static double maximizedUnorderedCorrelation(double[] _a, double[] _b) {
        double avgCorrelation = 0;
        for (int i = 0; i < _a.length; i++) {
            double maxCorrelation = 0;
            for (int j = 0; j < _b.length; j++) {
                double a = _a[i];
                double b = _b[j];
                double correlation = (1d - Math.abs(a - b)) * ((a + b) / 2);
                if (correlation > maxCorrelation) {
                    maxCorrelation = correlation;
                }
            }
            avgCorrelation += maxCorrelation;
        }
        avgCorrelation /= _a.length;
        return avgCorrelation;
    }

    /**
     *
     * @param _count
     * @param _window
     * @param _smoothing
     * @return
     */
    public static double[] noiseWaveform(int _count, int _window, int _smoothing) {
        double[] wave = new double[_count];
        for (int i = 0; i < _count; i++) {
            if (URandom.rand(_count) > _count / 2) {
                wave[i] = 0;
            }
            else {
                wave[i] = 1;
            }
        }
        double[] sma = UDouble.movingAverage(
            wave, _window, _smoothing,
            false);
        return sma;
    }
}
