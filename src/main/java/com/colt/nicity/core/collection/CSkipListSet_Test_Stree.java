/*
 * CSkipListSet_Test_Stree.java.java
 *
 * Created on 02-03-2010 09:52:54 PM
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
package com.colt.nicity.core.collection;

import com.colt.nicity.core.comparator.AValueComparator;
import com.colt.nicity.core.comparator.UValueComparator;
import com.colt.nicity.core.lang.URandom;
import com.colt.nicity.core.time.MilliTimer;

/**
 *
 * @author Administrator
 */
public class CSkipListSet_Test_Stree {
    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        final CSkipListSet c = new CSkipListSet(UValueComparator.value(AValueComparator.cAscending));
        int adds = 100000;
        MilliTimer t = new MilliTimer();
        t.start();
        for (int i = 0; i < adds; i++) {
            //c.add(URandom.randomLowerCaseAlphaString(10).intern());
            c.add(URandom.rand(adds));
        }
        t.stop();
        System.out.print("Adds:"+t.stats()+" rate="+((double)adds/((double)t.duration()/1000d)));
        System.out.println(" Tail:" + c.tail.value+" Count:" + c.getCount());

        t = new MilliTimer();
        t.start();
        for (int i = 0; i < adds; i++) {
            c.remove(URandom.rand(adds));
        }
        t.stop();
        System.out.print("Removes:"+t.stats()+" rate="+((double)adds/((double)t.duration()/1000d)));
        System.out.println(" Tail:" + c.tail.value+" Count:" + c.getCount());

        t = new MilliTimer();
        t.start();
        for (int i = 0; i < adds; i++) {
            if (URandom.rand(1d) < 0.5d) c.remove(URandom.rand(adds));
            else c.add(URandom.rand(adds));
        }
         t.stop();
        System.out.print("Add/Remove:"+t.stats()+" rate="+((double)adds/((double)t.duration()/1000d)));
        System.out.println(" Tail:" + c.tail.value+" Count:" + c.getCount());
        //c.toSysOut();
        System.exit(0);

    }
}
