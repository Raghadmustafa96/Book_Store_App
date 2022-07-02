package constants;

public class Endpoint {

    public static final String HOST;
    public static final String BOOKS_API;
    public static final String GET_AUTHORS_API;
    public static final String SEARCH_API;
    public static final String GET_BOOK_API;
    public static final String CREATE_BOOK_PAGE;
    public static final String DELETE_BOOK_API;


    static {
        HOST = "http://localhost:8080";
        BOOKS_API = HOST + "/books";
        GET_AUTHORS_API = HOST + "/authors";
        SEARCH_API = HOST + "/search";
        GET_BOOK_API = "/edit";       // /books/{id}/edit
        CREATE_BOOK_PAGE = "/create";
        DELETE_BOOK_API = "/delete";  // /books/{id}/delete
    }
}
