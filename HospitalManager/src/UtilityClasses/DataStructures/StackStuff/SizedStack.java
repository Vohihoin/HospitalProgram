package UtilityClasses.DataStructures.StackStuff;

public class SizedStack<T> extends LinkedStack<T>{
    private int capacity;
    
    public SizedStack(int capacity){
        super();
        this.capacity = capacity;
    }

    public boolean isFull(){
        return (this.size() == this.capacity);
    }

    @Override
    public void push(T element){
        if (isFull()){
            throw new IllegalStateException();
        }
        super.push(element);
    }
}
