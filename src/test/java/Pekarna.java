import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Pekarna {
    public static void main(String[] args) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(new File("pdfs/BB.pdf")));

        char[] buffer = new char[1024];

        Set<char[]> fileSet = new LinkedHashSet<>();
        while (streamReader.ready()){
            int s = streamReader.read(buffer);
            fileSet.add(buffer);
            if (s < 1024){
                buffer = new char[s];
                streamReader.read(buffer);
                fileSet.add(buffer);
            }
            buffer = new char[1024];
        }

        streamReader.close();

        for (char[] chars: fileSet) {
            System.out.println(chars.length);
        }
    }
}
