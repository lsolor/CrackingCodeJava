import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordDistance {

    private HashMap<String,Integer> DictHashMap = new LinkedHashMap<>();
    private String inputFile;
    private String wordA;
    private String wordB;


    public WordDistance(String inputFile, String wordA, String wordB){
        this.wordA = wordA;
        this.wordB = wordB;
        this.inputFile = inputFile;

        CreateDictionary();

        if(!DictHashMap.containsKey(wordA) || !DictHashMap.containsKey(wordB)){
            System.err.println("Both words are not in file");
            return;
        }

        Collection<Integer> ListA = DictHashMap.values();
        Collection<Integer> ListB = DictHashMap.values();



    }


    // Go through entire file, and word and index mapping
    // Updates DictHashMap with these values
    private void CreateDictionary() {
        int index = 0;
        try {
            Scanner scanner = new Scanner(new File(this.inputFile));
            while(scanner.hasNext())
            {
                String currentWord = scanner.next();
                currentWord = currentWord.toLowerCase();
                DictHashMap.put(currentWord,index);
                index++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




    }


}
