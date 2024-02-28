package code.tmp.krish;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
    private static Map<Integer, Long> memo = new HashMap<>();

    public long findFibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        
        if (!memo.containsKey(n)) {
            memo.put(n, findFibonacci(n - 1) + findFibonacci(n - 2));
        }
        return memo.get(n);
    }
}
