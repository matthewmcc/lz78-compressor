import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * James Luxton 1190809
 * Matthew McCollugh <ID>
 *
 */

public class Bit {
    
    public Bit(){
        
    }
    
    public void pack() throws IOException{
        
        int shell = 0;
        int mask = 0;
        int bitsForPhrase = -1 ;
        int unusedBits = 32;
        int out = 0;
        
        String workingDirectory = System.getProperty("user.dir");
        File tempFile = new File(workingDirectory + File.separator + "out.lz78");
        FileOutputStream fop = new FileOutputStream(tempFile);
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        File input = new File("temp.txt");
        
        Scanner sc = new Scanner(input);
        int phrase = sc.nextInt();
        int val = sc.nextInt();
        boolean cont = true;
        
        while (cont) {
            // set up input
            // declare variables that will be used
            // Use a bits unused structure
            // increment phrasebits
            // pack phrase number
            // pack phrase
            // output one byte of the storage shell if unused bits < 16
            
            bitsForPhrase++;
            int phraseLength = Integer.toBinaryString(bitsForPhrase).length();
            
            // Will mask the phrase number very simply
            mask = phrase;
            mask <<= unusedBits - phraseLength;
            shell |= mask;
            
            unusedBits -= phraseLength;
            
            // Will mask the phrase
            mask = val;
            mask <<= unusedBits - 8;
            shell |= mask;
            //debug(shell);
            unusedBits -= 8;
            
            // this will print out the first byte of the storage int if it is over half full I feel this is the best way to handle overflow
            while (unusedBits < 16) {
                mask = 255;
                mask <<= 24;
                out = shell & mask;
                out >>>= 24;
                //debug(out);
                writer.write(out);
                shell <<= 8;
                unusedBits += 8;
            }
            
            // If another line exists in the lz78 compression go again
            if (sc.hasNextLine()){			
                phrase = sc.nextInt();
                val = sc.nextInt();
            }
            else {
                // otherwise close while
                cont = false;
            }
            
        }
        
        // Write out to file
        writer.writeTo(fop);			
        
        // be a tidy kiwi
        writer.close();
        sc.close();
        
    }		
    
    
    public void debug(int shell){
        System.out.println(String.format("%32s", Integer.toBinaryString(shell)).replace(" ", "0"));
    }
}