package list;

import java.util.Comparator;

/**
 * Кастомная реализация динамического массива, похожего на {@link java.util.ArrayList}.
 *
 * @param <E> тип элементов, которые будут храниться в списке
 */
public class MyArrayList<E> {

    /**
     * Начальное значение для размера массива
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Внутренний массив для хранения элементов
     */
    private E[] elements;

    /**
     * Размер MyArrayList (число элементов которые находятся в нём в данный момент)
     */
    private int size;

    /**
     * Создаёт пустой список с начальной емкостью 10.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Создаёт пустой список с указанной начальной емкостью.
     *
     * @param capacity начальная емкость списка
     * @throws IllegalArgumentException если указанная емкость меньше 0
     */
    @SuppressWarnings("unchecked")
    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Емкость не может быть отрицательной: " + capacity);
        }
        this.elements = (E[]) new Object[capacity];
        this.size = 0;
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент, который нужно добавить
     */
    public void add(E element) {
        ensureCapacity();
        elements[size++] = element;
    }

    /**
     * Вставляет элемент в указанную позицию в списке.
     *
     * @param index   позиция, в которую нужно вставить элемент
     * @param element элемент, который нужно вставить
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    public void add(int index, E element) {
        checkIndexForAdd(index);
        ensureCapacity();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента, который нужно вернуть
     * @return элемент по указанному индексу
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    public E get(int index) {
        checkIndex(index);
        return elements[index];
    }

    /**
     * Заменяет элемент по указанному индексу на новый.
     *
     * @param index   индекс элемента, который нужно заменить
     * @param element новый элемент
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    public void set(int index, E element) {
        checkIndex(index);
        elements[index] = element;
    }

    /**
     * Удаляет элемент по указанному индексу.
     *
     * @param index индекс элемента, который нужно удалить
     * @return элемент, который был удалён
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    public E remove(int index) {
        checkIndex(index);
        E removedElement = elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
        return removedElement;
    }

    /**
     * Удаляет все элементы из списка. После вызова список будет пустым.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Возвращает количество элементов в списке.
     *
     * @return текущее количество элементов в списке
     */
    public int size() {
        return size;
    }

    /**
     * Сортирует список в естественном порядке, используя реализацию {@link Comparable}.
     *
     * @throws IllegalStateException если элементы не реализуют {@link Comparable}
     */
    @SuppressWarnings("unchecked")
    public void sort() {
        if (size > 0 && elements[0] instanceof Comparable) {
            sort((o1, o2) -> ((Comparable<E>) o1).compareTo(o2));
        } else {
            throw new IllegalStateException("Элементы должны реализовывать Comparable для естественной сортировки");
        }
    }

    /**
     * Сортирует список, используя указанный {@link Comparator}.
     *
     * @param comparator компаратор, определяющий порядок сортировки
     */
    public void sort(Comparator<? super E> comparator) {
        quickSort(0, size - 1, comparator);
    }

    /**
     * Проверяет, есть ли достаточно места в массиве для добавления нового элемента,
     * и при необходимости увеличивает емкость.
     */
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    /**
     * Проверяет, допустим ли индекс для добавления элемента.
     *
     * @param index индекс, который нужно проверить
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Проверяет, допустим ли индекс для доступа или изменения элемента.
     *
     * @param index индекс, который нужно проверить
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Реализация алгоритма быстрой сортировки (QuickSort).
     *
     * @param low        нижний индекс диапазона
     * @param high       верхний индекс диапазона
     * @param comparator компаратор для определения порядка сортировки
     */
    private void quickSort(int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int partitionIndex = partition(low, high, comparator);
            quickSort(low, partitionIndex - 1, comparator);
            quickSort(partitionIndex + 1, high, comparator);
        }
    }

    /**
     * Разделяет массив на две части вокруг опорного элемента.
     *
     * @param low        нижний индекс диапазона
     * @param high       верхний индекс диапазона
     * @param comparator компаратор для определения порядка сортировки
     * @return индекс опорного элемента
     */
    private int partition(int low, int high, Comparator<? super E> comparator) {
        E pivot = elements[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(elements[j], pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    /**
     * Меняет местами два элемента в массиве.
     *
     * @param i индекс первого элемента
     * @param j индекс второго элемента
     */
    private void swap(int i, int j) {
        E temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }
}
