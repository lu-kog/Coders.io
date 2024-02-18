package code.runner;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONObject;

public class TestRunner {
    public static void main(String[] args) throws Exception {
        String userid = "krish";
        String className = "Calculator";
    	// have to create object in run time
    	
    	Class<?> cls = Class.forName("code.tmp."+userid+"."+className);
		Object calculator = cls.getDeclaredConstructor().newInstance();

        
        System.out.println(calculator.getClass());
        
        
        List<TestCase> testCases = Arrays.asList(
        	            new TestCase(calculator, "add", 3, 2, 1),   
        	            new TestCase(calculator, "add", 8, 5, 3),   
        	            new TestCase(calculator, "add", 1, 0, 0),
        	            new TestCase(calculator, "add", 10, 6, 4),   
        	            new TestCase(calculator, "add", 15, 9, 6),  
        	            new TestCase(calculator, "add", 100, 50, 50),  
        	            new TestCase(calculator, "add", 31, 11, 10),   
        	            new TestCase(calculator, "add", 12, 7, 5),   
        	            new TestCase(calculator, "add", 30, 20, 10),  
        	            new TestCase(calculator, "add", 33, 15, 18),
        	            new TestCase(calculator, "add", 28, 8, 20),   
        	            new TestCase(calculator, "add", 1, 0, 1),   
        	            new TestCase(calculator, "add", 40, 30, 10),   
        	            new TestCase(calculator, "add", 9, 3, 3),  
        	            new TestCase(calculator, "add", 17, 12, 5),
        	            new TestCase(calculator, "add", 23, 10, 13),   
        	            new TestCase(calculator, "add", 37, 23, 14),   
        	            new TestCase(calculator, "add", 44, 20, 24),   
        	            new TestCase(calculator, "add", 9, 5, 4),  
        	            new TestCase(calculator, "add", 35, 25, 10),   
        	            new TestCase(calculator, "add", 39, 20, 19),   
        	            new TestCase(calculator, "add", 16, 9, 7),  
        	            new TestCase(calculator, "add", 29, 22, 7),  
        	            new TestCase(calculator, "add", 19, 14, 5),   
        	            new TestCase(calculator, "add", 42, 30, 12),  
        	            new TestCase(calculator, "add", 5, 2, 3),   
        	            new TestCase(calculator, "add", 50, 30, 20),  
        	            new TestCase(calculator, "add", 14, 9, 5)
       	 );

        ExecutorService executor = Executors.newFixedThreadPool(100);
        List<Callable<JSONObject>> tasks = testCases.stream()
                                                .map(TestCase::toCallable)
                                                .toList();

        
        System.out.println("Test");
        
        List<Future<JSONObject>> results = executor.invokeAll(tasks);
        System.out.println("Test");

        
        System.out.println("Results:");
        // Print test results
        for (Future<JSONObject> result : results) {
            System.out.println(result.get());
        }
        
        System.out.println("Done");
    }
}

