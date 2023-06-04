package sky.pro.service;

import sky.pro.exception.ElementNotFoundException;
import sky.pro.exception.OutOfBoundException;
import sky.pro.exception.TooLittleArrayException;
import sky.pro.interfaces.IntegerList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class IntegerListImpl implements IntegerList {

    private Integer[] storage;
    int pointer = 0;


    public IntegerListImpl(int arraySize) {
        storage = new Integer[arraySize];
    }

    public static void bubbleSort(IntegerListImpl list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    swap(list.toArray(), j, j + 1);
                }
            }
        }
    }

    public static void selectionSort(IntegerListImpl list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j) < list.get(minIndex)) {
                    minIndex = j;
                }
            }
            swap(list.toArray(), i, minIndex);
        }
    }

    public static void insertionSort(IntegerListImpl list) {
        for (int i = 1; i < list.size(); i++) {
            int current = list.get(i);
            int j = i;
            while (j > 0 && list.get(j - 1) > current) {
                list.set(j, list.get(j - 1));
                j--;
            }
            list.set(j, current);
        }
    }

    public static void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, end);
        return i + 1;
    }
    
    private void privateInsertionSort() {
        for (int i = 1; i < storage.length; i++) {
            if (storage[i] != null) {
                int current = storage[i];
                int j = i;
                while (j > 0 && storage[j - 1] > current) {
                    storage[j] = storage[j - 1];
                    j--;
                }
                storage[j] = current;
            }
        }
    }

    private boolean binarySearch(Integer x) {
        int min = 0;
        int max = this.size() - 1;
        while (min <= max) {
            int mid = (max + min) / 2;
            if (storage[mid].equals(x)) {
                return true;
            }
            if (x < storage[mid]) {
                max = mid - 1;
            } else {
                min = mid +1;
            }
        }
        return false;
    }

    private static void swap(Integer[] list, int index1, int index2) {
        int tmp = list[index1];
        list[index1] = list[index2];
        list[index2] = list[tmp];
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

/*    @Override
    public boolean contains(Integer item) {
        if (indexOf(item) != -1) {
            return true;
        } else {
            return false;
        }
    }*/
    @Override
    public boolean contains(Integer item) {
        Integer[] copy = this.toArray();
        privateInsertionSort();
        return binarySearch(item);
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
        if (Objects.isNull(otherList)) {
            throw new NullPointerException();
        }
        if (otherList == this) {
            return true;
        }

        return Arrays.equals(this.toArray(), otherList.toArray());
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
        Integer[] out = new Integer[this.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = storage[i];
        }
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
