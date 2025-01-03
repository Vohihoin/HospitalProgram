package UtilityClasses.DataStructures.StackStuff;

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
import UtilityClasses.DataStructures.QueueStuff.LinkedNode;

/**
 * Generic stack class to store data in a class
 * @param <T> the type of objects stored in the stack
 */
public class LinkedStack <T> implements StackADT<T>{
  
  private LinkedNode<T> top;
  private int size = 0;

  /**
   * Push adds an element to the top of a stack
   * @param value the element to add at the top of the stack
   */
  @Override
  public void push(T value) {
    if (isEmpty()) { // pushing to an empty stack
      top = new LinkedNode<T>(value);
      size++;
      return;
    }
    
    // pushing to a non-empty stack
    LinkedNode<T> newTop = new LinkedNode<T>(value);
    newTop.setNext(top);
    top = newTop;
    size++;
  }

  /**
   * Pop removes an element front the top of the stack and returns it
   * @return the element at the top of the stack or null if the stack is empty
   */
  @Override
  public T pop() {
    if (isEmpty()) {
      return null;
    }
    
    T toReturn = top.getData();
    top = top.getNext();
    return toReturn;
    
  }

  public int size(){
    return this.size;
  }

  /**
   * Peek returns a reference to the element at the top of the stack but doesn't remove the element
   * @return a reference to the element at the top of the stack or null if the stack is empty
   */
  @Override
  public T peek() {
    if (isEmpty()) {
      return null;
    }
    // if not empty, return reference to data
    return top.getData();
    
  }

  /**
   * Returns true if the stack is completely empty, otherwise returns false
   * @return true if the stack is completely empty, otherwise returns false
   */
  @Override
  public boolean isEmpty() {
    return (top == null);
  }

  /**
   * Checks if the stack contains a particular element. A stack contains an element value if and
   * only if there exists an element in the stack such that value.equals(that-element) returns
   * true, otherwise returns false
   * @return true if the stack contains the element, false otherwise
   */
  @Override
  public boolean contains(T value) {
    LinkedNode<T> curr = top;
    while(curr != null) {
      if (curr.getData().equals(value)) {
        return true;
      }
      curr = curr.getNext();
    }
    return false;
  }
  
  /**
   * Creates an arraylist of the content of the stack with the first element being the 
   * element at the top of the stack and so on. 
   * @return an arraylist representation of the contents of the stack
   */
  public ArrayList<T> getList(){
    ArrayList<T> returnList = new ArrayList<T>();
    
    LinkedNode<T> curr = top;
    while(curr != null) {
      returnList.add(curr.getData());
      curr = curr.getNext();
    }
    
    return returnList;
  }

}
