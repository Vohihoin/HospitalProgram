package UtilityClasses.DataStructures.QueryStuff;

/**
 * A class C implement Queryable <C, T> means that class is queryable over itself (C), with a T that refers to a Query class that
 * implements Query over C. So, your Query Class T is built for a particular C which is queryable over itself and your new query class
 * 
 * The interface queryable defines that any class that implements it be able to be queried to see
 * if it matches certain conditions outlined by the query class over which it extends
 * C - class you're implementing queryable into
 * T - the query class you're using to run queries over C
 */
public interface Queryable<C> {

    /**
     * Matches should call the matches of the query on this
     * @param query
     * @return
     */
    public <Q extends Query<C>>  boolean matches(Q query);

}
