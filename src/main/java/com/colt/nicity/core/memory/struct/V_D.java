/*
 * V_D.java.java
 *
 * Created on 03-12-2010 11:25:34 PM
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

public class V_D {

    private double v;

    public V_D() {
    }

    public V_D(V_D _v_d) {
        v = _v_d.v;
    }

    public V_D(double _v) {
        v = _v;
    }

    public double getV() {
        return v;
    }

    public void setV(double _v) {
        v = _v;
    }

    @Override
    public int hashCode() {
        return (int) (v);
    }

    @Override
    public boolean equals(Object instance) {
        if (!(instance instanceof V_D)) {
            return false;
        }
        V_D p = (V_D) instance;
        if (p.v != v) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "[" + v + "]";
    }
}
