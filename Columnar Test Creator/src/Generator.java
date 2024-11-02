import java.io.File;
import java.io.IOException;
import java.util.*;

public class Generator {
    public static void main(String[] args) throws IOException {
        Scanner fileRead = new Scanner(new File("Quotes.txt"));
        String file = "";
        ArrayList<String[]> splitQuotes = new ArrayList<>();
        while(fileRead.hasNextLine()) file += fileRead.nextLine();
        String[] wholeQuotes = file.split("@");
        for(String wholeQuote : wholeQuotes) splitQuotes.add(wholeQuote.split("/"));
        Collections.shuffle(splitQuotes);
        //for(String[] quote : splitQuotes) System.out.println(quote[0] + " - " + quote[1]);
        for(String[] quote : splitQuotes) System.out.println(createProblem(quote) + "\n\n");
    }
    private static String createProblem(String[] quote) {
        String spacedText = quote[0].replaceAll("[^a-zA-Z|\\u0020]", ""),
                text = spacedText.replaceAll("\\u0020", "");
        Random numGen = new Random();
        int columns = numGen.nextInt(7) + 5, numX = (columns-(text.length() % columns))%columns;
        for(int i = 0; i < numX; i++) text += "x";
        text = text.toUpperCase();
        ArrayList<Integer> columnOrder = new ArrayList<>();
        for(int i = 0; i < columns; i++) columnOrder.add(i);
        Collections.shuffle(columnOrder);
        String encoded = "";
        for(int columnNum : columnOrder) {
            for(int i = columnNum; i < text.length(); i+=columns) {
                encoded += text.charAt(i);
                //System.out.println(text.charAt(i) + " " + encoded.length());
                if(encoded.length() % 48 == 47) {
                    encoded += "\n";
                    //System.out.println("newline " + encoded.length());
                } else if(encoded.length() % 6 == 5) {
                    encoded += " ";
                    //System.out.println("space " + encoded.length());
                }
            }
        }
        ArrayList<String> possibleCribs = new ArrayList<>();
        int tolerance = 0;
        while(true) {
            StringTokenizer cribFind = new StringTokenizer(spacedText, " ");
            while(cribFind.hasMoreTokens()) {
                String word = cribFind.nextToken();
                if(word.length() >= columns-3-tolerance && word.length() <= columns+3+tolerance) possibleCribs.add(word);
            }
            if(!possibleCribs.isEmpty()) break;
            tolerance++;
        }
        Collections.shuffle(possibleCribs);
        return "Solve this Complete Columnar of a quote by " + quote[1] + " given a crib of "
                + possibleCribs.get(0).toUpperCase() + ":\n\n" + encoded;
    }
}
