package webserver;

import model.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

class HTMLTableMaker {
    static String makeUserTable(String htmlPath, Collection<User> users) throws IOException {
        var document = Jsoup.parse(new File(htmlPath), "UTF-8");

        var table = getTableClassElement(document);

        var tbody = getTableBodyTagElement(table);

        writeUserRows(users, tbody);

        return document.html();
    }

    private static Element getTableBodyTagElement(Element table) {
        return table.getElementsByTag("tbody")
                .stream()
                .findFirst()
                .orElseThrow(() -> new WebServerException(WebServerErrorMessage.UNEXPECTED_HTML));
    }

    private static Element getTableClassElement(Document document) {
        return document.getElementsByClass("table")
                .stream()
                .findFirst()
                .orElseThrow(() -> new WebServerException(WebServerErrorMessage.UNEXPECTED_HTML));
    }

    private static void writeUserRows(Collection<User> users, Element tbody) {
        int rowNum = 1;
        StringBuilder stringBuilder = new StringBuilder("");
        for (var user : users) {
            stringBuilder.append(String.format(
                    "<tr> <th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td> </tr>",
                    rowNum++,
                    user.getUserId(),
                    user.getName(),
                    user.getEmail()));
        }
        tbody.html(stringBuilder.toString());
    }

}
