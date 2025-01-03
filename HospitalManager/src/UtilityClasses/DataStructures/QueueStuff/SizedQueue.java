package UtilityClasses.DataStructures.QueueStuff;

public class SizedQueue<T> extends LinkedQueue<T>{

    private int capacity;
    
    public SizedQueue(int capacity){
        super();
        this.capacity = capacity;
    }

    public boolean isFull(){
        return (this.size() == this.capacity);
    }

    @Override
    public void enqueue(T element){
        if (isFull()){
            throw new IllegalStateException();
        }
        super.enqueue(element);
    }
    
}
