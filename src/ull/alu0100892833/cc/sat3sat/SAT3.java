package ull.alu0100892833.cc.sat3sat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Óscar Darias Plasencia on 23/12/17.
 */
public class SAT3 extends SAT {

    public SAT3() {
        super();
    }

    public SAT3(String description) {
        super(description);
        for (SATClause temp : getClauses()) {
            if (temp.getSize() != 3) {
                List<SATClause> adapted = temp.transformTo3SAT();
                getClauses().remove(temp);
                getClauses().addAll(adapted);
            }
        }
    }

    public SAT3 (SAT transform) {
        super();
        this.setIdentifier(transform.getIdentifier());
        ArrayList<SATClause> adapted = new ArrayList<>();
        for (SATClause temp : transform.getClauses())
            adapted.addAll(temp.transformTo3SAT());
        this.setClauses(adapted);
    }
}
