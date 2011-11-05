/*
 * KVA.java.java
 *
 * Created on 03-12-2010 06:29:57 PM
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

import colt.nicity.core.collection.CArray;
import colt.nicity.core.process.IAsyncResponse;

/**
 *
 * @author Administrator
 */
public class KVA<K> extends KV<K,CArray> {
    /**
     *
     * @param _key
     */
    public KVA(K _key) {
        super(_key,new CArray(KV.class));
    }
    /**
     *
     * @param _index
     * @param _kvs
     */
    public void add(int _index,KV... _kvs) {

    }
    /**
     *
     * @param _index
     * @param _kvs
     */
    public void remove(int _index,KV... _kvs) {

    }
    /**
     *
     * @param _index
     * @param _kv
     * @param _took
     */
    public void take(int _index,KV _kv,IAsyncResponse<KV> _took) {

    }
    /**
     *
     * @param _index
     * @param _kv
     * @param _copied
     */
    public void copy(int _index,KV _kv,IAsyncResponse<KV> _copied) {
        
    }
}
