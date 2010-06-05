/*
 * SysOut.java.java
 *
 * Created on 12-28-2009 08:40:00 PM
 *
 * Copyright 2009 Jonathan Colt
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

public class SysOut implements IOut {
    public boolean canceled() {
        return false;
    }
    public void out(double _at, double _outof) {
        System.out.println(Thread.currentThread() + " progress " + ((_at / _outof) * 100d) + "%");
    }
    public void out(Object... _status) {
        for (Object s : _status) {
            if (s instanceof Throwable) {
                ((Throwable)s).printStackTrace();
            }
            else {
                System.out.println(s+" "+Thread.currentThread());
            }
        }
    }
}
