import java.util.ArrayList;
import java.util.List;

//Дана строка, содержащая следующий текст (xml-документ): (текст строки в задании)
//Напишите анализатор, позволяющий последовательно возвращать содержимое узлов xml-документа и его тип
//(открывающий тег, закрывающий тег, содержимое тега, тег без тела).
//Пользоваться готовыми парсерами XML для решения данной задачи нельзя.

public class XmlParser {
    public XmlParser(String xml) {
        getTags(xml);
    }

    private static void getTags(String xml) {
        StringBuilder tag = new StringBuilder();
        List<String> tags = new ArrayList<>();
        char[]symbols = xml.toCharArray();
        for (int i = 0; i < symbols.length; i++) {
            for(int j = i + 1; j < symbols.length; j++) {
                if(symbols[i] == '<' && symbols[j] != '/' && symbols[j] != '>') tag.append(symbols[j]);
                else if(symbols[i] == '<' && j < symbols.length - 1 && symbols[j + 1] == '>' && symbols[j] == '/')
                    tag.append(symbols[j]);
                else break;
            }
            if(tag.toString().length() > 0) tags.add(tag.toString());
            tag = new StringBuilder();
        }
        getContentOfTags(xml, tags);
    }

    private static void getContentOfTags(String xml, List<String> tags) {
        int firstTagIndex;
        int lastTagIndex;
        int tempFirst = 0;
        String openTag;
        String closingTag;
        String tagBody;
        String[] closingTags;
        for (int i = 0; i < tags.size(); i++) {
            if(tags.get(i).contains("/")) {
                openTag = "<" + tags.get(i) + ">";
                if(i == 0) firstTagIndex = xml.indexOf(openTag);
                else firstTagIndex = xml.indexOf(openTag, tempFirst);
                tagBody = xml.substring(firstTagIndex, firstTagIndex + (openTag.length()));
                System.out.println(tagBody);
                tempFirst += openTag.length();
            } else {
                openTag = "<" + tags.get(i) + ">";
                closingTags = tags.get(i).split(" ");
                closingTag = "</" + closingTags[0] + ">";
                if(i == 0) {
                    firstTagIndex = xml.indexOf(openTag);
                    lastTagIndex = xml.indexOf(closingTag);
                    tempFirst = firstTagIndex;
                } else {
                    firstTagIndex = xml.indexOf(openTag, tempFirst);
                    lastTagIndex = xml.indexOf(closingTag, firstTagIndex);
                }
                tagBody = xml.substring(firstTagIndex, lastTagIndex + (closingTag.length()));
                System.out.println(tagBody);
                tempFirst += openTag.length();
            }
        }
    }
}
