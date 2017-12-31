package ull.alu0100892833.cc.sat3sat;

import ull.alu0100892833.cc.sat3sat.exceptions.WrongSizeArrayException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Class to represent a clause in a SAT problem.
 * It just keeps an ArrayList with all the variables (SATVariable).
 * Provides all methods needed by a clause, to simplify SAT class design.
 * @author Óscar Darias Plasencia
 * @since 23/12/17.
 */
public class SATClause {

    // this variable is just a counter for the new y variables sub-index.
    public static int newVarCount = 1;

    private ArrayList<SATVariable> variables;

    /**
     * Constructor from a description on a String, like "{...}".
     * @param definition
     */
    public SATClause(String definition) {
        variables = new ArrayList<>();
        ArrayList<String> expression = tokenizeExpression(definition.trim());
        for (String temp : expression)
            if (!temp.trim().equals(""))
                variables.add(new SATVariable(temp));
    }

    public ArrayList<SATVariable> getVariables() {
        return variables;
    }

    /**
     * Returns the number of elements in the clause.
     * @return
     */
    public int getSize() {
        return variables.size();
    }

    /**
     * Constructor without arguments.
     */
    public SATClause() {
        variables = new ArrayList<>();
    }

    /**
     * Adds a new variable (SATVariable) to the clause.
     * @param var
     */
    public void addVariable(SATVariable var) {
        variables.add(var);
    }

    /**
     * Provides a String representation of the clause.
     * @return
     */
    @Override
    public String toString() {
        String output = "{";
        for (int i = 0; i < variables.size(); i++) {
            output += variables.get(i);
            if (i != variables.size() - 1)
                output += ", ";
        }
        output += "}";
        return output;
    }

    /**
     * Copy constructor.
     * @param copy
     */
    public SATClause(SATClause copy) {
        this.variables = new ArrayList<>(copy.variables);
    }

    /**
     * Method to tokenize a clause description String and store by pieces on an ArrayList.
     * This way it is easier to process.
     * @param exp
     * @return
     */
    private ArrayList<String> tokenizeExpression(String exp) {
        ArrayList<String> tokenized = new ArrayList<>();
        StringTokenizer str = new StringTokenizer(exp, " {,=}");
        while (str.hasMoreElements())
            tokenized.add((String) str.nextElement());
        return tokenized;
    }

    /**
     * Checks whether or not a boolean assignation satisfies the clause.
     * In other words, it checks if at least one variable is going to have the value true.
     * @param booleanAssign
     * @return
     * @throws WrongSizeArrayException
     */
    public boolean checkSatisfiability(ArrayList<Boolean> booleanAssign) throws WrongSizeArrayException {
        if (booleanAssign.size() != variables.size())
            throw new WrongSizeArrayException("ERROR - WRONG VALUES ASSIGNATION");
        for (int i = 0; i < booleanAssign.size(); i++) {
            if (((variables.get(i).isNegated()) && (!booleanAssign.get(i)))
                    || ((!variables.get(i).isNegated()) && (booleanAssign.get(i))))
                return true;
        }
        return false;
    }

    /**
     * Method for adapting the clause for a 3SAT problem.
     * @return ArrayList with all the clauses adapted. Represents the equivalent C'j set.
     */
    public ArrayList<SATClause> transformTo3SAT() {
        System.out.print(this + " to 3SAT =====> ");
        ArrayList<SATClause> newC = new ArrayList<>();
        if (variables.size() == 3) {
            newC.add(new SATClause(this));
        } else if (variables.size() == 1) {
            newC.addAll(transformForK1());
        } else if (variables.size() == 2) {
            newC.addAll(transformForK2());
        } else {
            newC.addAll(transformForBiggerK());
        }
        System.out.println("\t" + newC);
        return newC;
    }

    /**
     * Generates the four clauses adapted for 3SAT from a |c_i| = 1 clause.
     * @return C'_j set.
     */
    private List<SATClause> transformForK1() {
        SATVariable first = new SATVariable("y_" + newVarCount); newVarCount++;
        SATVariable second = new SATVariable("y_" + newVarCount); newVarCount++;
        List<SATClause> newSet = new ArrayList<>();

        SATClause clause1 = new SATClause(this);
        clause1.addVariable(first);
        clause1.addVariable(second);
        newSet.add(clause1);

        SATClause clause2 = new SATClause(this);
        clause2.addVariable(first.getNegated());
        clause2.addVariable(second.getNegated());
        newSet.add(clause2);

        SATClause clause3 = new SATClause(this);
        clause3.addVariable(first.getNegated());
        clause3.addVariable(second);
        newSet.add(clause3);

        SATClause clause4 = new SATClause(this);
        clause4.addVariable(first);
        clause4.addVariable(second.getNegated());
        newSet.add(clause4);

        return newSet;
    }

    /**
     * Generates the two clauses adapted for 3SAT from a |c_i| = 2 clause.
     * @return C'_j set.
     */
    private List<SATClause> transformForK2() {
        SATVariable newVar = new SATVariable("y_" + newVarCount); newVarCount++;
        List<SATClause> newSet = new ArrayList<>();

        SATClause clause1 = new SATClause(this);
        clause1.addVariable(newVar);
        SATClause clause2 = new SATClause(this);
        clause2.addVariable(newVar.getNegated());

        newSet.add(clause1);
        newSet.add(clause2);
        return newSet;
    }

    /**
     * Generates the C'_j, adapted for 3SAT and from a |c_i| > 3 clause.
     * @return C'_j set.
     */
    private List<SATClause> transformForBiggerK() {
        int numberOfNewVars = variables.size() - 3;
        SATVariable currentVar = new SATVariable("y_" + newVarCount); newVarCount++;
        List<SATClause> newSet = new ArrayList<>();

        SATClause newClause = new SATClause();
        newClause.addVariable(variables.get(0));
        newClause.addVariable(variables.get(1));
        newClause.addVariable(currentVar);
        newSet.add(newClause);
        currentVar = currentVar.getNegated();

        for (int i = 1; i < numberOfNewVars; i++) {
            newClause = new SATClause();
            newClause.addVariable(currentVar);
            newClause.addVariable(variables.get(1 + i));
            currentVar = new SATVariable("y_" + newVarCount); newVarCount++;
            newClause.addVariable(currentVar);
            currentVar = currentVar.getNegated();
            newSet.add(newClause);
        }

        newClause = new SATClause();
        newClause.addVariable(currentVar);
        newClause.addVariable(variables.get(variables.size() - 2));
        newClause.addVariable(variables.get(variables.size() - 1));
        newSet.add(newClause);

        return newSet;
    }






    public static void resetNewVarCount() {
        newVarCount = 1;
    }

}
