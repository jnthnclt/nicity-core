/*
 * SWH_I.java.java
 *
 * Created on 01-03-2010 09:13:00 AM
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
package com.colt.nicity.core.memory.struct;

import com.colt.nicity.core.lang.ASetObject;

public class SWH_I extends ASetObject {

    private String name;
    public int w;
    public int h;

    public SWH_I(String _name, int _w, int _h) {
        name = _name;
        w = _w;
        h = _h;
    }

    public Object hashObject() {
        return name;
    }

    @Override
    public String toString() {
        return w + "x" + h + " " + name;
    }
}
 
