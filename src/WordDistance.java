import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/*
Word Distance: You have a large text file containing words. Given any two words, find the shortest distance (in
 terms of number of words) beetween them in the file. If the operation will be repeated many times for the same file
  (but diffferent pairs of words), can you optimize your solution?
 */
public class WordDistance {

    private HashMap<String,ArrayList<Integer>> DictHashMap = new HashMap<>();
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

        ArrayList<Integer> ListA = DictHashMap.get(wordA);
        ArrayList<Integer> ListB = DictHashMap.get(wordB);
        ArrayList<Integer> merged = mergeList(ListA, ListB);

        int greatestDiff = CalculateDifferences(merged);
        System.out.println(greatestDiff);

    }

    private int CalculateDifferences(ArrayList<Integer> merged) {
        int greatestDiff= Integer.MIN_VALUE;

        for(int i=0; i <merged.size()-1; i++){
            int diff = merged.get(i+1) - merged.get(i);
            if(diff> greatestDiff) greatestDiff = diff;
        }
        return greatestDiff;
    }

    private ArrayList<Integer> mergeList(ArrayList<Integer> ListA, ArrayList<Integer> ListB) {
        ArrayList<Integer> mergedList = new ArrayList<>();

        Boolean AddedALast = false;
        Boolean AddedBLast = false;

        int indexA = 0;
        int indexB =0;
        int index = 0;

        while(indexA < ListA.size() && indexB < ListB.size()){
            int currentA = ListA.get(indexA);
            int currentB = ListB.get(indexB);

            // haven't added anything yet
            if(!AddedALast && !AddedBLast){
                if( currentA < currentB ){
                    mergedList.add(currentA);
                    indexA++;
                    AddedALast = true;
                }
                else{
                    mergedList.add(currentB);
                    indexB++;
                    AddedBLast = true;
                }
                index++;
            }
            //index from listA was the last to be added
            else if(AddedALast){
                //replace previously added currentA with new currentA
                if(currentA < currentB){
                    mergedList.set(index, currentA);
                    indexA++;
                }
                //add currentB and changed flags
                else{
                    mergedList.add(currentB);
                    indexB++;
                    AddedALast = false;
                    AddedBLast = true;
                }
                index++;
            }
            //index from listB was the last to be added
            else{
                //replace previously added currentB with new currentB
                if(currentB < currentA){
                    mergedList.set(index,currentB);
                    indexB++;
                }
                //add currentA and change flags
                else{
                    mergedList.add(currentA);
                    indexA++;
                    AddedALast = true;
                    AddedBLast = false;
                }
                index++;
            }

        }

        //Check to see if we can add one more item

        // Check to see if there is one more B we can add
        if(AddedALast){
            //if there are more B values and the B value is greater than the last added A value then add it
            if(indexB < ListB.size() && ListA.get(indexA) > mergedList.get(index)){
                mergedList.add(ListB.get(indexB));

            }
        }
        // Check to see if there is one more A we can add
        else{
            //if there are more A values and the A value is greater than the last added B value then add it
            if(indexA < ListA.size() && ListA.get(indexA)> mergedList.get(index)){
                mergedList.add(ListA.get(indexA));
            }
        }
        return mergedList;
    }


    // Go through entire file, and add word and index mappings
    // Updates DictHashMap with these values
    private void CreateDictionary() {
        int index = 0;
        try {
            Scanner scanner = new Scanner(new File(this.inputFile));
            while(scanner.hasNext())
            {
                String currentWord = scanner.next();
                currentWord = currentWord.toLowerCase();

                //existing key ... add to that arraylist
                if(DictHashMap.containsKey(currentWord)){
                    DictHashMap.get(currentWord).add(index);
                }

                //nonexisting key ... create new arraylist with that index added
                else{
                    ArrayList<Integer> newList = new ArrayList<Integer>();
                    newList.add(index);
                    DictHashMap.put(currentWord,newList);
                }

                index++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




    }


}
