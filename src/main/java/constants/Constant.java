package constants;

public class Constant {

    public static final String CONTENT_TYPE;
    public static final String text_html_CONTENT_TYPE;
    public static final int EXPECTED_RESPONSE_TIME;
    public static final String CONTENT_LANGUAGE;
    public static final String ENGLISH;
    public static final String ID;
    public static final String URLrEncoded;
    public static final String BOOK_ENDPOINT;


    static {
        CONTENT_TYPE = "Content-Type";
        text_html_CONTENT_TYPE = "text/html;charset=UTF-8";
        URLrEncoded = "application/x-www-form-urlencoded";
        CONTENT_LANGUAGE = "Accept-Language";
        ENGLISH = "en-US,en;q=0.9";
        EXPECTED_RESPONSE_TIME = 500;
        ID = "id";
        BOOK_ENDPOINT = "bookEndpoint";
    }
}
