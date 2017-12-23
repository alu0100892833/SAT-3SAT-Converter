package ull.alu0100892833.cc.sat3sat;

/**
 * Created by Óscar Darias Plasencia on 23/12/17.
 */
class SATVariable {

    private String symbol;
    private boolean negated;

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

    public SATVariable getNegated() {
        if (negated)
            return new SATVariable(symbol);
        else
            return new SATVariable("¬" + symbol);
    }

    @Override
    public String toString() {
        if (negated)
            return "¬" + symbol;
        else
            return symbol;
    }
}
