package ull.alu0100892833.cc.sat3sat;

/**
 * Class to represent a variable for a SAT problem.
 * It is extremely simple and it just stores the identifier symbol and if it is negated or not.
 * @author Óscar Darias Plasencia
 * @since 23/12/17.
 */
class SATVariable {

    private String symbol;
    private boolean negated;

    /**
     * Constructor from the description of the variable using a String object.
     * Checks if it is negated.
     * @param sym
     */
    public SATVariable(String sym) {
        if (sym.charAt(0) == '¬') {
            negated = true;
            sym = sym.substring(1);
        } else
            negated = false;
        this.symbol = sym;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    /**
     * Returns the complementary variable (the same but negated).
     * @return
     */
    public SATVariable getNegated() {
        if (negated)
            return new SATVariable(symbol);
        else
            return new SATVariable("¬" + symbol);
    }

    /**
     * Provides a String representation of the object.
     * @return
     */
    @Override
    public String toString() {
        if (negated)
            return "¬" + symbol;
        else
            return symbol;
    }
}
