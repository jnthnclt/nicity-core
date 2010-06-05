/*
 * XY_I.java.java
 *
 * Created on 12-30-2009 09:38:00 PM
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
package com.colt.nicity.core.memory.struct;

/**
 *
 * @author Administrator
 */
public class XY_I {

    /**
     *
     */
    public int x;
    /**
     *
     */
    public int y;

    /**
     *
     */
    public XY_I() {
    }

    /**
     *
     * @param x
     * @param y
     */
    public XY_I(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @param _x
     * @param _y
     */
    public void add(int _x, int _y) {
        x += _x;
        y += _y;
    }
    // Takes the incoming point and sets it's "x" and "y" if this point's "x" and "y" are larger

    /**
     *
     * @param point
     * @return
     */
    public XY_I max(XY_I point) {
        if (point.x < x) {
            point.x = x;
        }
        if (point.y < y) {
            point.y = y;
        }
        return point;
    }
    // Takes the incoming point and sets it's "x" and "y" if this point's "x" and "y" are smaller

    /**
     *
     * @param point
     * @return
     */
    public XY_I min(XY_I point) {
        if (point.x > x) {
            point.x = x;
        }
        if (point.y > y) {
            point.y = y;
        }
        return point;
    }

    /**
     *
     * @param _point
     * @return
     */
    public XY_I getDelta(XY_I _point) {
        return new XY_I(x - _point.x, y - _point.y);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public int hashCode() {
        return x + y;
    }

    @Override
    public boolean equals(Object instance) {
        if (!(instance instanceof XY_I)) {
            return false;
        }
        XY_I p = (XY_I) instance;
        if (p.x != x || p.y != y) {
            return false;
        }
        return true;
    }
}
