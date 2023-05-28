package service;

import org.assertj.core.api.Assertions;
import sky.pro.exception.ElementNotFoundException;
import sky.pro.exception.OutOfBoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sky.pro.service.IntegerListImpl;

class IntegerListImplTest {
    private IntegerListImpl out = new IntegerListImpl(4);

    @BeforeEach
    void setUp() {
        out.add(5);
        out.add(11);
    }

    @AfterEach
    void after() {
        out.clear();
    }

    @Test
    void addInTheEnd() {
        out.add(21);
        out.add(3);
        out.add(7);
        Integer[] expected = new Integer[]{5, 11, 21, 3, 7, null, null};
        Assertions.assertThat(expected).
                usingRecursiveComparison().
                isEqualTo(out.toArray());
    }

    @Test
    void addByIndex() {
        out.add(1, 21);
        Integer[] expected = new Integer[]{5, 21, 11, null};
        Assertions.assertThat(expected).
                usingRecursiveComparison().
                isEqualTo(out.toArray());
    }

    @Test
    void addByIndexWithArrayExtending() {
        out.add(21);
        out.add(3);
        out.add(2, 7);
        Integer[] expected = new Integer[]{5, 11, 7, 21, 3, null, null};
        Assertions.assertThat(expected).
                usingRecursiveComparison().
                isEqualTo(out.toArray());
    }

    @Test
    void addByIncorrectIndex() {
        Assertions.assertThatExceptionOfType(OutOfBoundException.class).isThrownBy(() ->
                out.add(3, 175));
        Assertions.assertThatExceptionOfType(OutOfBoundException.class).isThrownBy(() ->
                out.add(5, 17));
    }


    @Test
    void set() {
        out.set(1, 27);
        Integer[] expected = new Integer[]{5, 27, null, null};
        Assertions.assertThat(expected).
                usingRecursiveComparison().
                isEqualTo(out.toArray());
    }

    @Test
    void removeByIndex() {
        Integer[] expected = new Integer[]{11, null, null, null};
        out.remove(0);
        Assertions.assertThat(expected).
                isEqualTo(out.toArray());
    }

    @Test
    void removeByIndexNull() {
        Assertions.assertThatExceptionOfType(NullPointerException.class).
                isThrownBy(() ->
                        out.remove(null));
    }

    @Test
    void removeByIncorrectIndex() {
        Assertions.assertThatExceptionOfType(ElementNotFoundException.class).
                isThrownBy(() ->
                        out.remove(3));
    }

    @Test
    void removeByItem() {
        out.add(27);
        out.add(29);
        Integer[] expected = new Integer[]{5, 11, 29, null, null, null, null};
        out.remove((Integer) 27);
        Assertions.assertThat(expected).
                isEqualTo(out.toArray());
    }

    @Test
    void removeNonExistedItem() {
        Assertions.assertThatExceptionOfType(ElementNotFoundException.class).
                isThrownBy(() ->
                        out.remove(552));
    }

    @Test
    void contains() {
        out.add(27);
        out.add(33);
        out.add(15);
        Assertions.assertThat(out.contains(27)).isTrue();
        Assertions.assertThat(out.contains(511)).isFalse();

    }

    @Test
    void indexOf() {
        int expected = 0;
        Assertions.assertThat(expected).
                isEqualTo(out.indexOf(5));
        int expected2 = -1;
        Assertions.assertThat(expected2).
                isEqualTo(out.indexOf(554));
    }

    @Test
    void indexOfNull() {
        Assertions.assertThatExceptionOfType(NullPointerException.class).
                isThrownBy(() -> out.indexOf(null));

    }

    @Test
    void lastIndexOfNull() {
        Assertions.assertThatExceptionOfType(NullPointerException.class).
                isThrownBy(() -> out.lastIndexOf(null));
    }

    @Test
    void lastIndexOf() {
        int expected = 0;
        Assertions.assertThat(expected).
                isEqualTo(out.lastIndexOf(5));
        int expected2 = -1;
        Assertions.assertThat(expected2).
                isEqualTo(out.lastIndexOf(15));
    }

    @Test
    void get() {
        Integer expected = 5;
        Assertions.assertThat(expected).
                isEqualTo(out.get(0));
    }

/*    @Test
    void testEquals() {
        IntegerListImpl list = new IntegerListImpl(4);
        IntegerListImpl list2 = new IntegerListImpl(6);
        IntegerListImpl list3 = new IntegerListImpl(4);
        list.add((Integer) 5);
        list.add((Integer) 11);
        list2.add((Integer) 5);
        list2.add((Integer) 11);
        list3.add((Integer) 5);
        list3.add((Integer) 7);
        Assertions.assertThat(out.equals(list))
                .isTrue();
        Assertions.assertThat(out.equals(list2))
                .isTrue();
        Assertions.assertThat(out.equals(list3))
                .isFalse();


    }
    @Test
    void testEqualsNull() {

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(out.equals(null));

    }*/

    @Test
    void size() {
        Assertions.assertThat(out.size()).
                isEqualTo(2);
        out.add(17);
        out.add(26);
        out.add(36);
        Assertions.assertThat(out.size()).
                isEqualTo(5);
    }

    @Test
    void isEmpty() {
    }

    @Test
    void clear() {
        out.clear();
        Integer[] expected = new Integer[]{null, null, null, null};
        Assertions.assertThat(expected).
                usingRecursiveComparison().
                isEqualTo(out.toArray());
        out.add(15);
        out.add(27);
        Integer[] expected2 = new Integer[]{15, 27, null, null};
        Assertions.assertThat(expected2).
                usingRecursiveComparison().
                isEqualTo(out.toArray());
    }

    @Test
    void toArray() {
    }
}