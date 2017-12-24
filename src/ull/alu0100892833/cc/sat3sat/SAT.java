package ull.alu0100892833.cc.sat3sat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Class to represent an instance of a SAT problem.
 * @author Óscar Darias Plasencia
 * @since 23/12/17.
 */
public class SAT {

    private String identifier;
    private ArrayList<SATClause> clauses;

    /**
     * Constructor without arguments.
     */
    public SAT() {
        identifier = "";
        clauses = new ArrayList<>();
        SATClause.resetNewVarCount();
    }

    /**
     * Constructor from a description.
     * @param description String like "ID = {...}"
     */
    public SAT(String description) {
        SATClause.resetNewVarCount();
        clauses = new ArrayList<>();
        ArrayList<String> expression = tokenizeExpression(description);
        identifier = expression.get(0);
        for (int iterator = 1; iterator < expression.size(); iterator++)
            clauses.add(new SATClause("{" + expression.get(iterator) + "}"));
    }

    /**
     * Method to tokenize a SAT description String and store by pieces on an ArrayList.
     * This way it is easier to process.
     * @param description
     * @return
     */
    private ArrayList<String> tokenizeExpression(String description) {
        ArrayList<String> tokenized = new ArrayList<>();
        StringTokenizer str = new StringTokenizer(description, "{=}^");
        while (str.hasMoreElements()) {
            String element = (String) str.nextElement();
            if (!element.trim().equals(""))
                tokenized.add(element);
        }
        return tokenized;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<SATClause> getClauses() {
        return clauses;
    }

    protected void setClauses(ArrayList<SATClause> set) {
        clauses = set;
    }

    /**
     * Generates and returns a String with all the variables of the problem.
     * This String will be like "U = {z1, z2, z3... zn}"
     * @return
     */
    public String getUSet() {
        HashSet<String> uSet = new HashSet<>();
        for (SATClause temp : clauses) {
            for (SATVariable var : temp.getVariables()) {
                uSet.add(var.getSymbol());
            }
        }
        String output = "U = {";
        for (String str : uSet) {
            output += str + ", ";
        }
        output = output.substring(0, output.length() - 2);
        output += "}";
        return output;
    }

    /**
     * Returns a representation of the problem on a String object.
     * @return
     */
    @Override
    public String toString() {
        String output = identifier + " = ";
        for (int i = 0; i < clauses.size(); i++) {
            output += clauses.get(i).toString();
            if (i != clauses.size() - 1)
                output += " ^ ";
        }
        return output;
    }
}
