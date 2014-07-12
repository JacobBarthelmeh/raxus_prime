/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raxus_prime;

import java.util.HashMap;

/**
 * Simple array-backed data structure that guarantees O(1) retrieval, and O(1)
 * remove, O(1) add, O(1) search. Order is not maintained. 
 *
 * @author alexhuleatt
 */
public class BossList<E> {

    IntNode buffer;
    Object[] arr;
    int length;
    int soFar;
    int cap;
    HashMap<E, Integer> map;

    private class IntNode {

        int val;
        IntNode next;

        IntNode(int val, IntNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public BossList(int cap) {
        this.arr = new Object[cap];
        this.cap = cap;
        length = 0;
        soFar = 0;
        map = new HashMap<E, Integer>(length);
    }

    public void add(E val) throws IndexOutOfBoundsException {
        if (buffer == null) {
            if (soFar == cap) {
                throw new IndexOutOfBoundsException();
            } else {
                arr[soFar] = val;
                map.put(val, soFar++);
                length++;
            }
        } else {
            arr[buffer.val] = val;
            map.put(val, buffer.val);
            buffer = buffer.next;
            length++;
        }

    }

    public E remove(int index) throws IndexOutOfBoundsException {
        E e = (E) arr[index];
        if (e != null) {
            arr[index] = null;
            IntNode n = new IntNode(index, buffer);
            buffer = n;
            map.put(e, null);
            length--;
        }
        return e;
    }

    public E remove(E e) {
        return remove(map.get(e));
    }

    public E get(int index) {
        return (E) arr[index];
    }

    public boolean contains(E e) {
        return map.get(e) != null;
    }

    public int size() {
        return length;
    }   

    public Object[] condensed() {
        Object[] con = new Object[length];
        int i = 0;
        for (Object o : arr) {
            if (o != null) {
                con[i++] = o;
            }
        }
        return con;
    }

}
