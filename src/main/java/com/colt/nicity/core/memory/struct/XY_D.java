/*
 * XY_D.java.java
 *
 * Created on 03-12-2010 11:25:57 PM
 *
 * Copyright 2010 Jonathan Colt
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
public class XY_D {

    /**
     *
     */
    public double x;
    /**
     *
     */
    public double y;

    /**
     *
     */
    public XY_D() {
    }

    /**
     *
     * @param _xy_d
     */
    public XY_D(XY_D _xy_d) {
        x = _xy_d.x;
        y = _xy_d.y;
    }

    /**
     *
     * @param _x
     * @param _y
     */
    public XY_D(double _x, double _y) {
        x = _x;
        y = _y;
    }

    /**
     *
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @param _x
     */
    public void setX(double _x) {
        x = _x;
    }

    /**
     *
     * @param _y
     */
    public void setY(double _y) {
        y = _y;
    }

    /**
     *
     * @param _x
     * @param _y
     */
    public void setXY(double _x, double _y) {
        x = _x;
        y = _y;
    }

    @Override
    public int hashCode() {
        return (int) (x + y);
    }

    @Override
    public boolean equals(Object instance) {
        if (!(instance instanceof XY_D)) {
            return false;
        }
        XY_D p = (XY_D) instance;
        if (p.x != x || p.y != y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "[" + x + "," + y + "]";
    }
}
