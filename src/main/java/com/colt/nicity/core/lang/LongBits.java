/*
 * LongBits.java.java
 *
 * Created on 01-03-2010 11:16:00 AM
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
package com.colt.nicity.core.lang;

/**
 *
 * @author Administrator
 */
public class LongBits {

    /**
     *
     * @param _bits
     * @param _mask
     * @return
     */
    public static boolean hasBit(long _bits, long _mask) {
        return (_bits & _mask) == _mask;
    }

    /**
     *
     * @param _bits
     * @param _mask
     * @return
     */
    public static long addBit(long _bits, long _mask) {
        return (_bits |= _mask);
    }

    /**
     *
     * @param _bits
     * @param _mask
     * @return
     */
    public static long removeBit(long _bits, long _mask) {
        return (_bits &= ~_mask);
    }
    private long masks;

    /**
     *
     */
    public LongBits() {
    }

    /**
     *
     * @param _masks
     */
    public LongBits(long _masks) {
        masks = _masks;
    }

    /**
     *
     * @param _addBit
     */
    public void addBit(long _addBit) {
        addMask(_addBit);
    }

    /**
     *
     * @param _addMask
     */
    public void addMask(long _addMask) {
        masks |= _addMask;
    }

    /**
     *
     * @param _removeBit
     */
    public void removeBit(long _removeBit) {
        removeMask(_removeBit);
    }

    /**
     *
     * @param _removeMask
     */
    public void removeMask(long _removeMask) {
        masks &= ~_removeMask;
    }

    /**
     *
     * @param _toggleMask
     */
    public void toggleMask(long _toggleMask) {
        if (hasMask(_toggleMask)) {
            removeMask(_toggleMask);
        } else {
            addMask(_toggleMask);
        }
    }

    /**
     *
     * @param _removeMask
     * @param _addMask
     */
    public void replaceMask(long _removeMask, long _addMask) {
        removeMask(_removeMask);
        addMask(_addMask);
    }

    /**
     *
     * @return
     */
    public long getMask() {
        return masks;
    }

    /**
     *
     * @param _hasBit
     * @return
     */
    public boolean hasBit(long _hasBit) {
        return hasMask(_hasBit);
    }

    /**
     *
     * @param _hasMask
     * @return
     */
    public boolean hasMask(long _hasMask) {
        return (masks & _hasMask) == _hasMask;
    }

    @Override
    public int hashCode() {
        return (int) masks;
    }

    @Override
    public boolean equals(Object _instance) {
        if (_instance == this) {
            return true;
        }
        if (_instance instanceof LongBits) {
            LongBits ibm = (LongBits) _instance;
            return (ibm.masks == masks);
        }
        if (_instance instanceof Long) {
            return masks == (Long) _instance;
        }
        return false;
    }

    @Override
    public String toString() {
        return Long.toBinaryString(masks);
    }
}

