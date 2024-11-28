package UtilityClasses.DataStructures.BinarySearchStuff;

import java.util.Iterator;
import java.util.NoSuchElementException;

import UtilityClasses.DataStructures.QueryStuff.Query;
import UtilityClasses.DataStructures.QueryStuff.Queryable;
import UtilityClasses.General.Patient.Patient;
import UtilityClasses.General.Patient.PatientQuery;

/**
 * Binary tree class upon objects that are comparable
 * 
 * Note: T is always queryable over itself and will always have a query, so we don't need to worry about it
 * @see Comparable
 */
public class BinarySearchTree <T extends Comparable<T> & Queryable<T>> implements Iterable<T>{

    protected BSTNode<T> root;
    private int size;

    /**
     * BinaryTree constructor
     * Creates a new binary tree
     */
    public BinarySearchTree(){
        size = 0;
        root = null;
    }

    /**
     * Creates a new binary search tree from a root
     * @param root
     */
    public BinarySearchTree(BSTNode<T> root){
        this.root = root;
        size = countNumberOfNodesInSubtree(root);
    }

    /**
     * Adds a new element to the binary tree. Returns true if the element is added properly,
     * false otherwise
     * @param element
     * @return true if the element is added properly, false otherwise
     */
    public boolean addElement(T element){
        if (root == null){
            root = new BSTNode<T>(element);
            size++;
            return true;
        }
        return addElementHelper(root, element);
    }

    /**
     * Counts the number of nodes in a subtree rooted at a given node
     * @return
     */
    private int countNumberOfNodesInSubtree(BSTNode<T> node){
        if (node == null){
            return 0;
        }
        return 1 + countNumberOfNodesInSubtree(node.getLeft()) + countNumberOfNodesInSubtree(node.getRight());
    }

    /**
     * Recursive helper method for adding nodes to a binary tree
     * @param node
     * @param element
     * @return
     */
    private boolean addElementHelper(BSTNode<T> node, T element){
        
        // if element is less than node, we add it the left subtree
        if (element.compareTo(node.getData()) < 0){
            if (node.getLeft() == null){ // BASE CASE
                node.setLeft(new BSTNode<T>(element));
                size++;
                return true;
            }
            return addElementHelper(node.getLeft(), element);
        }
        // if element is greater than node, we add it to the right subtree
        if (element.compareTo(node.getData()) > 0){
            if (node.getRight() == null){
                node.setRight(new BSTNode<T>(element));
                size++;
                return true;
            }
            return addElementHelper(node.getRight(), element);
        }

        // duplicate element so we return false
        return false;

    }

    /**
     * Return the first element that matches the query
     * @param <Q>
     * @param query
     * @return
     */
    public <Q extends Query<T>> T find(Q query){
        for (T element : this){
            if (query.matches(element) ){
                return element;
            }
        }
        return null;
    }

    /**
     * Removes an element from the binary search tree and returns true if the element is successively removed
     * @param element the element to remove
     * @return true if the element is successfully removed, false otherwise (if the element isn't in the tree)
     */
    public boolean remove(T element){
        if (root == null){
            return false;
        }
        try{
            root = removeHelper(root, element);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    /**
     * Private helper recursive method removing an element from a node,
     * takes in the root node of a subtree and returns the root node of the subtree with the element removed
     * or throws a no such element exception
     * @param node
     * @param element
     * @return
     * @throws NoSuchElementException if the element to remove isn't in the tree
     */
    private BSTNode<T> removeHelper(BSTNode<T> node, T element){
        if (node == null){
            throw new NoSuchElementException();
        }
        if (element.compareTo(node.getData()) < 0){
            node.setLeft(removeHelper(node.getLeft(), element));
            return node;
        }

        if (element.compareTo(node.getData()) > 0){
            node.setRight(removeHelper(node.getRight(), element));
            return node;
        }

        // aaay we found the node, so we want to remove it

        // the node is a leaf
        if (isLeaf(node)){
            size--;
            return null;
        }

        // the node is single-childed
        if (isSingleChilded(node)){
            size--;
            return (node.getLeft() != null ? node.getLeft() : node.getRight()); // we return the s
        }

        // if the node is double-childed, we find its successor (must exist because it has a right child)
        // then we remove it's successor from the tree
        // replace our node with it's succesor by setting the node's right and left as the successors right and left, then 
        // return our new successor subtree as the tree with the element removed/
        T successor = next(node.getData());
        remove(successor);

        BSTNode<T> succesorNode = new BSTNode<T>(successor);
        succesorNode.setLeft(node.getLeft());
        succesorNode.setRight(node.getRight());
        size--;
        return succesorNode;

    }

    /**
     * Returns true if a node is a leaf (no children)
     * @param node the node we're checking
     * @return true if a node is a leaf, false otherwise
     */
    private boolean isLeaf(BSTNode<T> node){
        return (node.getLeft() == null && node.getRight() == null );
    }

    /**
     * Returns true if a node is single childed
     * @param node the node we're checking
     * @return true if a node is single childed, false otherwise
     */
    private boolean isSingleChilded(BSTNode<T> node){
        return (node.getLeft() != null && node.getRight() == null) || (node.getLeft() == null && node.getRight() != null);
    }

    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator<T>(this);
    }

    /**
     * Finds an elements successor or returns null if the element has no successor
     * @param element
     * @return
     */
    public T next(T element){
        return nextHelper(root, element);
    }

    /**
     * Looks for a successor to an element in a subtree and return the successor if it does exist
     * or null if it doesn't
     * @param node
     * @param element
     * @return
     */
    public T nextHelper(BSTNode<T> node, T element){
        // if you're element's next would be in the left subtree

        if (node == null){
            return null;
        }

        // whenever we go left, our current element, we're going for could be the successor or there could be a better one
        // so we check if there is a better one in the left subtree and if it there is, we use it, otherwise we use our current node
        if (element.compareTo(node.getData()) < 0){
            T possibleSuccessor = node.getData();
            T otherPossibleSuccessor = nextHelper(node.getLeft(), element);
            return ((otherPossibleSuccessor != null) ? otherPossibleSuccessor : possibleSuccessor);
        }
        
        // otherwise, our element is greater than or equals to the current node, so our successor must be in the right subtree
        return nextHelper(node.getRight(), element);
        
    }

    /**
     * Returns the minimum element in the tree (decided by compareTo) or null if the tree is empty
     * @return
     */
    public T getMin(){
        if (root == null){
            return null;
        }
        BSTNode<T> curr = root;
        while(curr.getLeft() != null){
            curr = curr.getLeft();
        }
        return curr.getData();
    }

    /**
     * Returns the maximum element in tree as defined by compareTo or null if the tree is empty
     * @return
     */
    public T getMax(){
        if (root == null){
            return null;
        }
        BSTNode<T> curr = root;
        while(curr.getRight() != null){
            curr = curr.getRight();
        }
        return curr.getData();
    }

}
