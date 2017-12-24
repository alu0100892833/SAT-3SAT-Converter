package ull.alu0100892833.cc.sat3sat;

import ull.alu0100892833.cc.sat3sat.exceptions.WrongSizeArrayException;

import java.util.ArrayList;
import java.util.List;
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

    public ArrayList<SATVariable> getVariables() {
        return variables;
    }

    public int getSize() {
        return variables.size();
    }

    public SATClause() {
        variables = new ArrayList<>();
    }

    public void addVariable(SATVariable var) {
        variables.add(var);
    }

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
        for (int i = 0; i < booleanAssign.size(); i++) {
            if (((variables.get(i).isNegated()) && (!booleanAssign.get(i)))
                    || ((!variables.get(i).isNegated()) && (booleanAssign.get(i))))
                return true;
        }
        return false;
    }

    public ArrayList<SATClause> transformTo3SAT() {
        System.out.println("==============");
        System.out.print("Adapting " + this + " to 3SAT -----> ");
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
        System.out.println("==============");
        return newC;
    }

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
