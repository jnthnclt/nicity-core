/*
 * Tuple3.java.java
 *
 * Created on 03-12-2010 11:25:18 PM
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
 * @param <C>
 */
public class Tuple3<A, B, C> {

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
     */
    public C c;

    /**
     *
     * @param _a
     * @param _b
     * @param _c
     */
    public Tuple3(A _a, B _b, C _c) {
        a = _a;
        b = _b;
        c = _c;
    }
}
