/*
 * MilliTimer.java.java
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
package com.colt.nicity.core.time;

/**
 *
 * @author Administrator
 */
public class MilliTimer {
    private long start;
    private int duration = 0;
    private int count = 0;
    private int min = Integer.MAX_VALUE;
    private int max = 0;
    private boolean running = false;
    /**
     *
     */
    public MilliTimer() {
    }
    /**
     *
     */
    public void start() {
        running = true;
        start = System.currentTimeMillis();
    }
    /**
     *
     */
    public void stop() {
        if (!running) {
            return;
        }
        int time = (int) (System.currentTimeMillis() - start);
        if (time < min) {
            min = time;
        }
        if (time > max) {
            max = time;
        }
        duration += time;
        count++;
        running = false;
    }
    /**
     *
     */
    public void reset() {
        duration = 0;
        count = 0;
        min = Integer.MAX_VALUE;
        max = 0;
    }
    // start-stop, start-stop, etc. accumulates a total duration; reset to zero
    /**
     *
     * @return
     */
    public int duration() {// milliseconds accumulation since reset
        return duration;
    }
    /**
     *
     * @return
     */
    public int count() {
        return count;
    }
    /**
     *
     * @return
     */
    public int min() {
        return min;
    }
    /**
     *
     * @return
     */
    public int max() {
        return max;
    }
    /**
     *
     * @return
     */
    public int ave() {
        return duration / count;
    }
    /**
     *
     * @return
     */
    public String stats() {
        return "Elapse:" + UTime.elapse(duration);
    }
    // if you want to glance at the stopwatch without stopping it
    /**
     *
     * @return
     */
    public int glance() {// milliseconds since first start after reset
        if (!running) {
            return duration;
        }
        return (int) (System.currentTimeMillis() - start);
    }
    @Override
    public String toString() {
        return stats();
    }
}
