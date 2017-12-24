package ull.alu0100892833.cc.sat3sat;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent an instance of a 3SAT problem. It extends the SAT class, just making sure that every clause has exactly three elements.
 * @author Óscar Darias Plasencia
 * @since 23/12/17.
 */
public class SAT3 extends SAT {

    /**
     * Constructor without arguments.
     */
    public SAT3() {
        super();
    }

    /**
     * Constructor providing a description.
     * @param description String like "ID = {....}"
     */
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

    /**
     * Constructor from a SAT object. Similar to a copy constructor but making sure that every clause has 3 elements.
     * If not, it is transformed.
     * @param transform
     */
    public SAT3 (SAT transform) {
        super();
        this.setIdentifier(transform.getIdentifier());
        ArrayList<SATClause> adapted = new ArrayList<>();
        for (SATClause temp : transform.getClauses())
            adapted.addAll(temp.transformTo3SAT());
        this.setClauses(adapted);
    }
}
