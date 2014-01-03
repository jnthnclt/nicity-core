/*
 * RateMonitor.java.java
 *
 * Created on 12-30-2009 07:20:00 AM
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
package colt.nicity.core.time;

import colt.nicity.core.lang.UDouble;

/**
 * 
 * @author Administrator
 */
public class RateMonitor {
    /**
     *
     */
    public long count = 0;
    private long lastTime = 0;
    private long lastCount = 0;
    private double maxRate = 0;
    private double rate = 0;
    private long perTimeUnit = 1000;
    private String name = "";
    private long unitsSize = 1;
    private String unitsName = "";
    private boolean changeVolume;// false change true volume
    /**
     *
     * @param _changeVolume
     */
    public RateMonitor(boolean _changeVolume) {
        this("", _changeVolume);
    }
    /**
     *
     * @param _name
     * @param _changeVolume
     */
    public RateMonitor(String _name, boolean _changeVolume) {
        if (_name != null) {
            name = _name;
        }
        changeVolume = _changeVolume;
    }
    /**
     *
     * @param _unitsName
     * @param _unitsSize
     */
    public void setUnits(String _unitsName, long _unitsSize) {
        unitsName = _unitsName;
        unitsSize = _unitsSize;
    }
    /**
     *
     */
    public void reset() {
        lastTime = 0;
        lastCount = 0;
        maxRate = 0;
        rate = 0;
        count = 0;
    }
    /**
     *
     * @param _count
     */
    public void count(long _count) {
        count = _count;
    }
    /**
     *
     */
    public void inc() {
        count++;
    }
    /**
     *
     * @param _count
     */
    public void inc(int _count) {
        count += _count;
    }
    /**
     *
     * @return
     */
    public long getCount() {
        return count;
    }
    /**
     *
     * @return
     */
    public double getRate() {
        return rate;
    }
    /**
     *
     * @return
     */
    public double getMaxRate() {
        return maxRate;
    }
    /**
     *
     * @return
     */
    public double getZeroToOne() {
        double _rate = rate;
        if (maxRate == 0) {
            return 0;
        }
        return _rate / maxRate;
    }
    /**
     *
     * @param _count
     * @return
     */
    public String timeRemaining(long _count) {
        double _rate = rate;
        if (_rate == 0) {
            return "";
        }
        long remaining = (long) (((double) _count / (double) _rate) * perTimeUnit);
        return UTime.elapse(remaining);
    }
    /**
     *
     * @param _rate
     * @return
     */
    public String format(long _rate) {
        return name + " " + _rate;
    }
    /**
     *
     */
    public void calculate() {
        long time = System.currentTimeMillis();
        if (time > lastTime) {
            perTimeUnit = time - lastTime;
            long _count = getCount();
            if (changeVolume) {
                rate = _count;
            } else {
                rate = (double) ((_count - lastCount)) * ((double) 1000 / (double) (time - lastTime));
            }
            maxRate = Math.max(rate, maxRate);
            lastCount = _count;
        }
        lastTime = time;
    }
    @Override
    public String toString() {
        double _rate = rate;
        if (unitsSize > 1 && _rate > 0) {
            _rate /= unitsSize;
        }
        _rate = UDouble.trimPrecision(_rate, 4);
        double _maxRate = maxRate;
        if (unitsSize > 1 && _maxRate > 0) {
            _maxRate /= unitsSize;
        }
        _maxRate = UDouble.trimPrecision(_maxRate, 4);
        if (changeVolume) {
            return " " + name + " " + ((int) _rate) + unitsName + " ";
        } else {
            return " " + name + " " + ((int) _rate) + unitsName + " ";
        }
    }
}
