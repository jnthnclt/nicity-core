/*
 * UnorderedKeys.java.java
 *
 * Created on 03-13-2010 07:40:21 AM
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
package colt.nicity.core.lang;

// !! Important
// new UnorderedKeys(new Object[]{a,b}).equals(new UnorderedKeys(new Object[]{b,a})) == true
// new UnorderedKeys(new Object[]{a,b}).hashCode() == new UnorderedKeys(new Object[]{b,a}).hashCode() == true

/**
 *
 * @author Administrator
 * @param <K>
 */
public class UnorderedKeys<K> extends ACompositeKey<K> {

    /**
     *
     * @param _keys
     */
    public UnorderedKeys(K... _keys) {
        super(_keys);
    }
    
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        if (keys == null) {
            return hashCode;
        }
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) continue;
            hashCode += keys[i].hashCode();
        }
        return hashCode;
    }
    
    @Override
    public boolean equals(Object instance) {
        if (instance == this) {
            return true;
        }
        if (!(instance instanceof UnorderedKeys)) {
            return false;
        }
        UnorderedKeys i = (UnorderedKeys) instance;
        if (keys == null) {
            return i.keys == null;
        }
        if (i.keys == null) {
            return false;
        }
        if (keys.length != i.keys.length) {
            return false;
        }
        for (int a = 0; a < keys.length; a++) {
            boolean ok = false;
            // unorder brute force equals
            for (int b = 0; b < i.keys.length; b++) {
                if (keys[a].equals(i.keys[b])) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(ACompositeKey<K> o) {
        if (!(o instanceof UnorderedKeys)) {
            return -1;
        }
        if (equals(o)) {
            return 0;
        }
        return 1;
    }
}
