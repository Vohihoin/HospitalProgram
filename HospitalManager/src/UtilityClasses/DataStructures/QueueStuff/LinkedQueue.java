package UtilityClasses.DataStructures.QueueStuff;

//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    JukeBox (Back to the 1950s!)
// Course:   CS 300 Fall 2024
//
// Author:   Vahe Ohihoin
// Email:    vohihoin@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         none
// Online Sources:  Piazza Posts: @2037
//                                https://piazza.com/class/lzhgq2kaz6b5n8/post/2043_f1
//                                @2070
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

/**
 * Generic Queue Class to store data in a queue
 * @param <T> the type of objects stored in the queue
 */
public class LinkedQueue<T> implements QueueADT<T>{
  
  private LinkedNode<T> front;
  private LinkedNode<T> back;
  private int size;
  
  /**
   * Add a new element to the back of the queue, assumed to be non-null.
   * @param value value to add to the back of the queue, assumed to be non-null
   */
  @Override
  public void enqueue(T value) {
    
    LinkedNode<T> newNode = new LinkedNode<T>(value);
    
    if (isEmpty()) { // if empty
      front = newNode;
      back = newNode;
      size++;
      return;
    }
    
    // if not empty
    back.setNext(newNode);
    back = newNode;
    size++;
    
  }

  /**
   * Removes an element from the front of the queue and returns it. The element at the front is
   * the least recently added element
   * @return the value that was dequeued or null if queue is empty
   */
  @Override
  public T dequeue() {
    if (isEmpty()) {
      return null;
    }
    
    // if not empty
    T toReturn = front.getData();
    front = front.getNext();
    
    // accounting for the back in the case of 1 element
    if (size == 1) {
      back = null;
    }
    
    size--;
    return toReturn;
    
  }

  /**
   * Returns a reference to the current element at the front of the queue (oldest-added element)
   * @return a reference to the current element at the front of the queue (it doesn't however
   * remove the element like dequeue)
   */
  @Override
  public T peek() {
    if (isEmpty()) {
      return null;
    }
    return front.getData();
  }

  /**
   * Returns true if the queue is empty, otherwise returns false
   * @return true if the queue is empty, otherwise returns false
   */
  @Override
  public boolean isEmpty() {
    return (front == null) && (back == null) && (size == 0);
  }

  /**
   * Returns the current number of elements in the queue
   * @return the current number of elements in the queue
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Empties out the queue so that there's nothing in it
   */
  @Override
  public void clear() {
    front = null;
    back = null;
    size = 0;
  }

  /**
   * Checks if the queue contains a particular value. A queue contains a value if and only if
   * there exists an element in the queue such that value.equals(that-element)returns true
   * @return true if queue contains the element, false otherwise
   */
  @Override
  public boolean contains(T value) {
    
    LinkedNode<T> curr = front;
    
    while(curr != null) {
      if (curr.getData().equals(value)) {
        return true;
      }
      curr = curr.getNext();
    }
    
    return false;
  }
  
  /**
   * Creates a copy of the current contents of this queue in the order they are present here, in 
   * ArrayList form. This method should traverse the queue without removing any elements, and add 
   * the values (not the nodes!) to an ArrayList in the order they appear in the queue, with the 
   * front of the queue at index 0.
   * @return an arraylist of the elements in the queue.
   */
  public ArrayList<T> getList(){
    ArrayList<T> returnList = new ArrayList<T>();
    
    LinkedNode<T> curr = front;
    while(curr != null) {
      returnList.add(curr.getData());
      curr = curr.getNext();
    }
    
    return returnList;
  }

}
