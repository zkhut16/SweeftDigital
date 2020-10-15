package pack;
import java.io.*;
import java.util.Iterator;

public class MyHashTable<E> implements Iterable<E>{
    Object[] array;
    int size;
    int count;
    public MyHashTable(){
        count = 0;
        size = 100;
        array = new Object[size];
    }

    public void add(E e){
        if(count == size)
            array = growArr();
        int index = e.hashCode();
        if(array[index]!=null){
            while(array[index]!=null){
                //check if element is already in hashtable
                if(((E)array[index]).equals(e)) return;
                index = (index + 1) % size;
            }
        }
        count++;
        array[index] = e;
    }

    public void remove(E e){
        int index = e.hashCode();
        while(index>size){
            growArr();
        }
        int breaker = 0;
        if(array[index]!=null){
            while(array[index]!=null){
                if(((E)array[index]).equals(e)){
                    array[index] = null;
                    return;
                }
                index = (index + 1) % size;
                breaker++;
                //if we iterated full array and element is not present break;
                if(breaker == size) break;
            }
        }
    }

    private Object[] growArr() {
        size *= 2;
        Object[] arr = new Object[size];
        for(int i = 0; i < array.length; i++){
            arr[i] = array[i];
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator<E> implements Iterator{
        private int index = 0;
        @Override
        public boolean hasNext() {
            if(index==size)
                return false;
            while(array[index]==null) {
                index++;
                if (index == size)
                    return false;
            }
            return true;
        }

        @Override
        public E next() {
            index++;
            return (E)array[index-1];
        }

        @Override
        public void remove() {

        }
    }
}