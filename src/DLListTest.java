
import student.TestCase;
import java.util.*;

/**
 * @author Brantson Bui (CS 2114)
 * @version 2024.03.24
 *
 */
public class DLListTest extends TestCase {
    /**
     * the list we will use
     */
    private DLList<String> list;

    /**
     * run before every test case
     */
    @Override
    public void setUp() {
        list = new DLList<String>();
    }


    /**
     * Tests that an IndexOutOfBounds exception is thrown when the index is
     * greater than or equal to size and less than zero
     */
    public void testRemoveException() {
        list.add("A");
        Exception e = null;
        try {
            list.remove(2);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
        e = null;
        try {
            list.remove(-1);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests that objects can be removed at the beginning and end and that the
     * size is changed
     */
    public void testRemoveIndex() {
        list.add("A");
        list.add("B");
        assertTrue(list.remove(1));
        assertEquals(1, list.size());
        list.add("B");
        assertTrue(list.remove(0));
        assertEquals(1, list.size());
    }


    /**
     * Tests the add method. Ensures that it adds the object is added at the end
     * and the size is increased
     */
    public void testAdd() {
        assertEquals(0, list.size());
        list.add("A");
        assertEquals(1, list.size());
        list.add("B");
        assertEquals(2, list.size());
        assertEquals("B", list.get(1));

    }


    /**
     * Tests that objects can be added at the beginning and end and that they
     * are placed correctly
     */
    public void testAddIndex() {
        list.add("B");
        list.add(0, "A");
        assertEquals("A", list.get(0));
        assertEquals(2, list.size());
        list.add(2, "D");
        assertEquals("D", list.get(2));
        list.add(2, "C");
        assertEquals("C", list.get(2));
    }


    /**
     * This tests that the add method throws a null pointer exception when
     * adding null data to the list
     */
    public void testAddNullException() {
        Exception e = null;
        try {
            list.add(null);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IllegalArgumentException);
    }


    /**
     * This tests that the add method throws a Invalid argument when adding null
     * data to the list
     */
    public void testAddIndexNullException() {
        Exception e = null;
        try {
            list.add(0, null);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IllegalArgumentException);
    }


    /**
     * This tests when the add method is called with both valid and invalid
     * index values,
     * ensuring that the correct exceptions are thrown or not thrown as
     * expected.
     */
    public void testAddException() {
        list.add("A"); // Adds "A" at index 0, size becomes 1

        // Case 1: Index greater than size (size is 1, so index 2 is invalid)
        Exception e = null;
        try {
            list.add(2, "B"); // Invalid index, should throw
                              // IndexOutOfBoundsException
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException); // This should pass

        // Case 2: Negative index (index -1 is invalid)
        e = null;
        try {
            list.add(-1, "B"); // Invalid index, should throw
                               // IndexOutOfBoundsException
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException); // This should pass

        // Case 3: Valid index at the end (index == size)
        e = null;
        try {
            list.add(1, "B"); // Valid index, should NOT throw any exception
        }
        catch (Exception exception) {
            e = exception;
        }
        assertNull(e); // No exception expected

        // Case 4: Valid index at the beginning (index == 0)
        e = null;
        try {
            list.add(0, "C"); // Valid index, should NOT throw any exception
        }
        catch (Exception exception) {
            e = exception;
        }
        assertNull(e); // No exception expected

        // Case 5: Index equal to the size after adding more elements
        list.add("D"); // Adds at index 2 (size now 3)
        e = null;
        try {
            list.add(3, "E"); // Valid index == size, should NOT throw any
                              // exception
        }
        catch (Exception exception) {
            e = exception;
        }
        assertNull(e); // No exception expected
    }


    /**
     * This tests when the add method is called with various combinations of the
     * index < 0 and size < index checks to ensure all logical combinations are
     * covered.
     */
    public void testAddLogicalConditions() {
        list.add("A"); // Adds "A" at index 0, size becomes 1

        // Case 1: (false, false) - index is within valid range (index == size)
        Exception e = null;
        try {
            list.add(1, "B"); // Valid index (index == size)
        }
        catch (Exception exception) {
            e = exception;
        }
        assertNull(e); // No exception expected for valid index

        // Case 2: (false, true) - index > size (size is 1, index is 2)
        e = null;
        try {
            list.add(3, "C"); // Invalid index (index > size)
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException); // Exception
                                                            // expected

        // Case 3: (true, false) - index < 0 (negative index)
        e = null;
        try {
            list.add(-1, "D"); // Invalid index (index < 0)
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException); // Exception
                                                            // expected

        // Case 4: (true, true) - index < 0 and index > size (size is 1, index
        // is -1)
        e = null;
        try {
            list.add(-1, "E"); // Invalid index (index < 0 and index > size
                               // doesn't apply)
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException); // Exception
                                                            // expected

        // Edge Case 5: Adding at index 0, should be valid (index == 0)
        e = null;
        try {
            list.add(0, "F"); // Valid index (index == 0)
        }
        catch (Exception exception) {
            e = exception;
        }
        assertNull(e); // No exception expected for valid index at the beginning
    }


    /**
     * Tests removing a object changes the size appropiately and that you can
     * remove the first and last elements
     */
    public void testRemoveObj() {
        assertFalse(list.remove(null));
        list.add("A");
        list.add("B");
        assertEquals(2, list.size());
        assertTrue(list.remove("A"));
        assertEquals(1, list.size());
        assertEquals("B", list.get(0));
        list.add("C");
        assertTrue(list.remove("C"));
        assertEquals("B", list.get(0));
    }


    /**
     * Tests get when the index is greater than or equal to size and when the
     * index is less than zero
     */
    public void testGetException() {
        Exception exception = null;
        try {
            list.get(-1);
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
        exception = null;
        list.add("A");
        try {
            list.get(1);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }


    /**
     * Test contains when it does and does not contain the object
     */
    public void testContains() {
        assertFalse(list.contains("A"));
        list.add("A");
        assertTrue(list.contains("A"));
        assertFalse(list.contains("B"));
        list.add("B");
        assertTrue(list.contains("B"));
    }


    /**
     * Test lastIndexOf when the list is empty, when the object is not in the
     * list, and when it is at the beginning or end
     */
    public void testLastIndexOf() {
        assertEquals(-1, list.lastIndexOf("A"));
        list.add("A");
        assertEquals(0, list.lastIndexOf("A"));
        list.add("A");
        assertEquals(1, list.lastIndexOf("A"));
        list.add("B");
        assertEquals(1, list.lastIndexOf("A"));
        assertEquals(2, list.lastIndexOf("B"));
        list.add("A");
        assertEquals(3, list.lastIndexOf("A"));
    }


    /**
     * Tests isEmpty when empty and full
     */
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add("A");
        assertFalse(list.isEmpty());
    }


    /**
     * Ensures that all of the objects are cleared and the size is changed
     */
    public void testClear() {
        list.add("A");
        list.clear();
        assertEquals(0, list.size());
        assertFalse(list.contains("A"));
    }


    /**
     * Tests the toString when there are 0, 1, and 2 objects in the list
     */
    public void testToString() {
        assertEquals("{}", list.toString());
        list.add("A");
        assertEquals("{A}", list.toString());
        list.add("B");
        assertEquals("{A, B}", list.toString());
    }


    /**
     * Tests removing from an empty list
     */
    public void testRemoveFromEmpty() {
        list.add("dance");
        list.add(0, "safety");
        list.clear();
        assertFalse(list.remove("safety"));
        Exception exception;
        exception = null;
        try {
            list.remove(0);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);

        DLList<String> emptyList = new DLList<String>();
        exception = null;
        try {
            emptyList.remove(0);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests the iterator methods
     */
    public void testIterator() {
        list.add("Item1");
        list.add("Item2");
        list.add("Item3");

        Iterator<String> iter = list.iterator();

        assertTrue(iter.hasNext());
        assertEquals("Item1", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("Item2", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("Item3", iter.next());

        assertFalse(iter.hasNext());
        NoSuchElementException nse = null;
        try {
            iter.next();
        }
        catch (NoSuchElementException e) {
            nse = e;
        }
        assertNotNull(nse);
    }

}
