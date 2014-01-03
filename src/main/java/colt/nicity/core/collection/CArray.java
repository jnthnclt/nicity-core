/*
 * CArray.java.java
 *
 * Created on 03-12-2010 10:47:28 PM
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

import colt.nicity.core.comparator.IComparator;
import colt.nicity.core.lang.ICallback;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.lang.UArray;
import colt.nicity.core.observer.AObservable;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 * @author Administrator
 * @param <E>
 */
public class CArray<E> extends AObservable implements IBackcall<E>, Cloneable {

    /**
     *
     */
    protected Class contentClass = Object.class;
    /**
     *
     */
    protected int count = 0;
    /**
     *
     */
    protected E[] objects;//!! performance hack should be private
    /**
     *
     */
    protected int growth = 30;//!! might should implement a 10% growth model
    // Contructors

    /**
     *
     */
    public CArray() {
        init(contentClass, 0, growth);
    }

    /**
     *
     * @param size
     */
    public CArray(int size) {
        init(contentClass, size, growth);
    }

    /**
     *
     * @param size
     * @param growth
     */
    public CArray(int size, int growth) {
        init(contentClass, size, growth);
    }

    /**
     *
     * @param contentClass
     */
    public CArray(Class<? extends E> contentClass) {
        init(contentClass, 0, growth);
    }

    /**
     *
     * @param contentClass
     * @param size
     */
    public CArray(Class<? extends E> contentClass, int size) {
        init(contentClass, size, growth);
    }

    /**
     *
     * @param contentClass
     * @param size
     * @param growth
     */
    public CArray(Class<? extends E> contentClass, int size, int growth) {
        init(contentClass, size, growth);
    }

    /**
     *
     * @param _objects
     */
    public CArray(E[] _objects) {
        objects = _objects;
        contentClass = objects.getClass();
        count = objects.length;
    }

    private void init(Class<? extends E> _contentClass, int _size, int _growth) {
        if (_contentClass == null) {
            throw new RuntimeException("Bad Argument");
        }
        contentClass = _contentClass;
        objects = (E[]) Array.newInstance(
            _contentClass, _size);
        growth = _growth;
    }

    /**
     *
     * @param _comparator
     */
    public synchronized void sort(IComparator _comparator) {
        Arrays.sort(objects, 0, getCount(), _comparator);
    }

    /**
     *
     * @param _values
     * @return
     */
    synchronized public Object[] removeAt(Object[] _values) {
        if (_values != null) {
            for (int i = 0; i < _values.length; i++) {
                removeAt(_values[i]);
            }
        }
        return null;
    }

    /**
     *
     * @param _key
     * @return
     */
    public E getAt(Object _key) {
        int i = getIndex(_key);
        if (i == -1) {
            return null;
        }
        return getAt(i);
    }

    /**
     *
     * @param keys
     * @return
     */
    public E[] getAt(Object[] keys) {
        E[] got = (E[]) Array.newInstance(contentClass, keys.length);
        for (int i = 0; i < keys.length; i++) {
            got[i] = getAt(keys[i]);
        }
        return got;
    }
    //StackClass Classs

    /**
     * 
     * @param newContentClass
     */
    synchronized public void setContentClass(Class newContentClass) {
        if (newContentClass == null) {
            return; // doesn't modify data so I don't fire a Task
        }
        if (isBeingObserved()) {
            Object[] removedValues = (Object[]) Array.newInstance(contentClass,
                count);
            System.arraycopy(objects, 0, removedValues, 0, count);
            contentClass = newContentClass;
            objects = (E[]) Array.newInstance(this.contentClass, 0);
            count = 0;
            change("Write", null);
        }
        contentClass = newContentClass;
        objects = (E[]) Array.newInstance(contentClass, 0);
        count = 0;
    }

    @Override
    public String toString() {
        return super.toString() + " Count:" + count + " Size:" + getSize();
    }
    // Cloneable

    @Override
    synchronized public Object clone() {
        try {
            CArray<E> clone = (CArray<E>) super.clone();
            clone.objects = (E[]) Array.newInstance(contentClass, count);
            System.arraycopy(objects, 0, clone.objects, 0, count);
            return clone;
        }
        catch (CloneNotSupportedException x) {
            throw new InternalError();
        }
    }

    /**
     *
     * @return
     */
    synchronized public Class getContentClass() {
        return contentClass;
    }

    /**
     *
     * @return
     */
    synchronized public int getSize() {
        return objects.length;
    }

    /**
     *
     * @param suggestion
     * @return
     */
    synchronized public int setSize(int suggestion) {
        if (suggestion < count) { //shrink
            E[] array = (E[]) Array.newInstance(
                contentClass, count);
            System.arraycopy(objects, 0, array, 0, count);
            objects = array;
        }
        else { //grow
            E[] array = (E[]) Array.newInstance(
                contentClass, suggestion);
            System.arraycopy(objects, 0, array, 0, count);
            objects = array;
        }
        return objects.length;
    }

    /**
     *
     * @return
     */
    synchronized public int getCount() {
        return count;
    }

    /**
     *
     * @return
     */
    synchronized public E[] getAll() {
        Object[] gotValues = (contentClass == Object.class) ? new Object[count] : (Object[]) Array.newInstance(
            contentClass, count);
        System.arraycopy(objects, 0, gotValues, 0, count);
        if (isBeingObserved()) {
            change("Read", null);
        }
        return (E[]) gotValues;
    }

    /**
     *
     * @return
     */
    synchronized public Object[] removeAll() {
        Object[] removedObjects = _removeAll();
        if (isBeingObserved()) {
            change("Write", null);
        }
        return removedObjects;
    }

    private Object[] _removeAll() {
        Object[] gotValues = (contentClass == Object.class) ? new Object[count] : (Object[]) Array.newInstance(
            contentClass, count);
        System.arraycopy(objects, 0, gotValues, 0, count);
        count = 0;
        objects = (E[]) Array.newInstance(
            contentClass, 0);
        return gotValues;
    }

    /**
     *
     * @param _replace
     * @return
     */
    synchronized public Object[] replaceAll(E[] _replace) {
        Object[] removedObjects = _removeAll();
        insertLast(_replace);
        return removedObjects;
    }
    // Factored out static helpers
    // Reminder ensureCapacity sets count to newCount so get a localStack copy
    // if you need to retain count.

    synchronized private final void ensureCapacity(int newCount) {
        if (objects.length < newCount) {
            int grow = (int) (newCount * 0.5);//!! 50% growth padding
            E[] newArray = (E[]) Array.newInstance(
                contentClass, newCount + grow);
            System.arraycopy(objects, 0, newArray, 0, count);
            objects = newArray;
        }
        count = newCount;
    }

    synchronized private final void preventOverCapacity() {
        if ((objects.length - count) < growth) {//!! flip < and consider usage
            E[] newArray = (E[]) Array.newInstance(
                contentClass, count + growth);
            System.arraycopy(objects, 0, newArray, 0, count);
            objects = newArray;
        }
    }
    // IIndexedData
    // Indexing

    /**
     *
     * @param value
     * @return
     */
    synchronized public int getIndex(Object value) {
        if (count == 0) {
            return -1;
        }
        if (value == null) {
            for (int i = 0; i < count; i++) {
                if (objects[i] == null) {
                    return i;
                }
            }
        }
        else {
            int valueHash = value.hashCode();
            for (int i = 0; i < count; i++) {
                Object o = objects[i];
                if (o == value) {
                    return i;
                }
                if (o.hashCode() == valueHash && o.equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     *
     * @param value
     * @return
     */
    synchronized public int[] getIndexes(Object value) {
        if (count == 0) {
            return null;
        }

        int indexesCount = 0;
        int[] indexes = new int[count];

        if (value == null) {
            for (int i = 0; i < count; i++) {
                if (objects[i] == null) {
                    indexes[indexesCount++] = i;
                }
            }
        }
        else {
            for (int i = 0; i < count; i++) {
                if (value == objects[i] && value.equals(objects[i])) {
                    indexes[indexesCount++] = i;
                }
            }
        }

        if (indexesCount < indexes.length) {
            indexes = UArray.trim(indexes, indexesCount);
        }
        return indexes;
    }
    // Getting
    // return null if count == 0 else return objects[0]

    /**
     *
     * @return
     */
    synchronized public E getFirst() { // peekShift();
        Object gotValue = (count > 0) ? objects[0] : null;
        if (isBeingObserved()) {
            change("Read", null);
        }
        return (E) gotValue;
    }
    // return null if count == 0 else return objects[count-1]

    /**
     *
     * @return
     */
    synchronized public E getLast() { // peekPop();
        Object gotValue = (count > 0) ? objects[count - 1] : null;
        if (isBeingObserved()) {
            change("Read", null);
        }
        return (E) gotValue;
    }
    // If index < 0 throws RuntimeException!
    // If index > count CArray grows to accomedate index!

    /**
     *
     * @param index
     * @return
     */
    synchronized public E getAt(int index) {
        if (index >= count) {
            return null;
        }
        if (index < 0) {
            throw new RuntimeException("Index Out of Bounds");
        }

        Object gotValue = (count != 0) ? objects[index] : null;
        if (isBeingObserved()) {
            change("Read", null);
        }
        return (E) gotValue;
    }

    /**
     *
     * @param fromIndex
     * @param toIndex
     * @return
     */
    synchronized public Object[] getFromTo(int fromIndex, int toIndex) {
        if (count == 0 || fromIndex < 0 || fromIndex > toIndex || toIndex >= count) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (fromIndex == toIndex) {
            return (contentClass == Object.class) ? new Object[0] : (Object[]) Array.newInstance(
                contentClass, 0); // doesn't modify data so I don't fire a Task
        }
        int len = toIndex - fromIndex + 1;

        Object[] gotValues = (contentClass == Object.class) ? new Object[len] : (Object[]) Array.newInstance(
            contentClass, len);
        System.arraycopy(objects, fromIndex, gotValues, 0, len);
        if (isBeingObserved()) {
            change("Read", null);
        }
        return gotValues;
    }
    // Removing

    /**
     *
     * @param _remove
     */
    public void remove(CArray _remove) {
        remove(_remove.getAll());
    }

    /**
     *
     * @param _remove
     */
    synchronized public void remove(Object[] _remove) {
        CSet<E> set = new CSet<E>(count);
        set.add(objects);
        set.remove(_remove);
        objects = set.getAll(Object.class);
        count = (int) set.getCount();
        if (isBeingObserved()) {
            change("Write", null);
        }
    }

    /**
     *
     * @return
     */
    synchronized public E removeFirst() { //unshift
        if (count == 0) {
            return null; // doesn't modify data so I don't fire a Task
        }
        Object removedValue = objects[0];
        count--;
        System.arraycopy(objects, 1, objects, 0, count);
        //preventOverCapacity();
        if (isBeingObserved()) {
            change("Write", null);
        }
        return (E) removedValue;
    }

    /**
     *
     * @return
     */
    synchronized public E removeLast() { //pop
        if (count == 0) {
            return null; // doesn't modify data so I don't fire a Task
        }
        count--;
        Object removedValue = objects[count];
        System.arraycopy(objects, 0, objects, 0, count);
        //preventOverCapacity();
        if (isBeingObserved()) {
            change("Write", null);
        }
        return (E) removedValue;
    }
    // If index < 0 || index >= count  throws RuntimeException

    /**
     *
     * @param index
     * @return
     */
    synchronized public E removeAt(int index) {
        if (count == 0) {
            return null; // doesn't modify data so I don't fire a Task
        }
        if (index < 0 || index >= count) {
            throw new RuntimeException("Index Out of Bounds");
        }
        Object removedValue = objects[index];
        count--;
        for (int i = index; i < count; i++) {
            objects[i] = objects[i + 1];
        }
        objects[count] = null;
        //preventOverCapacity();
        if (isBeingObserved()) {
            change("Write", null);
        }
        return (E) removedValue;
    }
    // No synchronized because removeAt(int index) handles synchronized

    /**
     *
     * @param key
     * @return
     */
    synchronized public E removeAt(Object key) {
        if (key instanceof Integer) {
            return (E) removeAt(((Integer) key).intValue());
        }
        for (int i = 0; i < count; i++) {
            if (objects[i] == key || objects[i].equals(key)) {
                return (E) removeAt(i);
            }
        }
        return null;
    }
    // If fromIndex < 0 throws RuntimeException!
    // If fromIndex > toIndex throws RuntimeException!
    // If fromIndex > count || toIndex > count CArray grows to accomedate!
    // If fromIndex == toIndex  return null;

    /**
     *
     * @param fromIndex
     * @param toIndex
     * @return
     */
    synchronized public Object[] removeFromTo(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex > toIndex) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (fromIndex == toIndex) {
            return (contentClass == Object.class) ? new Object[0] : (Object[]) Array.newInstance(
                contentClass, 0); // doesn't modify data so I don't fire a Task
        }
        int len = (toIndex - fromIndex) + 1;
        if (fromIndex + len > count) {
            ensureCapacity(fromIndex + len);
        }

        Object[] removedValues = (contentClass == Object.class) ? new Object[len] : (Object[]) Array.newInstance(
            contentClass, len);
        System.arraycopy(objects, fromIndex, removedValues, 0, len);
        System.arraycopy(objects, toIndex + 1, objects, fromIndex,
            count - (toIndex + 1));
        count -= len;
        //preventOverCapacity();
        if (isBeingObserved()) {
            change("Write", null);
        }
        return removedValues;
    }
    // Inserting

    /**
     *
     * @param value
     */
    synchronized public void insertFirst(E value) {//shift
        if (count == 0) {
            ensureCapacity(count + 1);
            objects[0] = value;
            if (isBeingObserved()) {
                change("Write", null);
            }
            return;
        }

        ensureCapacity(count + 1);
        System.arraycopy(objects, 0, objects, 1, count - 1);
        objects[0] = value;
        if (isBeingObserved()) {
            change("Write", null);
        }
        return;
    }

    /**
     *
     * @param values
     */
    synchronized public void insertFirst(E[] values) {//shiftArray
        if (values == null || values.length == 0) {
            return; // doesn't modify data so I don't fire a Task
        }
        int len = values.length;
        if (count == 0) {
            ensureCapacity(len);
            System.arraycopy(values, 0, objects, 0, len);
            if (isBeingObserved()) {
                change("Write", null);
            }
            return;
        }

        ensureCapacity(count + len);
        System.arraycopy(objects, 0, objects, len, count - len);
        System.arraycopy(values, 0, objects, 0, len);
        if (isBeingObserved()) {
            change("Write", null);
        }
        return;
    }
    // if index < 0 throws RuntimeException

    /**
     *
     * @param value
     * @param index
     */
    synchronized public void insertAt(E value, int index) {//insert
        if (index < 0) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (index > count) {
            ensureCapacity(index + 1);
        }
        else {
            ensureCapacity(count + 1);
        }

        if (index == 0 && count == 0) {
            objects[0] = value;
            if (isBeingObserved()) {
                change("Write", null);
            }
            return;
        }

        System.arraycopy(objects, index, objects, index + 1, (count - 1) - index);
        objects[index] = value;
        if (isBeingObserved()) {
            change("Write", null);
        }
        return;
    }

    /**
     *
     * @param indexA
     * @param indexB
     */
    synchronized public void swap(int indexA, int indexB) {//insert
        if (indexA < 0 || indexB < 0) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (indexA > count) {
            ensureCapacity(indexA);
        }
        if (indexB > count) {
            ensureCapacity(indexB);
        }

        E swap = objects[indexA];
        objects[indexA] = objects[indexB];
        objects[indexB] = swap;
        if (isBeingObserved()) {
            change("Write", null);
        }
        return;
    }
    // if index < 0 throws RuntimeException

    /**
     *
     * @param values
     * @param index
     */
    synchronized public void insertAt(E[] values, int index) {//insertArray
        if (index < 0) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (values == null || values.length == 0) {
            return;// doesn't modify data so I don't fire a Task
        }
        int len = values.length;
        int localCount = count;
        if (index + len > count) {
            ensureCapacity(index + len);
        }
        else {
            ensureCapacity(count + len);
        }

        if (index == 0) {
            System.arraycopy(objects, 0, objects, len, localCount);
            System.arraycopy(values, 0, objects, 0, len);
            if (isBeingObserved()) {
                change("Write", null);
            }
            return;
        }

        System.arraycopy(objects, index, objects, index + len,
            localCount - index);
        System.arraycopy(values, 0, objects, index, len);
        if (isBeingObserved()) {
            change("Write", null);
        }
        return;

    }

    /**
     *
     * @param value
     */
    synchronized public void insertLast(E value) {// push
        ensureCapacity(count + 1);
        objects[count - 1] = value;
        if (isBeingObserved()) {
            change("Write", null);
        }
    }

    /**
     *
     * @param _values
     */
    synchronized public void insertLast(E[] _values) { //pushArray
        if (_values == null) {
            return;// doesn't modify data so I don't fire a Task
        }
        int len = _values.length;
        ensureCapacity(count + len);
        System.arraycopy(_values, 0, objects, count - len, len);
        if (isBeingObserved()) {
            change("Write", null);
        }
    }
    // Replacing

    /**
     *
     * @param value
     * @return
     */
    synchronized public Object replaceFirst(E value) {
        if (count == 0) {
            throw new RuntimeException("Index Out of Bounds");
        }
        Object replacedValue = objects[0];
        objects[0] = value;
        if (isBeingObserved()) {
            change("Write", null);
        }
        return replacedValue;
    }

    /**
     *
     * @param value
     * @return
     */
    synchronized public Object replaceLast(E value) {
        if (count == 0) {
            throw new RuntimeException("Index Out of Bounds");
        }
        Object replacedValue = objects[count - 1];
        objects[count - 1] = value;
        if (isBeingObserved()) {
            change("Write", null);
        }
        return replacedValue;
    }

    /**
     *
     * @param values
     * @return
     */
    synchronized public Object replaceFirst(E[] values) {
        if (count == 0) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (values == null || values.length == 0) {
            return null;// doesn't modify data so I don't fire a Task
        }
        int localCount = count;
        int len = values.length;
        ensureCapacity(localCount + len - 1);
        Object replacedValue = objects[0];
        System.arraycopy(objects, 1, objects, len, localCount - 1);
        System.arraycopy(values, 0, objects, 0, len);
        if (isBeingObserved()) {
            change("Write", null);
        }
        return replacedValue;
    }

    /**
     *
     * @param values
     * @return
     */
    synchronized public Object replaceLast(E[] values) {
        if (count == 0) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (values == null || values.length == 0) {
            return null;// doesn't modify data so I don't fire a Task
        }
        int localCount = count;
        int len = values.length;
        ensureCapacity(localCount + len - 1);
        Object replacedValue = objects[localCount - 1];
        System.arraycopy(values, 0, objects, localCount - 1, len);
        if (isBeingObserved()) {
            change("Write", null);
        }
        return replacedValue;
    }

    /**
     *
     * @param value
     * @param index
     * @return
     */
    synchronized public Object replaceAt(E value, int index) {
        if (index < 0) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (count == 0) {
            ensureCapacity(1 + index);
        }
        int localCount = count;
        if (index > localCount) {
            ensureCapacity(index);
        }
        Object replacedValue = objects[index];
        objects[index] = value;
        if (isBeingObserved()) {
            change("Write", null);
        }
        return replacedValue;
    }

    /**
     *
     * @param values
     * @param index
     * @return
     */
    synchronized public Object replaceAt(E[] values, int index) {
        if (count == 0 || index < 0) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (values == null || values.length == 0) {
            return null;// doesn't modify data so I don't fire a Task
        }
        int len = values.length;
        int localCount = count;
        if (index + len > localCount) {
            ensureCapacity(index + len - 1);
        }
        else {
            ensureCapacity(localCount + len - 1);
        }
        Object replacedValue = objects[index];

        if (index == 0) {
            System.arraycopy(objects, 1, objects, len, localCount - 1);
            System.arraycopy(values, 0, objects, 0, len);
        }
        else {
            System.arraycopy(objects, index + 1, objects, index + len,
                localCount - 1);
            System.arraycopy(values, 0, objects, index, len);
        }
        if (isBeingObserved()) {
            change("Write", null);
        }
        return replacedValue;
    }

    /**
     *
     * @param value
     * @param fromIndex
     * @param toIndex
     */
    synchronized public void replaceFromTo(E value, int fromIndex, int toIndex) {
        if (count == 0 || fromIndex < 0 || fromIndex > toIndex) {
            throw new RuntimeException("Index Out of Bounds");
        }
        int len = (toIndex - fromIndex) + 1;
        int localCount = count;
        if (fromIndex + len > localCount) {
            ensureCapacity(fromIndex + len);
        }
        Object[] replacedValues = (contentClass == Object.class) ? new Object[len] : (Object[]) Array.newInstance(
            contentClass, len);
        System.arraycopy(objects, fromIndex, replacedValues, 0, len);

        System.arraycopy(objects, toIndex + 1, objects, fromIndex + 1,
            localCount - len);
        objects[fromIndex] = value;
        count -= (len - 1);
        if (isBeingObserved()) {
            change("Write", null);
        }
    }

    /**
     *
     * @param values
     * @param fromIndex
     * @param toIndex
     */
    synchronized public void replaceFromTo(E[] values, int fromIndex,
        int toIndex) {
        if (count == 0 || fromIndex < 0 || fromIndex > toIndex) {
            throw new RuntimeException("Index Out of Bounds");
        }
        if (values == null || values.length == 0) {
            return;// doesn't modify data so I don't fire a Task
        }
        int valuesLen = values.length;
        int len = (toIndex - fromIndex) + 1;
        int localCount = count;
        if (fromIndex + len > localCount) {
            ensureCapacity(fromIndex + len);
        }
        Object[] replacedValues = (contentClass == Object.class) ? new Object[len] : (Object[]) Array.newInstance(
            contentClass, len);
        System.arraycopy(objects, fromIndex, replacedValues, 0, len);

        if (fromIndex + valuesLen - 1 > localCount) {
            ensureCapacity(fromIndex + valuesLen - 1);
        }
        System.arraycopy(objects, toIndex + 1, objects, fromIndex + valuesLen,
            localCount - len);
        System.arraycopy(values, 0, objects, fromIndex, valuesLen);
        count = localCount - len + valuesLen;
        if (isBeingObserved()) {
            change("Write", null);
        }
    }

    /**
     *
     * @param _object
     * @return
     */
    synchronized public boolean contains(E _object) {
        for (int i = 0; i < count; i++) {
            if (objects[i].equals(_object)) {
                return true;
            }
        }
        return false;
    }
    // IBackcall

    @Override
    public void backcall(IOut _, ICallback<E, E> _callback) {
        try {
            E[] _objects = objects;
            long c = _objects.length;
            if (c <= 0) {
                return;
            }
            for (int i = 0; i < c; i++) {
                E v = _objects[i];
                E back = _callback.callback(v);
                if (back != v) {
                    break;
                }
                if (_.canceled()) {
                    break;
                }
            }
            return;
        }
        catch (Exception x) {
            x.printStackTrace();
            return;
        }
    }

    /**
     *
     * @param _class
     * @return
     */
    public E[] toArray(Class<E> _class) {
        if (_class == contentClass) {
            return getAll();
        }
        return (E[]) Array.newInstance(_class, 0);
    }
}
