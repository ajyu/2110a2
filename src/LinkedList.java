/**
 * This interface specifies the design for a doubly linked list storing double
 * values. Most methods on this list are copied directly from the Java 6 List<E>
 * API. Feel free to read it if you have the time:
 * http://download.oracle.com/javase/6/docs/api/java/util/List.html
 */
public interface LinkedList {

   /**
    * Appends the specified element to the end of this list
    * 
    * @param element
    *           - the element to be added
    * @return - true if the add is successful, false otherwise
    */
   public boolean add(double element);

   /**
    * Inserts the specified element at the specified position in this list.
    * Shifts the element currently at that position (if any) and any subsequent
    * elements to the right (adds one to their indices).
    * 
    * @param index
    *           - the position the insert the new element
    * @param element
    *           - the element to insert
    * @throws IndexOutOfBoundsException
    *            - if the index is out of range (index < 0 || index > size())
    */
   public void add(int index, double element) throws IndexOutOfBoundsException;

   /**
    * Appends all of the elements in the specified array to the end of this
    * list, in the same order as they are in the array.
    * 
    * @param c
    *           the array to add
    * @return - true if the list is changed because of the call
    */
   public boolean addAll(double[] c);

   /**
    * Removes all of the elements from this list. The list will be empty after
    * this call returns.
    */
   public void clear();

   /**
    * returns true if this list contains the double value v
    * 
    * @param v
    *           - the value to search for in the list
    * @return - true if v is in this list
    */
   public boolean contains(double v);

   /**
    * returns true if contain() returns true for every element in the array c
    * 
    * @param c
    *           - the array of elements to search for in the list
    * @return - true if the elements of the array is a subset of this list
    */
   public boolean containsAll(double[] c);

   /**
    * Returns the element at the specified position in this list.
    * 
    * @param index
    *           - the position to search at
    * @return - the element at the specified position in this list
    * @throws IndexOutOfBoundsException
    *            - if the index is out of range (index < 0 || index >= size())
    */
   public double get(int index) throws IndexOutOfBoundsException;

   /**
    * Returns the index of the first occurrence of the specified element in this
    * list, or -1 if this list does not contain the element.
    * 
    * @param o
    *           - the element to search for
    * @return - the index if the element is in the list. -1 if the element is
    *         not found in the list
    */
   public int indexOf(double o);

   /**
    * Returns true if this list contains no elements. More formmally, returns
    * true if this.size == 0.
    * 
    * @return - true if this list has zero elements
    */
   public boolean isEmpty();

   /**
    * Removes the element at the specified position in this list. Shifts any
    * subsequent elements to the left (subtracts one from their indices).
    * Returns the element that was removed from the list.
    * 
    * @param index
    *           - the index of the element to be removed
    * @return - the element previously at the specified position
    * @throws IndexOutOfBoundsException
    *            - if the index is out of range (index < 0 || index >= size())
    */
   public double remove(int index) throws IndexOutOfBoundsException;

   /**
    * Returns the number of elements in this list. If this list contains more
    * than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
    * 
    * @return the number of elements in this list
    */
   public int size();

   /**
    * Converts this list to a double array whose elements occur in the same
    * order.
    * 
    * @return the double array
    */
   public double[] toArray();

   /**
    * Gets the head node of this list
    * 
    * @return the first node of this list
    */
   public Node getHeadNode();
}
