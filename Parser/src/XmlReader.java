import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Дана строка, содержащая следующий текст (xml-документ): (текст строки в задании)
//Напишите анализатор, позволяющий последовательно возвращать содержимое узлов xml-документа и его тип
//(открывающий тег, закрывающий тег, содержимое тега, тег без тела).
//Пользоваться готовыми парсерами XML для решения данной задачи нельзя.

public class XmlReader {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream("D:\\123.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String xml;
        while (reader.ready()) {
            builder.append(reader.readLine());
        }
        xml = builder.toString();
        if(isXml(xml) && validateXml(xml)) {
            new XmlParser(xml);
        }
        else System.out.println("Invalid data");
    }

    private static boolean isXml(String xml) {
        Pattern pattern = Pattern.compile("<[\\w]+[\\s]*(.)*>(.)*</[\\w]+>");
        Matcher matcher = pattern.matcher(xml);
        return matcher.matches();
    }

    private static boolean validateXml(String xml) {
        int count1 = 0;
        int count2 = 0;
        char[] symbols;
        if(xml.contains(" & ")) return false;
        if(xml.startsWith("<") && xml.endsWith(">")) {
            symbols = xml.toCharArray();
            for (Character c : symbols) {
                if(c.equals('<')) count1++;
                if(c.equals('>')) count2++;
            }
        } else return false;
        return (count1 == count2);
    }
}
