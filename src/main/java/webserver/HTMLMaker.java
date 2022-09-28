package webserver;

import memo.Memo;
import user.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class HTMLMaker {
    public static String makeUserTable(String htmlPath, Collection<User> users) throws IOException {
        var document = Jsoup.parse(new File(htmlPath), "UTF-8");

        var table = getTableClassElement(document);

        var tbody = getTableBodyTagElement(table);

        writeUserRows(users, tbody);

        return document.html();
    }

    // TODO: 테스트 만들어도 좋을 듯
    public static String makeMemoListView(String htmlPath, Collection<Memo> memos) throws IOException {
        var document = Jsoup.parse(new File(htmlPath), "UTF-8");

        var ul = getListClassElement(document);

        writeMemoInUl(memos, ul);

        return document.html();
    }

    private static Element getListClassElement(Document document) {
        return document.getElementsByClass("list")
                .stream()
                .findFirst()
                .orElseThrow(() -> new WebServerException(WebServerErrorMessage.UNEXPECTED_HTML));
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

    private static void writeMemoInUl(Collection<Memo> memos, Element ul) {
        StringBuilder stringBuilder = new StringBuilder("");

        for (var memo : memos) {
            stringBuilder.append(String.format(
                    "<li>\n" +
                            "<div class=\"wrap\">\n" +
                            "  <div class=\"main\">\n" +
                            "    <strong class=\"subject\">\n" +
                            "      %s\n" +
                            "    </strong>\n" +
                            "    <div class=\"auth-info\">\n" +
                            "      <span class=\"time\">%s</span>\n" +
                            "      <span class=\"author\">%s</span>\n" +
                            "    </div>\n" +
                            "  </div>\n" +
                            "</div>\n" +
                            " </li>",
                    memo.getContents(),
                    memo.getLocalDate().toString(),
                    memo.getAuthorId()));
        }

        ul.html(stringBuilder.toString());
    }

}
