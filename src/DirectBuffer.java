import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * DirectBuffer class is to read data using NIO technique using direct buffer.
 *
 * @author Sharmik Hirpara
 * @since 31/08/2019
 */

public class DirectBuffer {
	
    private static long startTime, elapsedTime;                                                     // For speed benchmarking
    private static byte[] compareByte;                                                              // For saving byte data from byte buffer to convert into string
    
    /**
     * ReadDirectBuffer function reads data from input file and checks for keywords
     * and copies data if it contains keywords.
     *
     * @param bufferSize holds size of the byte buffer  
     * @param inputFilePath holds name of input text file name
     * @param keywords holds keywords in string array
     * @return void
     * @exception IOException for any error occurrence while performing input/output operation
     * @exception FileNotFoundException if file not found or invalid directory
     */

    public static void ReadDirectBuffer(int bufferSize, String inputFilePath, 
    									String[] keywords) {

    	int count = 0;
        String output = "";
        elapsedTime = 0;

    	System.out.println("*******************************************************");
    	System.out.println("***Using FileChannel with a direct ByteBuffer of " + bufferSize + "***");
    	
    	try (FileChannel fis = new FileInputStream(inputFilePath).getChannel()) {
    		
    		ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(bufferSize);                     // Allocate a "direct" ByteBuffer
    		
            startTime = System.nanoTime();                                                           // Save start time before reading from file
            while (fis.read(directByteBuffer) > 0) {                                                 // Read data using file channel and store it in Direct ByteBuffer
    			count = directByteBuffer.position();                                                 // Get last index of buffer to initialise byte array
    			directByteBuffer.flip();                                                             // Flip the buffer which set the limit to current position, and position to 0.
    			compareByte = new byte[count];                                                       // Initialise byte array to display data
    			directByteBuffer.get(compareByte);	                                                 // Copy data from buffer
    			output = new String (compareByte);                                                   // Covert byte array into string
    			for(int i = 0; i < keywords.length ; i++) {                                          // Search for keywords in string
                    if(output.contains(keywords[i]))                                               // Check if string contains keyword or not
            			HashTableManager.updateHashTable(keywords[i], output);                                        // Save snippet in hashtable
                }
    		    directByteBuffer.clear();                                                            // Clear byte buffer for the next read
    		}

    		elapsedTime = System.nanoTime() - startTime;
    		System.out.println("\nElapsed Time is " + (elapsedTime / 1000000.0) + " msec");          // Display time elapsed for this opetaion
    		
    		HashTableManager.displayHashTable();                                                                      // Display hashtable
    		
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
            String output = getOutput();
            ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(output.getBytes().length);
            directByteBuffer.put(output.getBytes());
            directByteBuffer.flip();
            fos.write(directByteBuffer);
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
        return "\n***Used direct buffer method***\n" + HashTableManager.getHashTable();
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
