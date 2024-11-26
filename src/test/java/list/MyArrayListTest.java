package list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyArrayListTest {

    private MyArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyArrayList<>();
    }
    @Test
    void testConstructorWithCapacity() {
        MyArrayList<Integer> customList = new MyArrayList<>(15);
        assertEquals(0, customList.size(), "Size should initially be 0");

        for (int i = 0; i < 15; i++) {
            customList.add(i);
        }
        assertEquals(15, customList.size());
        assertEquals(0, customList.get(0));
        assertEquals(14, customList.get(14));
    }
    @Test
    void testConstructorWithNegativeCapacity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new MyArrayList<>(-1));

        assertEquals("Емкость не может быть отрицательной: -1", exception.getMessage());
    }
    @Test
    void testAddElement() {
        list.add(5);
        list.add(10);
        assertEquals(2, list.size());
        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
    }

    @Test
    void testAddElementByIndex() {
        list.add(1);
        list.add(2);
        list.add(1, 3);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(2, list.get(2));
    }

    @Test
    void testGetElement() {
        list.add(42);
        list.add(43);
        assertEquals(42, list.get(0));
        assertEquals(43, list.get(1));
    }

    @Test
    void testSetElement() {
        list.add(1);
        list.add(2);
        list.set(1, 5);
        assertEquals(5, list.get(1));
    }

    @Test
    void testRemoveElement() {
        list.add(10);
        list.add(20);
        list.add(30);
        int removed = list.remove(1);
        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    void testClear() {
        list.add(1);
        list.add(2);
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    void testSortNaturalOrder() {
        list.add(3);
        list.add(1);
        list.add(4);
        list.add(2);

        list.sort();

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(4, list.get(3));
    }

    @Test
    void testSortCustomOrder() {
        list.add(3);
        list.add(1);
        list.add(4);
        list.add(2);

        list.sort(Comparator.reverseOrder());

        assertEquals(4, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(2, list.get(2));
        assertEquals(1, list.get(3));
    }

    @Test
    void testSortThrowsIfNotComparable() {
        MyArrayList<Object> objList = new MyArrayList<>();
        objList.add(new Object());
        objList.add(new Object());
        assertThrows(IllegalStateException.class, objList::sort);
    }

    @Test
    void testEnsureCapacity() {
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        assertEquals(20, list.size());
        assertEquals(0, list.get(0));
        assertEquals(19, list.size() - 1);
    }

    @Test
    void testOutOfBoundExceptionOnGet() {
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }

    @Test
    void testOutOfBoundsExceptionOnRemove() {
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
    }

    @Test
    void testOutOfBoundsExceptionOnAddByIndex() {
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(5, 2));
    }
}
