import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * BufferedIOStream class is to read data using IO technique using byte buffer.
 *
 * @author Sharmik Hirpara
 * @since 07/09/2019
 */

public class BufferedIOStream {

    private static long startTime;                                                                      // For speed benchmarking
    private static long elapsedTime;                                                                    // For speed benchmarking
    /**
     * ReadBufferedIOStream function reads data from input file and checks for keywords
     * and copies data if it contains keywords.
     *
     * @param bufferSize holds size of the byte buffer
     * @param inputFilePath holds name of input text file name
     * @param keywords holds keywords in string array
     * @return void
     * @exception IOException for any error occurrence while performing input/output operation
     * @exception FileNotFoundException if file not found or invalid diractory
     */

    public static void ReadBufferedIOStream(int bufferSize, String inputFilePath, String[] keywords) {

        String output = "";

    	System.out.println("*******************************************************");
    	System.out.println("***Using Buffer I/O stream***");                                            // Using Buffer I/O stream

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFilePath))) {

                byte[] inputData = new byte[bufferSize];                                                // Byte buffer to store data

        		startTime = System.nanoTime();                                                          // Save start time before reading from file

        		while (bis.read(inputData) > 0) {                                                       // Read data from file into Byte array
        			output = new String (inputData);							                        // Convert byte array into string
            		for(int i = 0; i < keywords.length; i++) {                                          // Search for keywords in string
                    	if(output.contains(keywords[i]))									            // Check if string has keyword in it or not
                            HashTableManager.updateHashTable(keywords[i], output);                      // Store result in hashtable
                    }
        		}

        		elapsedTime = System.nanoTime() - startTime;
        		System.out.println("\nElapsed Time is " + (elapsedTime / 1000000.0) + " msec");         // Display time elapsed for this opetaion

                HashTableManager.displayHashTable();                                                    // Display output on console

        	} catch (IOException ex) {
        		ex.printStackTrace();
    	}
    }

    /**
     * saveToTextFile function writes the output in selected text file
     *
     * @param outputFilePath holds name of output text file name
     * @return void
     * @exception IOException for any error occurrence while performing input/output operation
     * @exception FileNotFoundException if file not found or invalid diractory
     */

    public static void saveToTextFile(String outputFilePath) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFilePath))) {
            String output = getOutput();                                                                // Get output from hashtable
            byte[] inputData = new byte[output.getBytes().length];                                      // Declare the size of byte buffer with length of output bytes
            inputData = output.getBytes();                                                              // Store bytes in byte buffer
            bos.write(inputData);                                                                       // Write data from ByteBuffer to file
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
     * ReadTxtFileString function reads text file and returns the string
     *
     * @param inputFilePath path of text file
     * @return result string
     * @exception IOException for any error occurrence while performing input/output operation
     * @exception FileNotFoundException if file not found or invalid diractory
     */
    public String ReadTxtFileString(String inputFilePath) {
    	String result = "";
    	try (BufferedInputStream bis =
    			new BufferedInputStream(new FileInputStream(inputFilePath));
            ) {
    			byte[] inputData = new byte[1024];
        		while (bis.read(inputData) > 0)                            			                    // Read data from file into Byte array
        			result += new String (inputData);								                    // Convert byte array into string
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    	return result;
    }

    /**
     * getOutput function returns the content of hashtable
     *
     * @return String
     */

    public static String getOutput() {
        return "\n***Used Buffer IO Stream method***\n" + HashTableManager.getHashTable();
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
