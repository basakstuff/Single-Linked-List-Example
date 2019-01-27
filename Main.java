import java.util.concurrent.ThreadLocalRandom;
public class Main {

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int n = random.nextInt(4) + 1;
        Function f1 = Function.random(7, 6);
        Function f2 = Function.random(7, 6);
        Function fSum = f1.sum(f2);

        System.out.println("Random Function Generation");
        System.out.println("==========================");
        System.out.println(f1);
        System.out.println(f2);

        System.out.println();
        System.out.println("Sum of Functions");
        System.out.println("==========================");
        System.out.println(fSum);

        System.out.println();
        System.out.println(String.format("%dth Derivatives of f3", n));
        System.out.println("=====================");

        System.out.println("By X");
        System.out.println(fSum.takeDerivative(Function.DerivativeBy.X, n));
        System.out.println();
        System.out.println("By Y");
        System.out.println(fSum.takeDerivative(Function.DerivativeBy.Y, n));
    }
}
