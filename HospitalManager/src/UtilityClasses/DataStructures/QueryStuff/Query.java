package UtilityClasses.DataStructures.QueryStuff;

/**
 * Queries are built to run specific queriers over specific classes
 * @param C - the class we're building our query to make queries over
 */
public interface Query<C> {

    /**
     * 
     * @param object
     * @return
     */
    public boolean matches(C object);
}
