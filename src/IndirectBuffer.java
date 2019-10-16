import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * IndirectBuffer class is to read data using NIO technique using indirect buffer.
 *
 * @author Sharmik Hirpara
 * @since 07/09/2019
 */

public class IndirectBuffer {

    private static long startTime, elapsedTime;                                                         // For speed benchmarking
    
    /**
     * ReadIndirectBuffer function reads data from input file and checks for keywords
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

    public static void ReadIndirectBuffer(int bufferSize, String inputFilePath, 
                                          String[] keywords) {

        int count = 0;
        String output = "";

		System.out.println("*******************************************************");
    	System.out.println("***Using FileChannel with a indirect ByteBuffer of " + bufferSize + "***"); // Using FileChannel with indirect ByteBuffer
    	
        try (FileChannel fis = new FileInputStream(inputFilePath).getChannel()) {
        	
            	ByteBuffer indirectByteBuffer = ByteBuffer.allocate(bufferSize);                        // Allocate a "indirect" ByteBuffer

        		startTime = System.nanoTime();                                                          // Save start time before reading from file

        		while (fis.read(indirectByteBuffer) > 0) {                                              // Read data from file into ByteBuffer
        			count = indirectByteBuffer.position();                                              // Get last index of buffer
        			indirectByteBuffer.flip();                                                          // Flip the buffer which set the limit to current position, and position to 0.
        			byte[] compareByte = new byte[count];                                               // Initialise byte array to display data
        			indirectByteBuffer.get(compareByte, 0, count);	                                    // Copy data from buffer
        			output = new String (compareByte);                                                  // Convert byte buffer into string
            		for(int i = 0; i < keywords.length ; i++)                                           // Search for keywords in string
                    	if(output.contains(keywords[i])) {                                              // Check if string contains keyword or not
                            HashTableManager.updateHashTable(keywords[i], output);
                    }
        			indirectByteBuffer.clear();                                                         // Clear byte buffer for the next read
        		}

        		elapsedTime = System.nanoTime() - startTime;
        		System.out.println("\nElapsed Time is " + (elapsedTime / 1000000.0) + " msec");         // Display time elapsed for this opetaion

                HashTableManager.displayHashTable();

        	} catch (IOException ex) {
        		ex.printStackTrace();
    	   }
    }

    /**
     * saveToTextFile function writes the output in selected text file
     *
     * @param outputFilePath holds name of output text file name
     * @return void
     */

    public static void saveToTextFile(String outputFilePath) {
        try (FileChannel fos = new FileOutputStream(outputFilePath).getChannel()) {
            String output = getOutput();                                                                // Get output from hashtable
            ByteBuffer indirectByteBuffer = ByteBuffer.allocateDirect(output.getBytes().length);        // Declare the size of byte buffer with length of output bytes
            indirectByteBuffer.put(output.getBytes());                                                  // Store bytes in byte buffer
            indirectByteBuffer.flip();                                                                  // Flip buffer to reset index
            fos.write(indirectByteBuffer);                                                              // Write data into text file
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
    
    public static ByteBuffer doubleToByteArray(double time) {
    	String data = "\n***Elapsed Time is " + Double.toString(time) + " msec***";
        ByteBuffer timeBuffer = ByteBuffer.allocateDirect(data.getBytes().length);
        timeBuffer.put(data.getBytes());
        timeBuffer.flip();
        return timeBuffer;
    }

    /**
     * doubleToByteArray function converts double into string and then into byte array
     *
     * @param time holds time elapsed for this method
     * @return ByteBuffer
     */

    public static String getOutput() {
        return "\n***Used indirect buffer method***\n" + HashTableManager.getHashTable();
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
