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

public class LongBits {

    public static boolean hasBit(long _bits, long _mask) {
        return (_bits & _mask) == _mask;
    }

    public static long addBit(long _bits, long _mask) {
        return (_bits |= _mask);
    }

    public static long removeBit(long _bits, long _mask) {
        return (_bits &= ~_mask);
    }
    private long masks;

    public LongBits() {
    }

    public LongBits(long _masks) {
        masks = _masks;
    }

    public void addBit(long _addBit) {
        addMask(_addBit);
    }

    public void addMask(long _addMask) {
        masks |= _addMask;
    }

    public void removeBit(long _removeBit) {
        removeMask(_removeBit);
    }

    public void removeMask(long _removeMask) {
        masks &= ~_removeMask;
    }

    public void toggleMask(long _toggleMask) {
        if (hasMask(_toggleMask)) {
            removeMask(_toggleMask);
        } else {
            addMask(_toggleMask);
        }
    }

    public void replaceMask(long _removeMask, long _addMask) {
        removeMask(_removeMask);
        addMask(_addMask);
    }

    public long getMask() {
        return masks;
    }

    public boolean hasBit(long _hasBit) {
        return hasMask(_hasBit);
    }

    public boolean hasMask(long _hasMask) {
        return (masks & _hasMask) == _hasMask;
    }

    public int hashCode() {
        return (int) masks;
    }

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

    public String toString() {
        return Long.toBinaryString(masks);
    }
}

