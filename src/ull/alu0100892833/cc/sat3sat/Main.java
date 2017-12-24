package ull.alu0100892833.cc.sat3sat;

public class Main {

    public static void main(String[] args) {
        String sat1 = "A = {z1, z2, ¬z6} ^ {¬z1, ¬z2} ^ {z3, ¬z4, ¬z5, z1, ¬z2, z7} ^ {z6}";
        SAT instance = new SAT(sat1);
        SAT3 adapted = new SAT3(instance);
        System.out.println(adapted.toString());
        System.out.println(adapted.getUSet());
    }
}
