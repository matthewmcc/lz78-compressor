import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Bit {
    
    public Bit() throws IOException {
        
    }
    
    public void unpack() throws IOException {
        
        // declare variables that will be used
        int nextByte = System.in.read();
        int unusedBits = 32;
        int bitsForPhrase = -1;
        int shell = 0;
        int index = 0;
        int mask = 0;
        int phrase = 0;
        int phraseLength = 0;
        boolean cont = true;
        
        // declare reader/writers
        String workingDirectory = System.getProperty("user.dir");
        File tempFile = new File(workingDirectory + File.separator + "unpack.file");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        
        // fill the shell
        while (unusedBits != 0) {
            nextByte <<= (unusedBits - 8);
            shell |= nextByte;
            unusedBits -= 8;
            nextByte = System.in.read();
        }
        
        while (cont) {
            
            
            // increment phrase bits
            bitsForPhrase++;
            phraseLength = Integer.toBinaryString(bitsForPhrase).length();
            
            // read in phrase number
            mask = -1;
            mask <<= 32 - phraseLength;
            index = shell & mask;
            shell <<= phraseLength;
            
            // store value for printing later
            
            index >>>= 32 - phraseLength;
            
            // increment unusedBits
            unusedBits += phraseLength;
            
            // read in next byte
            mask = 255;
            mask <<= 24;
            phrase = shell & mask;
            shell <<= 8;
            
            unusedBits += 8;
            // store int value of byte to be printed later
            phrase >>>= 24;
            
            // output both values to file
            writer.write(Integer.toString(index) + " ");
            writer.write(Integer.toString(phrase) + '\n');
            writer.flush();
            
            while (unusedBits > 16) {
                nextByte <<= (unusedBits - 8);
                shell |= nextByte;
                unusedBits -= 8;
                nextByte = System.in.read();
                if (nextByte == 0) {
                    // increment phrase bits
                    bitsForPhrase++;
                    phraseLength = Integer.toBinaryString(bitsForPhrase).length();
                    
                    // read in phrase number
                    mask = -1;
                    mask <<= 32 - phraseLength;
                    index = shell & mask;
                    shell <<= phraseLength;
                    
                    // store value for printing later
                    
                    index >>>= 32 - phraseLength;
                    
                    // increment unusedBits
                    unusedBits += phraseLength;
                    
                    // read in next byte
                    mask = 255;
                    mask <<= 24;
                    phrase = shell & mask;
                    shell <<= 8;
                    
                    unusedBits += 8;
                    // store int value of byte to be printed later
                    phrase >>>= 24;
                    
                    // output both values to file
                    writer.write(Integer.toString(index) + " ");
                    writer.write(Integer.toString(phrase) + '\n');
                    writer.flush();
                    cont = false;
                }
            }
        }
        writer.close();
    }
    
    public void debug(int shell){
        System.out.println(String.format("%32s", Integer.toBinaryString(shell)).replace(" ", "0"));
    }
}