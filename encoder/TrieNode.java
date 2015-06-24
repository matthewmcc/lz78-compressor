import java.util.ArrayList;

public class TrieNode {
    public Tuple tup;
    public ArrayList<TrieNode> dataList;
    
    public TrieNode(int phraseNum, byte data) {
        tup = new Tuple(phraseNum, data);
        dataList = new ArrayList<TrieNode>();
    }
    
    /**
     * Checks if a given byte is present in the TrieNode dataList,
     * and moves it to the front of the list
     * @param data, byte to check for
     * @return PhraseNum of tuple if data is present. Else -1
     */
    public int hasData(byte data) {
        // Checks datalist for data, returns the data phrase number if present, else returns -1
        for (int i = dataList.size(); i > 0; i--) {
            if (dataList.get(i - 1).tup.data == data) {
                TrieNode tNode = dataList.remove(i - 1);
                
                dataList.add(tNode);
                return tNode.tup.phraseNum;
            }
        }
        return -1;
    }
    
    /**
     * Adds the given byte data to the front of the arrayList or linkedList
     * @param data
     */
    public void addData(Tuple tup) {
        TrieNode tNode = new TrieNode(tup.phraseNum, tup.data);
        dataList.add(tNode); 
    }
}