/*
 * ArrayBackcall.java.java
 *
 * Created on 03-12-2010 10:39:04 PM
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
package colt.nicity.core.backcall;

import colt.nicity.core.collection.IBackcall;
import colt.nicity.core.lang.ICallback;
import colt.nicity.core.lang.IOut;

/**
 * 
 * @param <V>
 * @author Administrator
 */
public class ArrayBackcall<V> implements IBackcall<V> {

    private V[] array;

    /**
     *
     * @param _array
     */
    public ArrayBackcall(V[] _array) {
        setArray(_array);
    }

    /**
     *
     * @param _array
     */
    public void setArray(V[] _array) {
        if (_array == null) throw new RuntimeException("null not supported");
        array = _array;
    }

    /**
     *
     * @param _
     * @param _callback
     */
    @Override
    public void backcall(IOut _, ICallback<V, V> _callback) {
        V[] _array = array;
        for (int i = 0; i < _array.length; i++) {
            _.out(i, _array.length);
            _callback.callback(_array[i]);
        }

    }
}
