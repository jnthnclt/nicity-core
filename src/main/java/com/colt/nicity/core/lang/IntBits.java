/*
 * IntBits.java.java
 *
 * Created on 03-12-2010 11:23:09 PM
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

public class IntBits {

    private int masks;

    public IntBits() {
    }

    public IntBits(int _masks) {
        masks = _masks;
    }
    public void setMask(int _masks) {
        masks = _masks;
    }

    public void addBit(int _addBit) {
        addMask(_addBit);
    }

    public void addMask(int _addMask) {
        masks |= _addMask;
    }

    public void removeBit(int _removeBit) {
        removeMask(_removeBit);
    }

    public void removeMask(int _removeMask) {
        masks &= ~_removeMask;
    }

    public void toggleMask(int _toggleMask) {
        if (hasMask(_toggleMask)) {
            removeMask(_toggleMask);
        }
        else {
            addMask(_toggleMask);
        }
    }

    public void replaceMask(int _removeMask, int _addMask) {
        removeMask(_removeMask);
        addMask(_addMask);
    }

    public int getMask() {
        return masks;
    }

    public boolean hasBit(int _hasBit) {
        return hasMask(_hasBit);
    }

    public boolean hasMask(int _hasMask) {
        return (masks & _hasMask) == _hasMask;
    }

    @Override
    public int hashCode() {
        return masks;
    }

    @Override
    public boolean equals(Object _instance) {
        if (_instance == this) {
            return true;
        }
        if (_instance instanceof IntBits) {
            IntBits ibm = (IntBits) _instance;
            return (ibm.masks == masks);
        }
        if (_instance instanceof Integer) {
            return masks == (Integer) _instance;
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toBinaryString(masks);
    }
}
