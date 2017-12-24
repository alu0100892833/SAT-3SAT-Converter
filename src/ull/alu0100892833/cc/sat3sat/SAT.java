package ull.alu0100892833.cc.sat3sat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by Óscar Darias Plasencia on 23/12/17.
 */
public class SAT {

    private String identifier;
    private ArrayList<SATClause> clauses;

    public SAT() {
        identifier = "";
        clauses = new ArrayList<>();
        SATClause.resetNewVarCount();
    }

    public SAT(String description) {
        SATClause.resetNewVarCount();
        clauses = new ArrayList<>();
        ArrayList<String> expression = tokenizeExpression(description);
        identifier = expression.get(0);
        for (int iterator = 1; iterator < expression.size(); iterator++)
            clauses.add(new SATClause("{" + expression.get(iterator) + "}"));
    }

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
