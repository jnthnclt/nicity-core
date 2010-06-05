/*
 * Value.java.java
 *
 * Created on 03-12-2010 06:30:27 PM
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
package com.colt.nicity.core.value;

import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.collection.keyed.KeyedValue;
import com.colt.nicity.core.io.IFiler;
import com.colt.nicity.core.io.UIO;
import com.colt.nicity.core.lang.ASetObject;
import com.colt.nicity.core.lang.IBA;
import com.colt.nicity.core.lang.ICallback;
import com.colt.nicity.core.lang.OrderedKeys;
import com.colt.nicity.core.observer.AObservable;
import com.colt.nicity.core.time.UTime;
import java.util.HashSet;

/**
 *
 * @author Administrator
 * @param <V>
 */
public class Value<V> extends AObservable implements IValue<V>, ICallback<V,V> {

    /**
     *
     */
    protected V value;

    /**
     *
     */
    public Value() {
    }

    /**
     *
     * @param _value
     */
    public Value(V _value) {
        value = _value;
    }

    /**
     *
     * @return
     */
    @Override
    public Object hashObject() {
        if (value instanceof ASetObject) {
            return ((ASetObject) value).hashObject();
        }
        return value;
    }

    /**
     *
     * @return
     */
    public boolean isNull() {
        return value == null;
    }

    /**
     *
     * @param _f
     * @throws Exception
     */
    final public void saveToFiler(IFiler _f) throws Exception {
        saveValueToFiler(value, _f);
    }

    static final void saveValueToFiler(Object _value, IFiler _f) throws Exception {
        if (_value == null) {
            UIO.writeByte(_f, 0, "type");
            return;
        }
        if (_value instanceof String) {
            UIO.writeByte(_f, 1, "type");
            UIO.writeString(_f, (String) _value, "string");
            return;
        }
        if (_value instanceof Long) {
            UIO.writeByte(_f, 2, "type");
            UIO.writeLong(_f, ((Long) _value).longValue(), "long");
            return;
        }
        if (_value instanceof Double) {
            UIO.writeByte(_f, 3, "type");
            UIO.writeDouble(_f, ((Double) _value).doubleValue(), "double");
            return;
        }
        if (_value instanceof Float) {
            UIO.writeByte(_f, 4, "type");
            UIO.writeFloat(_f, ((Float) _value).floatValue(), "float");
            return;
        }
        if (_value instanceof Integer) {
            UIO.writeByte(_f, 5, "type");
            UIO.writeInt(_f, ((Integer) _value).intValue(), "int");
            return;
        }
        if (_value instanceof Short) {
            UIO.writeByte(_f, 6, "type");
            UIO.writeShort(_f, ((Short) _value).shortValue(), "short");
            return;
        }
        if (_value instanceof Character) {
            UIO.writeByte(_f, 7, "type");
            UIO.writeChar(_f, ((Character) _value).charValue(), "char");
            return;
        }
        if (_value instanceof Byte) {
            UIO.writeByte(_f, 8, "type");
            UIO.writeByte(_f, ((Byte) _value).byteValue(), "byte");
            return;
        }
        if (_value instanceof Boolean) {
            UIO.writeByte(_f, 9, "type");
            UIO.writeBoolean(_f, ((Boolean) _value).booleanValue(), "boolean");
            return;
        }

        if (_value instanceof String[]) {
            UIO.writeByte(_f, 10, "type");
            UIO.writeStringArray(_f, (String[]) _value, "stringArray");
            return;
        }
        if (_value instanceof long[]) {
            UIO.writeByte(_f, 11, "type");
            UIO.writeLongArray(_f, (long[]) _value, "longArray");
            return;
        }
        if (_value instanceof double[]) {
            UIO.writeByte(_f, 12, "type");
            UIO.writeDoubleArray(_f, (double[]) _value, "doubleArray");
            return;
        }
        if (_value instanceof float[]) {
            UIO.writeByte(_f, 13, "type");
            UIO.writeFloatArray(_f, (float[]) _value, "floatArray");
            return;
        }
        if (_value instanceof int[]) {
            UIO.writeByte(_f, 14, "type");
            UIO.writeIntArray(_f, (int[]) _value, "intArray");
            return;
        }
        if (_value instanceof short[]) {
            UIO.writeByte(_f, 15, "type");
            UIO.writeShortArray(_f, (short[]) _value, "shortArray");
            return;
        }
        if (_value instanceof char[]) {
            UIO.writeByte(_f, 16, "type");
            UIO.writeCharArray(_f, (char[]) _value, "charArray");
            return;
        }
        if (_value instanceof byte[]) {
            UIO.writeByte(_f, 17, "type");
            UIO.writeByteArray(_f, (byte[]) _value, "byteArray");
            return;
        }
        if (_value instanceof boolean[]) {
            UIO.writeByte(_f, 18, "type");
            UIO.writeBooleanArray(_f, (boolean[]) _value, "booleanArray");
            return;
        }


        if (_value instanceof IBA) {
            UIO.writeByte(_f, 19, "type");
            UIO.writeByteArray(_f, ((IBA)_value).immutableBytes(), "iba");
            return;
        }
        if (_value instanceof IBA[]) {
            UIO.writeByte(_f, 20, "type");
            IBA[] ibas = (IBA[]) _value;
            UIO.writeInt(_f, ibas.length, "Count");
            for (int i = 0; i < ibas.length; i++) {
                UIO.writeByteArray(_f, ((IBA)ibas[i]).immutableBytes(), "iba");
            }
            return;
        }
        if (_value instanceof Value) {
            UIO.writeByte(_f, 21, "type");
            Value v = (Value) _value;
            saveValueToFiler(v.value, _f);
            return;
        }
        if (_value instanceof OrderedKeys) {
            UIO.writeByte(_f, 22, "type");
            Object[] array = ((OrderedKeys) _value).getKeys();
            UIO.writeInt(_f, array.length, "Count");
            for (int i = 0; i < array.length; i++) {
                saveValueToFiler(array[i], _f);
            }
            return;
        }
        if (_value instanceof HashSet) {
            UIO.writeByte(_f, 23, "type");
            Object[] array = ((CSet) _value).getAll(Object.class);
            UIO.writeInt(_f, array.length, "Count");
            for (int i = 0; i < array.length; i++) {
                saveValueToFiler(array[i], _f);
            }
            return;
        }
        if (_value instanceof KeyedValue) {
            KeyedValue kv = (KeyedValue) _value;
            UIO.writeByte(_f, 24, "type");
            saveValueToFiler(kv.key(), _f);
            saveValueToFiler(kv.value(), _f);
            return;
        }
        if (_value instanceof CArray) {
            UIO.writeByte(_f, 25, "type");
            Object[] array = ((CArray) _value).getAll();
            UIO.writeInt(_f, array.length, "Count");
            for (int i = 0; i < array.length; i++) {
                saveValueToFiler(array[i], _f);
            }
            return;
        }
    }

    /**
     *
     * @param _f
     * @throws Exception
     */
    final public void loadFromFiler(IFiler _f) throws Exception {
        value = (V)loadValueFromFiler(_f);
    }

    static final Object loadValueFromFiler(IFiler _f) throws Exception {
        byte t = UIO.readByte(_f, "type");
        if (t == 0) {
            return null;
        } else if (t == 1) {
            return UIO.readString(_f, "string");
        } else if (t == 2) {
            return UIO.readLong(_f, "long");
        } else if (t == 3) {
            return UIO.readDouble(_f, "double");
        } else if (t == 4) {
            return UIO.readFloat(_f, "float");
        } else if (t == 5) {
            return UIO.readInt(_f, "int");
        } else if (t == 6) {
            return UIO.readShort(_f, "short");
        } else if (t == 7) {
            return UIO.readChar(_f, "char");
        } else if (t == 8) {
            return UIO.readByte(_f, "byte");
        } else if (t == 9) {
            return UIO.readBoolean(_f, "boolean");
        } else if (t == 10) {
            return UIO.readStringArray(_f, "stringArray");
        } else if (t == 11) {
            return UIO.readLongArray(_f, "longArray");
        } else if (t == 12) {
            return UIO.readDoubleArray(_f, "doubleArray");
        } else if (t == 13) {
            return UIO.readFloatArray(_f, "floatArray");
        } else if (t == 14) {
            return UIO.readIntArray(_f, "intArray");
        } else if (t == 15) {
            return UIO.readShortArray(_f, "shortArray");
        } else if (t == 16) {
            return UIO.readCharArray(_f, "charArray");
        } else if (t == 17) {
            return UIO.readByteArray(_f, "byteArray");
        } else if (t == 18) {
            return UIO.readBooleanArray(_f, "booleanArray");
        } else if (t == 19) {
            return new IBA(UIO.readByteArray(_f,"iba"));
        } else if (t == 20) {
            IBA[] ibas = new IBA[UIO.readInt(_f, "Count")];
            for (int i = 0; i < ibas.length; i++) {
                ibas[i] = new IBA(UIO.readByteArray(_f,"iba"));
            }
            return ibas;
        } else if (t == 21) {
            return new Value(loadValueFromFiler(_f));
        } else if (t == 22) {
            Object[] array = new Object[UIO.readInt(_f, "Count")];
            for (int i = 0; i < array.length; i++) {
                array[i] = loadValueFromFiler(_f);
            }
            return new OrderedKeys(array);
        } else if (t == 23) {
            Object[] array = new Object[UIO.readInt(_f, "Count")];
            for (int i = 0; i < array.length; i++) {
                array[i] = loadValueFromFiler(_f);
            }
            return new CSet(array);
        } else if (t == 24) {
            return new KeyedValue(loadValueFromFiler(_f), loadValueFromFiler(_f));
        } else if (t == 25) {
            Object[] array = new Object[UIO.readInt(_f, "Count")];
            for (int i = 0; i < array.length; i++) {
                array[i] = loadValueFromFiler(_f);
            }
            return new CArray(array);
        } else {
            throw new Exception("Unsupported Value Type:" + t);
        }
    }

    /**
     *
     * @return
     */
    final public boolean booleanValue() {
        return ((Boolean) getValue()).booleanValue();
    }

    /**
     *
     * @return
     */
    final public byte byteValue() {
        return ((Byte) getValue()).byteValue();
    }

    /**
     *
     * @return
     */
    final public char charValue() {
        return ((Character) getValue()).charValue();
    }

    /**
     *
     * @return
     */
    final public int intValue() {
        return ((Integer) getValue()).intValue();
    }

    /**
     *
     * @return
     */
    final public float floatValue() {
        return ((Float) getValue()).floatValue();
    }

    /**
     *
     * @return
     */
    final public long longValue() {
        return ((Long) getValue()).longValue();
    }

    /**
     *
     * @return
     */
    final public double doubleValue() {
        return ((Double) getValue()).doubleValue();
    }

    /**
     *
     * @return
     */
    final public String stringValue() {
        return (String) getValue();
    }

    /**
     *
     * @return
     */
    @Override
    public V getValue() {
        return value();
    }

    /**
     *
     * @param _value
     */
    @Override
    public void setValue(V _value) {
        value(_value);
        if (isBeingObserved()) {
            change("Set", _value);
        }
    }

    /**
     *
     * @param _amount
     */
    public void inc(Object _amount) {
        if (_amount instanceof Byte) {
            setValue((V)new Byte((byte)(byteValue() + (Byte) _amount)));
        }
        if (_amount instanceof Character) {
            setValue((V)new Character((char)(charValue() + (Character) _amount)));
        }
        if (_amount instanceof Integer) {
            setValue((V)new Integer(intValue() + (Integer) _amount));
        }
        if (_amount instanceof Float) {
            setValue((V)new Float(floatValue() + (Float) _amount));
        }
        if (_amount instanceof Long) {
            setValue((V)new Long(longValue() + (Long) _amount));
        }
        if (_amount instanceof Double) {
            setValue((V)new Double(doubleValue() + (Double) _amount));
        }
    }

    /**
     *
     * @param _value
     */
    final public void value(byte _value) {
        value(new Byte(_value));
    }

    /**
     *
     * @param _value
     */
    final public void value(char _value) {
        value(new Character(_value));
    }

    /**
     *
     * @param _value
     */
    final public void value(int _value) {
        value(new Integer(_value));
    }

    /**
     *
     * @param _value
     */
    final public void value(float _value) {
        value(new Float(_value));
    }

    /**
     *
     * @param _value
     */
    final public void value(long _value) {
        value(new Long(_value));
    }

    /**
     *
     * @param _value
     */
    final public void value(double _value) {
        value(new Double(_value));
    }

    /**
     *
     * @param _value
     */
    final public void value(V _value) {
        value = _value;
    }

    /**
     *
     * @return
     */
    final public V value() {
        return value;
    }

    // ICallback
    /**
     *
     * @param _value
     * @return
     */
    @Override
    public V callback(V _value) {
        _value = value;
        value = null;
        return _value;
    }

    @Override
    public String toString() {
        return (value == null || value == this) ? "" : value.toString();
    }

    /**
     *
     * @return
     */
    public Object getTimeToString() {
        return new Object() {

            @Override
            public String toString() {
                return UTime.basicTime(longValue());
            }
        };
    }

    /**
     *
     * @return
     */
    public Object getDateToString() {
        return new Object() {

            @Override
            public String toString() {
                return UTime.fixedWidthDate(longValue());
            }
        };
    }

    /**
     *
     * @return
     */
    public Object getCountToString() {
        return new Object() {

            @Override
            public String toString() {
                return "(" + Value.this.toString() + ")";
            }
        };
    }
}


