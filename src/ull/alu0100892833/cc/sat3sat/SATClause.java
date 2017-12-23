package ull.alu0100892833.cc.sat3sat;

import ull.alu0100892833.cc.sat3sat.exceptions.WrongSizeArrayException;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Óscar Darias Plasencia on 23/12/17.
 */
public class SATClause {
    public static int newVarCount = 1;

    private ArrayList<SATVariable> variables;

    public SATClause(String definition) {
        variables = new ArrayList<>();
        ArrayList<String> expression = tokenizeExpression(definition.trim());
        for (String temp : expression)
            if (!temp.trim().equals(""))
                variables.add(new SATVariable(temp));
    }

    public SATClause() {
        variables = new ArrayList<>();
    }

    public void addVariable(SATVariable var) {
        variables.add(var);
    }


    public SATClause(SATClause copy) {
        this.variables = new ArrayList<>(copy.variables);
    }

    private ArrayList<String> tokenizeExpression(String exp) {
        ArrayList<String> tokenized = new ArrayList<>();
        StringTokenizer str = new StringTokenizer(exp, " {,=}");
        while (str.hasMoreElements())
            tokenized.add((String) str.nextElement());
        return tokenized;
    }

    public boolean checkSatisfiability(ArrayList<Boolean> booleanAssign) throws WrongSizeArrayException {
        if (booleanAssign.size() != variables.size())
            throw new WrongSizeArrayException("ERROR - WRONG VALUES ASSIGNATION");
        return booleanAssign.contains(true);
    }

    public ArrayList<SATClause> transformTo3SAT() {
        if (variables.size() == 3) {
            ArrayList<SATClause> newC = new ArrayList<>();
            newC.add(new SATClause(this));
            return newC;
        } else if (variables.size() == 1) {
            return transformForK1();
        } else if (variables.size() == 2) {
            return transformForK2();
        } else {
            return transformForBiggerK();
        }
    }

    private ArrayList<SATClause> transformForK1() {
        SATVariable first = new SATVariable("y_" + newVarCount); newVarCount++;
        SATVariable second = new SATVariable("y_" + newVarCount); newVarCount++;
        ArrayList<SATClause> newSet = new ArrayList<>();

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

    private ArrayList<SATClause> transformForK2() {
        SATVariable newVar = new SATVariable("y_" + newVarCount); newVarCount++;
        ArrayList<SATClause> newSet = new ArrayList<>();

        SATClause clause1 = new SATClause(this);
        clause1.addVariable(newVar);
        SATClause clause2 = new SATClause(this);
        clause2.addVariable(newVar.getNegated());

        newSet.add(clause1);
        newSet.add(clause2);
        return newSet;
    }

    private ArrayList<SATClause> transformForBiggerK() {
        int numberOfNewVars = variables.size() - 3;
        SATVariable currentVar = new SATVariable("y_" + newVarCount); newVarCount++;
        ArrayList<SATClause> newSet = new ArrayList<>();

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
