/*
 * WH_F.java.java
 *
 * Created on 12-30-2009 09:37:00 PM
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
public class WH_F {

    /**
     *
     */
    public float w;
    /**
     *
     */
    public float h;

    /**
     *
     */
    public WH_F() {
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public WH_F(float _w, float _h) {
        h = _h;
        w = _w;
    }

    /**
     *
     * @return
     */
    public float w() {
        return w;
    }

    /**
     *
     * @return
     */
    public float h() {
        return h;
    }

    /**
     *
     * @return
     */
    public float getW() {
        return w;
    }

    /**
     *
     * @return
     */
    public float getH() {
        return h;
    }

    /**
     *
     * @param _w
     */
    public void setW(float _w) {
        w = _w;
    }

    /**
     *
     * @param _h
     */
    public void setH(float _h) {
        h = _h;
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void translate(float _w, float _h) {
        w += _w;
        h += _h;
    }

    /**
     *
     * @param _w
     * @return
     */
    public float translateW(float _w) {
        w += _w;
        return w;
    }

    /**
     *
     * @param _h
     * @return
     */
    public float translateH(float _h) {
        h += _h;
        return h;
    }

    // Takes the incoming dimension and sets it's "w" and "h" if this dimension's "w" and "h" are larger
    /**
     *
     * @param size
     * @return
     */
    public WH_F max(WH_F size) {
        if (size.w < w) {
            size.w = w;
        }
        if (size.h < h) {
            size.h = h;
        }
        return size;
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void max(float _w, float _h) {
        if (w < _w) {
            w = _w;
        }
        if (h < _h) {
            h = _h;
        }
    }

    /**
     *
     * @param _w
     * @return
     */
    public float maxW(float _w) {
        if (w < _w) {
            w = _w;
        }
        return w;
    }

    /**
     *
     * @param _h
     * @return
     */
    public float maxH(float _h) {
        if (h < _h) {
            h = _h;
        }
        return h;
    }

    // Takes the incoming dimension and sets it's "w" and "h" if this dimension's "w" and "h" are smaller
    /**
     *
     * @param size
     * @return
     */
    public WH_F min(WH_F size) {
        if (size.w > w) {
            size.w = w;
        }
        if (size.h > h) {
            size.h = h;
        }
        return size;
    }

    @Override
    public int hashCode() {
        return (int) (w + h);
    }

    @Override
    public boolean equals(Object instance) {
        if (!(instance instanceof WH_F)) {
            return false;
        }
        WH_F size = (WH_F) instance;
        if (size.w != w || size.h != h) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + w + "," + h + "]";
    }
}
