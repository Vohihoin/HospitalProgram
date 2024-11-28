package UtilityClasses.DataStructures.QueryStuff;
import java.util.ArrayList;

import UtilityClasses.DataStructures.BinarySearchTree;
import UtilityClasses.General.Patient.Patient;
import UtilityClasses.General.Patient.PatientQuery;

public class QueryUtility {

    /**
     * 
     * 
     * Type Parameters
     * @param C - Some queryable class (able ot call matches on some query Q)
     * @param D - Iterable data structure over queryable elements C
     */
    public static <C extends Queryable<C>, D extends Iterable<C>> ArrayList<C> findWithQuery(D dataStructure, Query<C> query){
        ArrayList<C> returnList =  new ArrayList<C>();
        for (C element : dataStructure){
            if (query.matches(element)){
                returnList.add(element);
            }
        }
        return returnList;
    }
    
}
