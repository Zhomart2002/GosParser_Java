package gos.logic;

import gos.logic.ClientUtil.Client;
import gos.logic.ResponseUtils.TrdBuy;
import gos.logic.ResponseUtils.TrdBuyJsonParser;
import gos.logic.Utils.LotUtils;
import gos.logic.Utils.QueryUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RequestResponseManager {
    private static final long NOT_HAVE_NEXT_PAGE = -1;
    private static final long TOTAL_COUNT_ZERO = 0;

    private RequestResponseManager() {
    }

    public static List<TrdBuy> getListTrdBuyFromClient(Client client) throws IOException {
        int currentSearchWordNum = 1;
        JSONArray listOfAllTrdBuy = new JSONArray();

        parent:
        for (String lotName : LotUtils.getSearchLotNames()) {
            long after = NOT_HAVE_NEXT_PAGE;
            do {
                String json = "{ \"query\" : \"" + QueryUtils.getLotQueryWithDescription(lotName, after) + "\"}";

                JSONObject response = client.postAndGetJsonObject(json);
                sleepSeconds();
                checkResponseForErrors(response);

                after = responseHasNextPage(response);
                if (after == TOTAL_COUNT_ZERO) {
                    System.out.println("No lots " + lotName);
                    continue parent;
                }

                JSONObject data = (JSONObject) response.get("data");
                listOfAllTrdBuy.addAll((JSONArray) data.get("Lots"));

            } while (after > TOTAL_COUNT_ZERO);

            System.out.printf("{%s} завершен. Осталось %d поиск.\n", lotName, LotUtils.getSearchLotNames().size() - currentSearchWordNum++);
        }

        return TrdBuyJsonParser.parse(listOfAllTrdBuy);
    }

    private static void checkResponseForErrors(JSONObject response) throws IOException {
        if (response == null)
            throw new IOException("Response is null");

        if (response.containsKey("message"))
            throw new IOException(response.get("message") + ". Invalid token");

        if (response.containsKey("errors"))
            throw new IOException("Error on query: " + response.get("errors"));
    }

    private static void sleepSeconds() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static long responseHasNextPage(JSONObject response) {
        JSONObject extensions = (JSONObject) response.get("extensions");
        JSONObject pageInfo = (JSONObject) extensions.get("pageInfo");

        boolean hasNextPage = (Boolean) pageInfo.get("hasNextPage");
        long lastId = (Long) pageInfo.get("lastId");
        long totalCount = (Long) pageInfo.get("totalCount");

        if (totalCount == 0)
            return TOTAL_COUNT_ZERO;

        if (hasNextPage)
            return lastId;

        return NOT_HAVE_NEXT_PAGE;
    }
}
