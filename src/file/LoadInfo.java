package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class LoadInfo {


    public static void main(String[] args) {
        Path filePath = Paths.get("D:\\Users\\Desktop", "1.txt");
        AtomicInteger i = new AtomicInteger(1);
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(x -> {

                String[] s = x.split("\t");
                String str = String.format("BuyerInf inf%s=new BuyerInf();\n" +
                        "        inf%s.setBuyerId(%sL);\n" +
                        "        inf%s.setBuyerName(\"%s\");\n" +
                        "        buyers.add(inf%s);", i.get(),i.get(), s[0],i.get(), s[1], i.get());
                System.out.println(str);
                i.getAndIncrement();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
