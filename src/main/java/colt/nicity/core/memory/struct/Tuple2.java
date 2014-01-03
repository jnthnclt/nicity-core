/*
 * Tuple2.java.java
 *
 * Created on 01-30-2010 09:01:54 AM
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

package colt.nicity.core.memory.struct;

/**
 *
 * @author Administrator
 * @param <A>
 * @param <B>
 */
public class Tuple2<A,B> {
    /**
     *
     */
    public A a;
    /**
     *
     */
    public B b;
    /**
     *
     * @param _a
     * @param _b
     */
    public Tuple2(A _a,B _b) {
        a = _a;
        b = _b;
    }
}
