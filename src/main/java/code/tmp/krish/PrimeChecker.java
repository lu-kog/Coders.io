package code.runner;

public class PrimeChecker {

    public static boolean isPrime(int a) {
        if (a < 2) {
            return false;
        }
        
        for (int i = 2; i <= Math.sqrt(a); i++) {
            if (a % i == 0) {
                return false;
            }
        }
        
        return true;
    }
}
