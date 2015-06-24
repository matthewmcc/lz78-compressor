public class Trie {
    public TrieNode root;				// Root Node of the trie
    public TrieNode currentNode;		// Last Node to be activated
    public int phraseNum;				// Last phrase number to activated
    public int phraseCount;				// Last new phrase number to be added
    
    public Trie() {
        root = new TrieNode(0, (byte) 0);
        currentNode = root;
        phraseNum = 0;
        phraseCount = 1;
    }
    
    /**
     * Adds node to trie if data doesn't exist at current Node,
     * else set current Node to next depths node and store phraseNum
     * @param data
     * @return Tuple to be added else a null tuple.
     */
    public Tuple insert(byte data) {
        
        // Checks if data to be inserted already exists in the arraylist.
        int temp = currentNode.hasData(data);
        
        if (temp == -1) {
            Tuple tup = new Tuple(phraseCount++, data);
            currentNode.addData(tup);
            currentNode = this.root;
            
            Tuple tupR = new Tuple(phraseNum, data);
            phraseNum = 0;
            return tupR;				// Tuple to add to output
        }
        // Node already exists in trie, set currentNode to that node
        else {
            // currentNode = currentNode.dataList.get(currentNode.dataList.size() - 1);
            
            currentNode = currentNode.dataList.get(currentNode.dataList.size() - 1);
            phraseNum = temp;
            
            Tuple tup = null;
            return tup;
        }
    }
    
    // Checks for any left over output when insertion is complete
    public Tuple flush() {
        if (currentNode != root) {
            Tuple tup = currentNode.tup;
            return tup;
        }
        else return null;
    }
}