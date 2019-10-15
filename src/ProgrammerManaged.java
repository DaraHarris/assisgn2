import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ProgrammerManaged class is to read data using IO technique using byte buffer.
 *
 * @author Sharmik Hirpara
 * @since 07/09/2019
 */

public class ProgrammerManaged {

    private static long startTime, elapsedTime;                                                 // For speed benchmarking
    
    /**
     * ReadProgrammerManaged function reads data from input file and checks for keywords
     * and copies data if it contains keywords.
     *
     * @param bufferSize holds size of the byte buffer  
     * @param inputFilePath holds name of input text file name
     * @param outputFilePath holds name of output text file name
     * @param keywords holds keywords in string array
     * @return void
     * @exception IOException for any error occurrence while performing input/output operation
     * @exception FileNotFoundException if file not found or invalid diractory
     */

    public static void ReadProgrammerManaged(int bufferSize, String inputFilePath, 
                                             String outputFilePath, String[] keywords) {

        String output = "";

    	System.out.println("*******************************************************");
    	System.out.println("***Using programmer-managed byte-array of " + bufferSize + "***");  // Using FileChannel with direct ByteBuffer
    	
        try (FileInputStream fis = new FileInputStream(inputFilePath)) {
    		
        	byte[] inputData = new byte[bufferSize];
    		
            startTime = System.nanoTime();                                                  // Save start time before reading from file
    		
            while (fis.read(inputData) != -1) {                             		        // Read data from file into ByteBuffer
    			output = new String (inputData);							                // Convert byte buffer into string
        		for(int i = 0; i < keywords.length; i++) {                                  // Search for keywords in string
                	if(output.contains(keywords[i])) {									    // Check if string contains keyword or not
                        HashTableManager.updateHashTable(keywords[i], output);                                        // Save snippet in hashtable
        			}
                }
    		}
    		
            elapsedTime = System.nanoTime() - startTime;
    		System.out.println("\nElapsed Time is " + (elapsedTime / 1000000.0) + " msec");     // Display time elapsed for this opetaion
            
            HashTableManager.displayHashTable();

    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    }

    /**
     * doubleToByteArray function converts double into string and then into byte array
     *
     * @param time holds time elapsed for this method
     * @return byte array
     */

    public static byte[] doubleToByteArray(double time) {
    	String data = "\n***Elapsed Time is " + Double.toString(time) + " msec***";
        byte[] timeBuffer = new byte[data.getBytes().length];
        timeBuffer = data.getBytes();
        return timeBuffer;
    }

    /**
     * saveToTextFile function writes the output in selected text file
     *
     * @param outputFilePath holds name of output text file name
     * @return void
     */

    public static void saveToTextFile(String outputFilePath) {
        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            String output = getOutput();
            byte[] inputData = new byte[output.getBytes().length];
            inputData = output.getBytes();
            fos.write(inputData);                                               // Write data from ByteBuffer to file
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * doubleToByteArray function converts double into string and then into byte array
     *
     * @param time holds time elapsed for this method
     * @return ByteBuffer
     */

    public static String getOutput() {
        return "\n***Used Programmer-managed byte-array method***\n" + HashTableManager.getHashTable();
    }
    
    /**
     * getElapsedTime function returns elapsed time using direct buffer method
     *
     * @return time in string (msec)
     */

    public static String getElapsedTime() {
        return Double.toString(elapsedTime / 1000000.0);
    }
}
