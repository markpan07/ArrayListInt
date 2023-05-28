package sky.pro.service;

import sky.pro.exception.ElementNotFoundException;
import sky.pro.exception.OutOfBoundException;
import sky.pro.exception.TooLittleArrayException;
import sky.pro.interfaces.IntegerList;

public class IntegerListImpl implements IntegerList {

    private Integer[] storage;
    int pointer = 0;

    public IntegerListImpl(int arraySize) {
        storage = new Integer[arraySize];
    }

    public static void bubbleSort(IntegerListImpl list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j) > list.get(j + 1)) {
                    swap(list, j, j + 1);
                }
            }
        }
    }

    private static void swap(IntegerListImpl list, int index1, int index2) {
        int tmp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, tmp);
    }

    @Override
    public Integer add(Integer item) {
        if (pointer < storage.length - 1) {
            storage[pointer] = item;
            pointer++;
        } else {
            resize();
            storage[pointer] = item;
            pointer++;
        }
        return storage[pointer - 1];
    }


    @Override
    public Integer add(int index, Integer item) throws OutOfBoundException {
        if (index < 0 || index >= storage.length || storage[index] == null) {
            throw new OutOfBoundException("There is no element with such index");
        }
        if (storage[storage.length - 1] != null) {
            resize();
        }
        shiftToRight(index);
        storage[index] = item;
        pointer++;
        return storage[index];
    }

    @Override
    public Integer set(int index, Integer item) throws OutOfBoundException, NullPointerException {
        if (item == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index >= storage.length || storage[index] == null) {
            throw new OutOfBoundException("There is no element with such index");
        }
        storage[index] = item;
        return storage[index];
    }

    @Override
    public Integer remove(Integer item) {
        int index = indexOf(item);
        if (index == -1) {
            throw new ElementNotFoundException("There is no element with such index");
        }
        return remove(index);
    }

    @Override
    public Integer remove(int index) {
        if (index < 0 || index >= storage.length - 1 || storage[index] == null) {
            throw new ElementNotFoundException("There is no element with such index");
        }
        Integer del = storage[index];
        storage[index] = null;
        shiftToLeft(index);
        pointer--;
        return del;
    }

    @Override
    public boolean contains(Integer item) {
        if (indexOf(item) != -1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int indexOf(Integer item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] != null && storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException();
        }
        for (int i = storage.length - 1; i >= 0; i--) {
            if (storage[i] != null && storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) throws ElementNotFoundException {
        if (index < 0 || index >= storage.length - 1 || storage[index] == null) {
            throw new ElementNotFoundException("There is no element with such index");
        }
        return storage[index];
    }

    @Override
    public boolean equals(IntegerListImpl otherList) throws NullPointerException {
/*
        if (otherList == null) {
            throw new NullPointerException();
        }
        if (otherList == this) {
            return true;
        }

        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] == null && otherList.get(i) == null) {
                break;
            } else if ((int) storage[i] != (int) otherList.get(i)) {
                return false;

            }
        }
*/

        return true;
    }

    @Override
    public int size() {
        int count = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
        Integer[] newStore = new Integer[4];
        storage = newStore;
        pointer = 0;
    }

    @Override
    public Integer[] toArray() {
        Integer[] out = new Integer[storage.length];
        System.arraycopy(storage, 0, out, 0, storage.length);
        return out;
    }


    private void resize() {
        Integer[] storage2 = new Integer[(int) (storage.length * 1.5 + 1)];
        System.arraycopy(storage, 0, storage2, 0, storage.length);
        storage = storage2;
    }

    private void shiftToRight(int index) {
        if (storage[storage.length - 1] != null) {
            throw new TooLittleArrayException("Array has to be extended");
        }
        for (int i = storage.length - 2; i >= index; i--) {
            storage[i + 1] = storage[i];
        }
        storage[index] = null;
    }

    private void shiftToLeft(int index) {
        for (int i = index; i < storage.length - 1; i++) {
            storage[i] = storage[i + 1];
        }

    }

}
