package code.runner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;

import utils.CommonLogger;


public class Compiler {
	static Logger logger = new CommonLogger(Compiler.class).getLogger();
	
	public boolean compile(String userID, String srcCode, String className) {
		String tmp = "src/main/java/code/tmp/";
		File folder = new File(tmp+userID);
		folder.mkdir();
		
		String packageName = "package code.tmp."+userID+";";
		String fullSrcCode = packageName + "\n" + srcCode;
		String filePath = tmp + userID + "/" + className + ".java";
		
		try {
			
			File javaFile = new File(filePath);
			javaFile.createNewFile();
	        FileWriter fw  = new FileWriter(javaFile);
	        
	        fw.append(fullSrcCode);
	        fw.close();
			
	        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
	        
	        DiagnosticCollector <JavaFileObject> dCollector = new DiagnosticCollector<>();
	        StandardJavaFileManager fManager = javac.getStandardFileManager(dCollector, null, null); 
	        List<File> sourceFiles = new ArrayList<File>();
	        
	        sourceFiles.add(javaFile);
	        
	        Iterable <? extends JavaFileObject> compileUnits = fManager.getJavaFileObjectsFromFiles(sourceFiles);

	        List<String> options = Arrays.asList("-d", "build/classes/");

	        
	        CompilationTask task = javac.getTask(null, fManager, dCollector, options, null, compileUnits);
	        
	        PrintStream originalOut = System.out;
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PrintStream newOut = new PrintStream(baos);
	        System.setOut(newOut);
	        
	        boolean taskStatus = task.call();

	        
	        System.out.println(dCollector.getDiagnostics());
	        System.setOut(originalOut);
	        if (taskStatus) {
	        	System.out.println("Success");
	        	return true;
			} else {
				System.out.println("Compile Error:");
				for (Diagnostic<?> diagnostic : dCollector.getDiagnostics()) {
	                System.out.format("Error on line %d ", diagnostic.getLineNumber());
	                System.out.println(diagnostic.getMessage(null));
	                System.out.println(123);
	            }
			}
	        

		} catch (Exception e) {
			logger.error(e);
		}
		return false;
		
	}
	
	public static void main(String[] args) throws IOException {
		String codeString = "public class Calculator {\n"
				+ "    \n"
				+ "	public int add(int a, int b) {\n"
				+ "        System.out.println(\"num1: \"+a);\n"
				+ "        System.out.println(\"num2: \"+b);\n"
				+ "        return a + b;\n"
				+ "    }\n"
				+ "	\n"
				+ "}";
		
		String userID = "krish";
		
		Compiler obj = new Compiler();
		obj.compile(userID, codeString, "Calculator");
		
		
	}
	
	

	public static String readFromFile(String filepath) throws Exception {
		File codeFile = new File(filepath);
		if (!codeFile.exists()) {
			logger.error("File not found - "+filepath);
			throw new Exception("File not found!");
		}
		
		StringBuilder content = new StringBuilder();
		try {
            Files.readAllLines(Paths.get(filepath)).forEach(line -> content.append(line).append("\n"));
            return content.toString();
        } catch (IOException e) {
        	logger.error("Can't read from "+filepath+": "+e);
            throw new Exception("Error reading from file.");
        }

		
	}
}
