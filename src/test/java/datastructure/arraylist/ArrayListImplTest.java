package datastructure.arraylist;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ArrayListImplTest {
    private static final int ELEMENTS_COUNT = 1000;
    private List<String> list;

    @Before
    public void setUp() {
        list = new ArrayListImpl<>();
    }

    @Test
    public void addElementToMyArrayList() {
        list.add("Test");
        list.add(null);
        list.add("for");
        list.add("Mate");
        assertEquals("Test failed! Size of array should be " + 4 + "but it is "
                     + list.size(), 4, list.size());
        assertEquals("Test failed! Element is not added correctly",
                "Test", list.get(0));
        Assert.assertNull("Test failed! Element is not added correctly", list.get(1));
        assertEquals("Test failed! Element is not added correctly",
                "for", list.get(2));
        assertEquals("Test failed! Element is not added correctly",
                "Mate", list.get(3));
    }

    @Test
    public void addElementToMyArrayListByIndex() {
        list.add("Test");
        list.add("for");
        list.add("Me");
        assertEquals("Test failed! Size of array should be " + 3 + "but it is "
                     + list.size(), 3, list.size());
        list.add(1, "Value");
        assertEquals("Test", list.get(0));
        assertEquals("Test failed! Can't correctly add element by index " + 1,
                "Value", list.get(1));
        assertEquals("for", list.get(2));
        assertEquals("Me", list.get(3));
        list.add(0, null);
        assertEquals("Test failed! Size of array should be " + 5 + "but it is "
                     + list.size(), 5, list.size());
        Assert.assertNull(list.get(0));
        list.add(5, "value");
        assertEquals("Test failed! Can't correctly add element by index " + 5,
                "value", list.get(5));
        assertEquals("Test failed! Size of array should be " + 6 + "but it is "
                     + list.size(), 6, list.size());
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void addElementInTheNonExistentPosition() {
        list.add("First");
        list.add("Second");
        list.add(5, "Second");
    }

    @Test
    public void addListToMyArrayList() {
        list.add("Test");
        list.add("for");
        list.add("Me");
        assertEquals(3, list.size());
        List<String> newMyArrayList = new ArrayListImpl<>();
        newMyArrayList.add("Value");
        newMyArrayList.add("Ukraine");
        list.addAll(newMyArrayList);
        assertEquals("Test failed! Size of array should be " + 5 + "but it is "
                     + list.size(), 5, list.size());
        assertEquals("Value", list.get(3));
        assertEquals("Ukraine", list.get(4));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addElementInTheNegativePosition() {
        list.add("String");
        list.add(-6, "Java");
    }

    @Test
    public void checkingResize() {
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            list.add("First + " + i);
        }
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            assertEquals("Test failed! Array can't resize correctly",
                    "First + " + i, list.get(i));
        }
    }

    @Test
    public void checkingResizeInAddByIndex() {
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            list.add(0, "First + " + i);
            assertEquals("Test failed! Size of array should be " + (i + 1) + "but it is "
                         + list.size(), i + 1, list.size());
        }
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            assertEquals("Test failed! Array can't resize correctly",
                    "First + " + (ELEMENTS_COUNT - i - 1), list.get(i));
        }
    }

    @Test
    public void removeElementFromMyArrayListByIndex() {
        list.add("String");
        list.add(null);
        list.add("Java");
        list.add("Private");
        assertEquals(4, list.size());
        String actualResult = list.remove(2);
        assertEquals("Test failed! Returned value should be " + actualResult,
                "Java", actualResult);
        assertEquals("Test failed! Size of array after removed element should be "
                     + 3 + "but it is " + list.size(), 3, list.size());
        assertEquals("Test failed! Can't remove element by index ",
                "Private", list.get(2));
        actualResult = list.remove(0);
        assertEquals("Test failed! Returned value should be " + actualResult,
                "String", actualResult);
        assertEquals(2, list.size());
        Assert.assertNull("Test failed! Can't remove element by index ", list.get(0));
        actualResult = list.remove(1);
        assertEquals("Test failed! Returned value should be null",
                "Private", actualResult);
        assertEquals(1, list.size());
        Assert.assertNull("Test failed! Can't remove element by index ", list.get(0));
    }

    @Test
    public void removeElementFromFullMyArrayListByIndex() {
        ArrayListImpl<Integer> arrayListImpl = new ArrayListImpl<>();
        for (int i = 0; i < 10; i++) {
            arrayListImpl.add(i);
        }
        assertEquals(10, arrayListImpl.size());
        Integer actualResult = arrayListImpl.remove(9);
        assertEquals(String.format("Test failed! Returned value should be %d, "
                                   + "but was %d\n", 9, actualResult), 9, actualResult.longValue());
        int actualSize = arrayListImpl.size();
        assertEquals(String.format("Test failed! Size of array after removing element "
                                   + "should be %d, but it is %d\n", 9, actualSize), 9, actualSize);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeElementFromMyArrayListByNonExistentIndex() {
        list.add("String");
        list.add("Java");
        list.add("Private");
        list.remove(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeElementFromMyArrayListByNegativeIndex() {
        list.add("String");
        list.remove(-5);
    }

    @Test
    public void removeElementFromMyArrayListByValue() {
        list.add("String");
        list.add("Another string");
        list.add(null);
        list.add("Java");
        list.add("Private");
        list.add(null);
        assertEquals(6, list.size());
        boolean actualResult = list.remove("Java");
        Assert.assertTrue(String.format("Test failed! Returned value should be \"%s\", "
                                        + "but was \"%s\"\n", "Java", actualResult), actualResult);
        int actualSize = list.size();
        assertEquals(String.format("Test failed! Size of array after removing element "
                                   + "should be %d, but it is %d\n", 5, actualSize), 5, actualSize);
        assertEquals("Test failed! Remove was incorrect",
                "Private", list.get(3));
        actualResult = list.remove("String");
        Assert.assertTrue(String.format("Test failed! Returned value should be \"%s\", "
                                        + "but was \"%s\"\n", "String", actualResult), actualResult);
        actualSize = list.size();
        assertEquals(String.format("Test failed! Size of array after removing element "
                                   + "should be %d, but it is %d\n", 4, actualSize), 4, actualSize);
        assertEquals("Test failed! Remove was incorrect",
                "Private", list.get(2));
        actualResult = list.remove(null);
        Assert.assertTrue("Test failed! Returned value should be null", actualResult);
        actualSize = list.size();
        assertEquals(String.format("Test failed! Size of array after removing element "
                                   + "should be %d, but it is %d\n", 3, actualSize), 3, actualSize);
    }

    @Test()
    public void removeElementFromMyArrayListByNonExistentValue() {
        list.add("String");
        list.add("Java");
        list.add("Private");
        Assert.assertFalse(list.remove("Public"));
    }

    @Test
    public void removeObjectValueByValue() {
        Cat firstCat = new Cat("Fantic", "grey");
        Cat secondCat = new Cat("Barsik", "black");
        Cat thirdCat = new Cat("Tom", "white");
        Cat fourthCat = new Cat("Barsik", "black");
        List<Cat> cats = new ArrayListImpl<>();
        cats.add(firstCat);
        cats.add(secondCat);
        cats.add(thirdCat);
        assertEquals("Test failed! Size of array should be " + 3 + "but it is "
                     + cats.size(), 3, cats.size());
        boolean actualResult = cats.remove(fourthCat);
        Assert.assertTrue("Test failed! Returned value should be true", actualResult);
        assertEquals("Test failed! Size of array should be " + 2 + "but it is "
                     + cats.size(), 2, cats.size());
    }

    @Test
    public void setValueInIndex() {
        list.add("5");
        list.add("115");
        assertEquals("115", list.get(1));
        list.set(1, "511");
        assertEquals("Test failed! Size of array should be " + 2 + "but it is "
                     + list.size(), 2, list.size());
        assertEquals("Test failed! Can't set value by special position",
                "511", list.get(1));
        list.set(0, null);
        assertEquals("Test failed! Size of array should be " + 2 + "but it is "
                     + list.size(), 2, list.size());
        Assert.assertNull("Test failed! Can't set value by special position",
                list.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setValueInTheNonExistentPosition() {
        list.add("First");
        list.add("Second");
        list.set(2, "Third");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setValueInTheNegativePosition() {
        list.add("First");
        list.set(-2, "Third");
    }

    @Test
    public void getElementByIndex() {
        list.add("First");
        list.add("Second");
        list.add("Third");
        list.add(null);
        String actualResult = list.get(2);
        assertEquals("Third", actualResult);
        actualResult = list.get(3);
        Assert.assertNull(actualResult);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getElementByNonExistedIndex() {
        list.add("First");
        list.add("Second");
        list.add("Third");
        list.get(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getElementByNegativeIndex() {
        list.add("First");
        list.get(-2);
    }

    @Test
    public void checkIsNotEmpty() {
        list.add("First");
        list.add("Second");
        list.add("Third");
        Assert.assertFalse("Test failed! myArrayList shouldn't be empty", list.isEmpty());
    }

    @Test
    public void checkIsEmpty() {
        Assert.assertTrue("Test failed! myArrayList should be empty", list.isEmpty());
        list.add("First");
        list.remove(0);
        Assert.assertTrue("Test failed! myArrayList should be empty", list.isEmpty());
    }

    @Test
    public void checkContains() {
        Assert.assertFalse("Test failed! myArrayList should be empty.", list.contains("word"));
        list.add("First");
        Assert.assertTrue("Test failed! myArrayList should contain 'First'", list.contains("First"));
    }

    @Test
    public void checkToArray() {
        String[] words = { "one", "two", "three" };
        String[] wordsWrong = { "one", "two" };
        Collections.addAll(list, words);
        Object[] objects = list.toArray();
        Assert.assertArrayEquals("", words, objects);
        assertThat(objects, not(equalTo(wordsWrong)));
    }

    @Test
    public void checkToArrayGeneric() {
        String[] words = { "one", "two", "three" };
        String[] wordsWrong = { "one", "two" };
        Collections.addAll(list, words);
        String[] objects = list.toArray(new String[0]);
        Assert.assertArrayEquals("Test failed! myArrayList should contain 'First'", words, objects);
        assertThat(objects, not(equalTo(wordsWrong)));
    }

    @Test(expected = ArrayStoreException.class)
    public void checkToArrayWrongGeneric() {
        String[] words = { "one", "two", "three" };
        Collections.addAll(list, words);
        list.toArray(new Integer[0]);
    }

    @Test()
    public void checkContainsAll() {
        String[] words = { "one", "two", "three", null };
        List<String> notFull = new ArrayList<>();
        notFull.add("one");
        notFull.add("four");
        Collections.addAll(list, words);

        boolean containsAll = list.containsAll(list);
        Assert.assertTrue("The list should contain itself", containsAll);

        boolean actual = list.containsAll(notFull);
        Assert.assertFalse(actual);

    }

    @Test()
    public void checkAddAll() {
        String[] words = { "one", "two", "three", null };
        List<String> secondArray = new ArrayList<>();
        secondArray.add("four");
        secondArray.add("five");
        Collections.addAll(list, words);

        boolean addAll = list.addAll(secondArray);
        Assert.assertTrue(addAll);

        String[] expectedRaw = { "one", "two", "three", null, "four", "five" };
        List<String> expectedList = new ArrayList<>();
        Collections.addAll(expectedList, expectedRaw);
        assertEquals(list.size(), expectedList.size());
        Assert.assertTrue(expectedList.containsAll(list));
    }

    @Test()
    public void checkAddAllByIndex() {
        String[] words = { "one", "two", "three", null };
        List<String> secondArray = new ArrayList<>();
        secondArray.add("four");
        secondArray.add("five");
        Collections.addAll(list, words);

        boolean addAll = list.addAll(0, secondArray);
        Assert.assertTrue(addAll);

        String[] expectedRaw = { "four", "five", "one", "two", "three", null };
        List<String> expectedList = new ArrayList<>();
        Collections.addAll(expectedList, expectedRaw);
        assertEquals(list.size(), expectedList.size());
        Assert.assertTrue(expectedList.containsAll(list));
    }

    @Test()
    public void removeAll() {
        String[] words = { "one", "two", "three", null };
        Collections.addAll(list, words);

        String[] toDelete = { "three", null };
        List<String> toDeleteList = new ArrayList<>();
        Collections.addAll(toDeleteList, toDelete);
        boolean b = list.removeAll(toDeleteList);

        List<String> expected = new ArrayList<>();
        expected.add("one");
        expected.add("two");


        assertEquals(2, list.size());
        Assert.assertTrue(b);

        assertEquals(expected, list);
    }

    @Test()
    public void retainAll() {
        String[] words = { "one", "two", "three", null };
        Collections.addAll(list, words);

        String[] toRetain = { "three", null };
        List<String> toDeleteList = new ArrayList<>();
        Collections.addAll(toDeleteList, toRetain);


        boolean b = list.retainAll(toDeleteList);

        List<String> expected = new ArrayList<>();
        expected.add("three");
        expected.add(null);


        Assert.assertTrue(b);
        assertEquals(2, list.size());
        assertEquals(expected, list);
    }

    @Test()
    public void checkClear() {
        String[] words = { "one", "two", "three", null };
        Collections.addAll(list, words);

        list.clear();

        assertEquals(0, list.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void checkIterator() {
        String[] words = { "one", "two" };
        Collections.addAll(list, words);

        Iterator<String> iterator = list.iterator();

        String first = iterator.next();
        String second = iterator.next();
        assertEquals("one", first);
        assertEquals("two", second);
        iterator.next();
    }

    @Test
    public void checkLastIndexOf() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        int actualIndex = list.lastIndexOf("two");

        assertEquals(2, actualIndex);
    }

    @Test
    public void checkLastIndexOfNull() {
        String[] words = { null, "one", "two", "two", null };
        Collections.addAll(list, words);

        int actualIndex = list.lastIndexOf(null);

        assertEquals(4, actualIndex);
    }

    @Test
    public void checkLastIndexOfIfNotPresent() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        int actualIndex = list.lastIndexOf("three");

        assertEquals(-1, actualIndex);
    }

    @Test
    public void checkListIterator() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        ListIterator<String> listIterator = list.listIterator();

        boolean hasNext = listIterator.hasNext();
        boolean hasPrevious = listIterator.hasPrevious();
        int previousIndex = listIterator.previousIndex();
        int nextIndex = listIterator.nextIndex();

        Assert.assertTrue(hasNext);
        Assert.assertFalse(hasPrevious);
        assertEquals(-1, previousIndex);
        assertEquals(0, nextIndex);
    }

    @Test
    public void checkListIteratorSet() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        ListIterator<String> listIterator = list.listIterator();

        listIterator.next();
        listIterator.set("five");
        String setValue = listIterator.previous();

        assertEquals("five", setValue);
    }

    @Test(expected = IllegalStateException.class)
    public void checkListIteratorSetFromTheStart() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        ListIterator<String> listIterator = list.listIterator();
        listIterator.set("five");
    }

    @Test
    public void checkListIteratorPrevious() {
        String[] words = { "one", "two", "three" };
        Collections.addAll(list, words);

        ListIterator<String> listIterator = list.listIterator();
        System.out.println(listIterator.next());
        System.out.println(listIterator.next());
        String previous = listIterator.previous();
        String previousOneMoreTime = listIterator.previous();

        assertEquals("two", previous);
        assertEquals("one", previousOneMoreTime);
    }

    @Test(expected = NoSuchElementException.class)
    public void checkListIteratorPreviousOnZeroIndex() {
        String[] words = { "one", "two", "three" };
        Collections.addAll(list, words);

        ListIterator<String> listIterator = list.listIterator();
        listIterator.previous();
    }

    @Test()
    public void checkSubList() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        List<String> actual = list.subList(1, 3);
        List<String> expected = List.of("two", "two");

        assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkSubListBadIndex() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        list.subList(1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkSubListWrongIndexes() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        list.subList(2, 1);
    }

    @Test
    public void checkListIteratorAdd() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        ListIterator<String> listIterator = list.listIterator();

        listIterator.next();
        listIterator.add("five");
        String addValue = listIterator.previous();

        assertEquals("five", addValue);

        for (String word : list) {
            System.out.println(word);
        }
    }

    @Test
    public void checkListIteratorRemove() {
        String[] words = { "one", "two", "two" };
        Collections.addAll(list, words);

        ListIterator<String> listIterator = list.listIterator();

        listIterator.next();
        listIterator.remove();

        assertEquals(2, list.size());
    }

    @Test
    public void checkListIteratorByIndex() {
        String[] words = { "one", "two", "three" };
        Collections.addAll(list, words);

        ListIterator<String> listIterator = list.listIterator(1);
        String next = listIterator.next();

        assertEquals("two", next);
    }
}