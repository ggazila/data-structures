package datastructure.arraylist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayListImpl<T> implements List<T> {
    private static final int DEFAULT_SIZE = 10;
    private Object[] array;
    private int size = 0;

    public ArrayListImpl() {
        array = new Object[DEFAULT_SIZE];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorImpl<>();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return (T1[]) Arrays.copyOf(array, size, a.getClass());
    }

    @Override
    public boolean add(T element) {
        checkAndIncrease();
        array[size++] = element;
        return true;
    }

    @Override
    public boolean remove(Object objectToRemove) {
        int index = indexOf(objectToRemove);
        if (index != -1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> externalCollection) {
        for (Object externalObj : externalCollection) {
            if (!contains(externalObj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int innerCount = index;

        for (T elementToAdd : c) {
            add(innerCount + 1, elementToAdd);
            innerCount++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int copySize = size;
        c.forEach(this::remove);
        return copySize != size;
    }

    @Override
    public boolean retainAll(Collection<?> external) {
        Object[] toRemove = new Object[size];
        int copySize = 0;
        int actualSize = 0;
        for (Object o : array) {
            if (!external.contains(o)) {
                toRemove[actualSize++] = o;
            }
        }
        for (int i = 0; i < actualSize; i++) {
            remove(toRemove[i]);
        }
        return copySize != size;
    }

    @Override
    public void clear() {
        array = new Object[DEFAULT_SIZE];
        size = 0;
    }

    @Override
    public T get(int index) {
        ensureIndex(index);
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        ensureIndex(index);
        Object replacedElement = array[index];
        array[index] = element;
        return (T) replacedElement;
    }

    @Override
    public void add(int index, T element) {
        checkAndIncrease();

        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        ensureIndex(index);
        Object o = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
        return (T) o;

    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i <= size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i <= size; i++) {
                if (o.equals(array[i])) {
                    return i;
                }

            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {

        if (o == null) {
            for (int i = size; i >= 0; i--) {
                if (array[i] == null) {
                    return i - 1;
                }
            }
        } else {
            for (int i = size; i >= 0; i--) {
                if (o.equals(array[i])) {
                    return i;
                }

            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr<>();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr<>(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        ensureSubListIndexes(fromIndex, toIndex);

        T[] subList = (T[]) new Object[toIndex - fromIndex];
        int count = 0;
        for (int i = fromIndex; i < toIndex; i++) {
            subList[count++] = (T) array[i];
        }
        return List.of(subList);
    }

    private void ensureSubListIndexes(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size) {
            throw new IndexOutOfBoundsException();
        } else if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
    }

    private void checkAndIncrease() {
        if (size + 1 >= array.length) {
            int newSize = size + (size >> 1);
            array = Arrays.copyOf(array, newSize);
        }
    }

    private void ensureIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class IteratorImpl<E> implements Iterator<E> {
        int cursor;

        public IteratorImpl() {
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            int innerIndex = cursor;
            if (innerIndex >= size) {
                throw new NoSuchElementException();
            }

            cursor += 1;

            return (E) array[innerIndex];
        }
    }

    private class ListItr<E> extends IteratorImpl<E> implements ListIterator<E> {
        public ListItr(int index) {
            cursor = index;
        }

        public ListItr() {
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            int innerIndex = cursor;
            if (innerIndex < 1) {
                throw new NoSuchElementException();
            }

            cursor -= 1;

            return (E) array[innerIndex - 1];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(E e) {
            if (cursor < 1) {
                throw new IllegalStateException();
            }
            ArrayListImpl.this.set(cursor - 1, (T) e);
        }

        @Override
        public void add(E e) {
            ArrayListImpl.this.add(cursor, (T) e);
            cursor++;
        }

        @Override
        public void remove() {
            ArrayListImpl.this.remove(cursor);
        }
    }
}
