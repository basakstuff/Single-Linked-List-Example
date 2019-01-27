import java.util.concurrent.ThreadLocalRandom;

public class Function {

    private static int functionCount = 0;
    private TermNode head;
    public Function() {
        head = null;
    }

    private static Function derivative(Function f, DerivativeBy by) {
        Function out = new Function();

        TermNode n = f.head;
        while (n != null) {
            Term term = n.data;
            if (term.get(by) == 0) {
                n = n.next;
                continue;
            }

            int x = term.getExponentX();
            int y = term.getExponentY();

            if (by == DerivativeBy.X)
                x -= 1;
            if (by == DerivativeBy.Y)
                y -= 1;

            out.add(
                    term.getCoefficient() * term.get(by),
                    x,
                    y
            );

            n = n.next;
        }

        return out;
    }

    public static Function random(int termCount, int rangeMax) {
        Function out = new Function();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        rangeMax++;
        for (int i = 0; i < termCount; i++) {
            out.add(
                    random.nextInt(rangeMax),
                    random.nextInt(rangeMax),
                    random.nextInt(rangeMax)
            );
        }

        return out;
    }

    public Function takeDerivative(DerivativeBy by, int n) {
        Function out = derivative(this, by);

        for (int i = 0; i < n - 1; i++) {
            out = derivative(out, by);
        }

        return out;
    }

    public Function sum(Function f2) {
        Function out = new Function();

        TermNode t1 = head;
        while (t1 != null) {
            boolean foundTerm = false;
            TermNode t2 = f2.head;
            while (t2 != null) {
                int x1 = t1.data.getExponentX(),
                        x2 = t2.data.getExponentX(),
                        y1 = t1.data.getExponentY(),
                        y2 = t2.data.getExponentY();

                if (x1 == x2 && y1 == y2) {
                    out.add(t1.data.getCoefficient() + t2.data.getCoefficient(), x1, y1);
                    foundTerm = true;
                }

                t2 = t2.next;
            }

            if (!foundTerm) {
                out.add(t1.data.getCoefficient(), t1.data.getExponentX(), t1.data.getExponentY());
            }
            t1 = t1.next;
        }

        t1 = f2.head;
        while (t1 != null) {
            if (!out.contains(t1.data))
                out.add(t1.data.getCoefficient(), t1.data.getExponentX(), t1.data.getExponentY());

            t1 = t1.next;
        }

        return out;
    }

    public boolean contains(Term term) {
        TermNode tmp = head;

        while (tmp != null) {
            Term t = tmp.data;

            if (t.getExponentX() == term.getExponentX() && t.getExponentY() == term.getExponentY())
                return true;

            tmp = tmp.next;
        }

        return false;
    }

    protected void sort() {
        boolean swapped = true;
        while (swapped) {
            swapped = false;

            TermNode n = head;
            while (n.getNext() != null) {
                if (
                        n.data.getExponentX() < n.getNext().data.getExponentX() ||
                                (
                                        n.data.getExponentX() == n.getNext().data.getExponentX() &&
                                                n.data.getExponentY() < n.getNext().data.getExponentY()
                                )
                        ) {
                    swap(n, n.getNext());
                    swapped = true;
                }

                n = n.getNext();
            }
        }
    }

    protected void swap(TermNode n1, TermNode n2) {
        Term tmp = n1.data;
        n1.data = n2.data;
        n2.data = tmp;
    }

    public void add(int coefficient, int exponentX, int exponentY) {
        Term newTerm = new Term(coefficient, exponentX, exponentY);
        TermNode newNode = new TermNode(newTerm);

        if (head == null) {
            head = newNode;
            return;
        }

        TermNode tmp = head;
        while (tmp.getNext() != null) {
            tmp = tmp.getNext();
        }

        tmp.setNext(newNode);
        sort();
    }

    @Override
    public String toString() {
        TermNode tmp = head;
        StringBuilder sb = new StringBuilder();

        sb.append("f");
        sb.append(++functionCount);
        sb.append("(x, y) = ");

        if (head == null) {
            sb.append("0");
            return sb.toString();
        }

        while (tmp != null) {
            // coefficient
            switch (tmp.data.getCoefficient()) {
                case -1:
                    sb.append("-");
                    break;
                case 0:
                case 1:
                    break;
                default:
                    sb.append(tmp.data.getCoefficient());
            }

            // x
            if (tmp.data.getExponentX() != 0) {
                sb.append("x");

                if (tmp.data.getExponentX() != 1) {
                    sb.append("^");
                    sb.append(tmp.data.getExponentX());
                }
            }

            // y
            if (tmp.data.getExponentY() != 0) {
                sb.append("y");

                if (tmp.data.getExponentY() != 1) {
                    sb.append("^");
                    sb.append(tmp.data.getExponentY());
                }
            }

            sb.append(" + ");
            tmp = tmp.getNext();
        }

        String out = sb.toString();
        return out.substring(0, out.length() - 3);
    }

    enum DerivativeBy {
        X, Y, K
    }

    static class TermNode {
        private Term data;
        private TermNode next;

        public TermNode(Term data) {
            this.data = data;
        }

        public TermNode(Term data, TermNode next) {
            this.data = data;
            this.next = next;
        }

        public Term getData() {
            return data;
        }

        public void setData(Term data) {
            this.data = data;
        }

        public TermNode getNext() {
            return next;
        }

        public void setNext(TermNode next) {
            this.next = next;
        }
    }

}
