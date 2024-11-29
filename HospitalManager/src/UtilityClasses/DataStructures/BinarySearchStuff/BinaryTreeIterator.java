package UtilityClasses.DataStructures.BinarySearchStuff;

import java.util.Iterator;
import java.util.NoSuchElementException;

import UtilityClasses.DataStructures.QueryStuff.Queryable;

public class BinaryTreeIterator <T extends Comparable<T> & Queryable<T>> implements Iterator<T>{

    private BinarySearchTree<T> tree;
    private T curr;

    public BinaryTreeIterator(BinarySearchTree<T> tree){
        this.tree = tree;
        curr = tree.getMin();
    }

    @Override
    public boolean hasNext(){
        return (curr != null);
    }
    
    @Override
    public T next(){
        if (!(hasNext())){
            throw new NoSuchElementException("ITERATED THROUGH");
        }
        T toReturn = curr;
        curr = tree.next(curr);
        return toReturn;
    }
    
}
