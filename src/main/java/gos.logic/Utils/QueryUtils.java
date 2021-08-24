package gos.logic.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryUtils {

    private static final String URL = "https://ows.goszakup.gov.kz/v3/graphql";

    private static final List<Integer> refTradeMethodsId = new ArrayList<>(Arrays.asList(3, 32));
    private static final List<Integer> refLotStatusId = new ArrayList<>(Arrays.asList(210, 220, 240));

    private static String QUERY = "";

    private QueryUtils() {
    }

    public static String getLotQueryWithDescription(String nameOfLot, long afterId) {
        if (QUERY.length() == 0)
            updateQuery();

        return String.format(QUERY, nameOfLot, afterId);
    }

    public static void updateQuery() {
        QUERY = "{ " +
                "  Lots(filter: { " +
                "      nameDescriptionRu: \\\"%s\\\", " + // \\\" -> parser(json) -> \"
                "      refTradeMethodsId: " + refTradeMethodsId + ", " +
                "      refLotStatusId: " + refLotStatusId + "}, " +
                "      limit: 200, " +
                "      after: %d){ " +
                "   TrdBuy { " +
                "       id " +
                "       numberAnno " +
                "       totalSum " +
                "       countLots " +
                "       endDate " +
                "       Lots { " +
                "           lotNumber " +
                "           Plans { " +
                "               nameRu " +
                "               count " +
                "               price " +
                "               refEnstruCode " +
                "               descRu " +
                "               extraDescRu " +
                "            } " +
                "       } " +
                "       Organizer { " +
                "           nameRu " +
                "       } " +
                "     } " +
                "   } " +
                "} ";
    }

    public static String getUrl() {
        return URL;
    }
}
