/*
 * UBackcall.java.java
 *
 * Created on 03-12-2010 10:38:53 PM
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
package com.colt.nicity.core.backcall;

import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.core.collection.IBackcall;
import com.colt.nicity.core.lang.ICallback;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.MutableLong;
import java.lang.reflect.Array;

public class UBackcall {

    public static <V> long getCount(final IOut _, IBackcall<V> _backcall) {
        if (_backcall == null) {
            return -1;
        }
        final MutableLong total = new MutableLong();
        ICallback<V,V> callback = new ICallback<V,V>() {
            public V callback(V _value) {
                total.inc();
                return _value;
            }
        };
        _backcall.backcall(_, callback);
        return total.longValue();
    }

    public static <V> V[] toArray(IOut _, IBackcall<V> _backcall,
        final Class< ? extends V> _class) {
        if (_backcall == null) {
            return (V[]) Array.newInstance(_class, 0);
        }
        final CArray<V> array = new CArray<V>(_class);
        ICallback<V,V> callback = new ICallback<V,V>() {

            public V callback(V _value) {
                if (_class.isInstance(_value)) {
                    array.insertLast(_value);
                }
                return _value;
            }
        };
        _backcall.backcall(_, callback);
        return array.getAll();
    }
}


