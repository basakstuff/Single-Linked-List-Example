public class Term {

    private int coefficient;
    private int exponentX;
    private int exponentY;

    public Term(int coefficient, int exponentX, int exponentY) {
        this.coefficient = coefficient;
        this.exponentX = exponentX;
        this.exponentY = exponentY;
    }

    public int get(Function.DerivativeBy by) {
        switch (by) {
            case X:
                return exponentX;
            case Y:
                return exponentY;
        }

        return coefficient;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getExponentX() {
        return exponentX;
    }

    public void setExponentX(int exponentX) {
        this.exponentX = exponentX;
    }

    public int getExponentY() {
        return exponentY;
    }

    public void setExponentY(int exponentY) {
        this.exponentY = exponentY;
    }
}
