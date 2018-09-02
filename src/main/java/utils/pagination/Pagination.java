package utils.pagination;

public class Pagination {

    public static final int FIRST_PAGE = 1;
    public static final int FIVE_RECORDS_PER_PAGE = 5;
    
    public static int countNumberOfPages(int rows, int recordsPerPage) {
        int nOfPages = rows / recordsPerPage;
        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }
        return nOfPages;
    }
}
