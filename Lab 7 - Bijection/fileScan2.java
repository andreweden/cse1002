import java.util.Scanner;
import java.io.File; 

public final class fileScan2 {
   	public static void main (final String[] args) {
   		// Declare key of 64 characters
		        final String[] keyArray = {"1","2","3","4","5","6","7","8","9","A",
		                                   "B","C","D","E","F","G","H","I","J","K",
		                                   "L","M","N","O","P","Q","R","S","T","U",
		                                   "V","W","X","Y","Z","a","b","c","d","e",
		                                   "f","g","h","i","j","k","l","m","n","o",
		                                   "p","q","r","s","t","u","v","w","x","y",
		                                   "z","<","#",">"};

      	// Declare initial bases
      	long baseA = 0;
   		long baseB = 0;

		try  {    
			Scanner sc = new Scanner(new File("input.txt"));    
			while (sc.hasNextLine())        

      		// Declare temporary values
            int tempInt = 0;
            int tempExp = 0;
            long decimal = 0;
            String temp1 = "0";
            String temp2 = "0";
            long result = 0;
            long remain = 0;
            String remainString = "";
            String endString = "";

            // Find inputed values for a, b, and n 
            String a = sc.next();
            String n = sc.next();
            String b = sc.next();

            // Find base A
            for (int i = 0; i < keyArray.length; i++){
               if (a.equals(keyArray[i])) {
                  baseA = i + 1;
               }
            }
            System.out.println("BaseA: " + baseA);
      		    
		} catch(Exception e) {  
				e.printStackTrace();  
		}  
	}
}