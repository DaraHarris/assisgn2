import java.util.ArrayList;
import java.util.Hashtable;

/**
 * HashTableManager class is responsible to manage all operation related to hashtable
 *
 * @author Sharmik Hirpara
 * @since 13/10/2019
 */

public class HashTableManager {
	
	private static Hashtable<String, ArrayList<String>> hashtable = new Hashtable<>();

    /**
     * getHashTable function returns content of hashtable in string format
     *
     * @return result String
     */

    public static String getHashTable() {
    	String result = "";
    	String[] displayKeys = hashtable.keySet().toArray(new String[hashtable.keySet().size()]);  // Save all keywords in array
		for(int i = 0; i < hashtable.keySet().size(); i++) {
			result += "\n-------------------------------------------------------";
			result += "\nKeyword: " + displayKeys[i];
			for(int j = 0; j < hashtable.get(displayKeys[i]).size(); j++) {
				result += "\nSnippet " + (j+1) + ": "+ hashtable.get(displayKeys[i]).get(j);       // Display all snippets
			}
		}
		result += ("\n-------------------------------------------------------\n");
        return result;
    }

    /**
     * clearHashTable delete all contents from the current hashtable
     *
     * @return void
     */
	public static void clearHashTable() {
		hashtable.clear();
	}

    /**
     * updateHashTable function saves the data in hastable
     *
     * @param keyword keyword which acts as a key of hashtable 
     * @param snippet holds string which contains keyword in it
     * @return void
     */

    public static void updateHashTable(String keyword, String snippet) {
        if(hashtable.containsKey(keyword))                                                         // Check if keyword already exist in hashtable
        	hashtable.get(keyword).add(snippet);                                                   // Put string in hash table along without keyword
        else {
            hashtable.put(keyword, new ArrayList<>());                                             // Put keyword in hashtable and initialise arraylist
            hashtable.get(keyword).add(snippet);                                                   // Add snippet in arraylist
        }
    }

    /**
     * displayHashTable function populates data on console
     *
     * @return void
     */
    
    public static void displayHashTable() {
    	String[] displayKeys = hashtable.keySet().toArray(new String[hashtable.keySet().size()]);  // Save all keywords in array
		for(int i = 0; i < hashtable.keySet().size(); i++) {
            System.out.println("-------------------------------------------------------");
			System.out.println("Keyword: " + displayKeys[i]);
			for(int j = 0; j < hashtable.get(displayKeys[i]).size(); j++) {
				System.out.println("Snippet " + (j+1) + ": " + hashtable.get(displayKeys[i]).get(j));// Display all snippets
			}
		}
        System.out.println("-------------------------------------------------------");
    }
}
