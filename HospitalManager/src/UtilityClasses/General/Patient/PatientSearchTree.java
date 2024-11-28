package UtilityClasses.General.Patient;
import java.util.ArrayList;

import UtilityClasses.DataStructures.BinarySearchStuff.BSTNode;
import UtilityClasses.DataStructures.BinarySearchStuff.BinarySearchTree;
import UtilityClasses.DataStructures.QueryStuff.Query;

public class PatientSearchTree extends BinarySearchTree<Patient>{

    
    /**
     * Creates a new patient search tree rooted at a given node
     * @param node
     */
    public PatientSearchTree(BSTNode<Patient> node){
        super(node);
    }

    public ArrayList<Patient> findWithQueryBinarySearch(PatientQuery query){
        ArrayList<Patient> returnPatients = new ArrayList<Patient>();

        // CASE 1: No Date of Birth to do binary search with
        if (query.getDateOfBirth() == null){
            // if there's no date of birth, we just have to search normally
            for (Patient patient : this){
                if (query.matches(patient)){
                    returnPatients.add(patient);
                }
            }
            return returnPatients;
        }

        // if we do have a date of birth, we can run binary search with this
        binarySearchHelper(root, query, returnPatients);
        return returnPatients;
    }

    /**
     * Recursive helper method for checking for a patient in a tree using binary search
     * @param node
     * @param query
     * @param patients
     */
    public void binarySearchHelper(BSTNode<Patient> node, PatientQuery query, ArrayList<Patient> patients){
        // BASE CASE 1: Nothing in tree, matches query of date of birth
        if (node == null){
            // we do nothing because nothing in the tree matches our query
        }
        if (query.compareTo(node.getData()) < 0){
            binarySearchHelper(node.getLeft(), query, patients);
            return;
        }
        if (query.compareTo(node.getData()) > 0){
            binarySearchHelper(node.getRight(), query, patients);
            return;
        }

        // otherwise if we meet an element that matches our date of birth, that means that POSSIBLY everything in its subtree could match
        // our query, so we create a new tree rooted at it's left and right nodes, and binary search through those
        // we also check if our node matches our query

        if (node.getData().matches(query)){
            patients.add(node.getData());
        }
        PatientSearchTree rightToSearch = new PatientSearchTree(node.getRight());
        PatientSearchTree leftToSearch = new PatientSearchTree(node.getLeft());
        patients.addAll(rightToSearch.findWithQueryBinarySearch(query));
        patients.addAll(leftToSearch.findWithQueryBinarySearch(query));

    }

    
    
}
