/*
 * CSkipListSet_Test.java.java
 *
 * Created on 02-03-2010 09:44:47 PM
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
package colt.nicity.core.collection;

import colt.nicity.core.comparator.AValueComparator;
import colt.nicity.core.comparator.UValueComparator;
import colt.nicity.core.lang.ICallback;
import colt.nicity.core.lang.SysOut;
import colt.nicity.core.lang.URandom;

/**
 *
 * @author Administrator
 */
public class CSkipListSet_Test {
    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        final CSkipListSet c = new CSkipListSet(UValueComparator.value(AValueComparator.cAscending));
        for (int i = 0; i < 30; i++) {
            //c.add(URandom.randomLowerCaseAlphaString(10).intern());
            c.add(URandom.rand(30));
        }
        c.getAllInOrder(new SysOut(), new ICallback() {
            int i=0;
            @Override
            public Object callback(Object _value) {
                System.out.println(i+":"+_value);
                i++;
                return _value;
            }
        });
        System.out.println("Tail:" + c.tail.value+" Count:" + c.getCount());
        
        for (int i = 0; i < 60; i++) {
            if (URandom.rand(1d) < 0.5d) c.remove(URandom.rand(30));
            else c.add(URandom.rand(30));
        }
        c.getAllInOrder(new SysOut(), new ICallback() {
            int i=0;
            Integer lastValue = -1;
            @Override
            public Object callback(Object _value) {
                System.out.println(i+":"+_value);
                i++;
                Integer v = (Integer)_value;
                if (v < lastValue) {
                    System.out.println("!!!!!BUG!!!!!!!!");
                    c.toSysOut();
                     System.out.println("!!!!!BUG!!!!!!!!");
                    System.exit(0);
                }
                else lastValue = v;
                return _value;
            }
        });
        System.out.println("Tail:" + c.tail.value+" Count:" + c.getCount());
        c.toSysOut();
        System.exit(0);

    }
}
