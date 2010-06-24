/*
 * KV.java.java
 *
 * Created on 03-12-2010 09:02:40 PM
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

package colt.nicity.core.value;

/**
 *
 * @author Administrator
 * @param <K>
 * @param <V>
 */
public class KV<K,V> extends Value<V> {
    private K key;
    /**
     *
     * @param _key
     * @param _value
     */
    public KV(K _key,V _value) {
        super(_value);
        key = _key;
    }
    /**
     *
     * @return
     */
    public K key() { return key; }
    /**
     *
     * @return
     */
    @Override
    public Object hashObject() {
        return key;
    }
}
