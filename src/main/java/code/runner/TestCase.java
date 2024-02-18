package code.runner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import utils.CommonLogger;

public class TestCase {
	static Logger logger = new CommonLogger(TestCase.class).getLogger();
    private String methodName;
    private Object[] parameters;
    private Class<?>[] parameterTypes;
    private Object expectedOutput;
    private Object targetObject; 

    public TestCase(Object targetObject, String methodName, Object expectedOutput, Object... parameters) {
        this.targetObject = targetObject;
        this.methodName = methodName;
        this.parameters = parameters;
        this.parameterTypes = new Class<?>[parameters.length];
        this.expectedOutput = expectedOutput;
    }

    public Callable<JSONObject> toCallable() {
        return () -> {
        	JSONObject resultObject = new JSONObject();

            try {
            	// preparing seperate printstream for get all logs
            	PrintStream originalOut = System.out;
            	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            	PrintStream newOut = new PrintStream(baos);
            	System.setOut(newOut);
            	
                convertParametersType(parameters, parameterTypes);
                
                
                Method method = targetObject.getClass().getMethod(methodName, parameterTypes);
                
                Object result = method.invoke(targetObject, parameters);
                
                System.setOut(originalOut);
                String logs = baos.toString();
                
                
                boolean isPass = compareObjects(expectedOutput, result);
                
                
                if (isPass) {
                	resultObject.put("status", isPass);
                	resultObject.put("logs", logs);
                } else {
                	resultObject.put("status", isPass);
                	resultObject.put("message", String.format("Test Failed: Expected %s, got %s", expectedOutput, result));
                	resultObject.put("logs", logs);
                }
                
            } catch (Exception e) {
            	logger.error(e);
            }
            return resultObject;
        };
    }
    
    
    
    public static boolean compareObjects(Object a, Object b) {
    	if (a == b) {
            return true;
        }
    	else if (a.getClass().isArray() && b.getClass().isArray()) {
			return comparePrimitiveArrays(a, b);
		}else if (a instanceof Map && b instanceof Map) {
            return compareMaps((Map<?, ?>) a, (Map<?, ?>) b);
        }else {
			return b.equals(a);
		}

    }
    
    
    private static boolean compareMaps(Map<?, ?> a, Map<?, ?> b) {
    	for (Map.Entry<?, ?> entry : a.entrySet()) {
            Object key = entry.getKey();
            Object valueA = entry.getValue();
            Object valueB = b.get(key);

            if (!Objects.equals(valueA, valueB)) {
                return false;
            }
        }
        return true;

	}

	private static boolean comparePrimitiveArrays(Object a, Object b) {
        return Arrays.deepEquals((Object[]) a, (Object[]) b);
    }
    
    private static void convertParametersType(Object[] params, Class<?>[] paramTypes) {
        
        for (int i = 0; i < params.length; i++) {
            Object parameter = params[i];
            Class<?> parameterType = parameter.getClass();
            
            if (parameterType == Integer.class) {
            	params[i] = (int) parameter;
            	paramTypes[i] = int.class;
            }
            else if (parameterType == Boolean.class) {
            	params[i] = (boolean) parameter;
            	paramTypes[i] = boolean.class;
            }
            else if (parameterType == Byte.class) {
            	params[i] = (byte) parameter;
            	paramTypes[i] = byte.class;
            }
            else if (parameterType == Character.class) {
            	params[i] = (char) parameter;
            	paramTypes[i] = char.class;
            }
            else if (parameterType == Double.class) {
            	params[i] = (double) parameter;
            	paramTypes[i] = double.class;
            }
            else if (parameterType == Float.class) {
            	params[i] = (float) parameter;
            	paramTypes[i] = float.class;
            }
            else if (parameterType == Long.class) {
            	params[i] = (long) parameter;
            	paramTypes[i] = long.class;
            }
            else if (parameterType == Short.class) {
            	params[i] = (short) parameter;
            	paramTypes[i] = short.class;
            }
            
        }

    }




}

