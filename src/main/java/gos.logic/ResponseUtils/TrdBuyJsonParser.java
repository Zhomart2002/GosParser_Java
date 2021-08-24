package gos.logic.ResponseUtils;

import gos.logic.Utils.AnnounceUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrdBuyJsonParser {
    private TrdBuyJsonParser() {
    }

    public static List<TrdBuy> parse(JSONArray listOfTrdBuy) {
        List<TrdBuy> parsedListTrdBuy = new ArrayList<>();

        for (Object object : listOfTrdBuy) {
            JSONObject trdBuyObject = getTrdBuy(object);
            if (trdBuyInCache(trdBuyObject))
                continue;

            TrdBuy trdBuy = parseTrdBuy(trdBuyObject);
            parsedListTrdBuy.add(trdBuy);
        }
        return parsedListTrdBuy;
    }

    private static JSONObject getTrdBuy(Object object) {
        JSONObject propertyTrdBuy = (JSONObject) object;
        return (JSONObject) propertyTrdBuy.get("TrdBuy");
    }

    private static boolean trdBuyInCache(JSONObject trdBuy) {
        Long idAnno = (Long) trdBuy.get("id");
        boolean contains = AnnounceUtils.containsCacheOfAnnNumber(idAnno);
        if (!contains)
            AnnounceUtils.addCacheOfAnnNumber(idAnno);
        return contains;
    }

    private static TrdBuy parseTrdBuy(JSONObject trdBuyObject) {
        String numberAnno = (String) trdBuyObject.get("numberAnno");
        String endDate = (String) trdBuyObject.get("endDate");
        String organizer = getOrganizer(trdBuyObject);

        TrdBuy trdBuy = new TrdBuy(numberAnno, endDate, organizer);
        parseLots(trdBuyObject, trdBuy);

        return trdBuy;
    }

    private static void parseLots(JSONObject trdBuyObject, TrdBuy trdBuy) {
        JSONArray lots = getLots(trdBuyObject);
        for (Object object2 : lots) {
            JSONObject lotObject = getLot(object2);
            JSONObject plan = getPlan(lotObject);
            String lotNumber = (String) lotObject.get("lotNumber");
            String nameRu = (String) plan.get("nameRu");
            String refEnstruCode = (String) plan.get("refEnstruCode");
            String descRu = (String) plan.get("descRu");
            String extraDescRu = (String) plan.get("extraDescRu");
            Long price = ((Number) plan.get("price")).longValue();
            Long count = (Long) plan.get("count");

            Lot lot = new Lot(lotNumber, nameRu, count, price, refEnstruCode, descRu, extraDescRu);
            trdBuy.addToLots(lot);
        }
    }

    private static String getOrganizer(JSONObject trdBuy) {
        JSONObject organizer = (JSONObject) trdBuy.get("Organizer");
        return (String) organizer.get("nameRu");
    }

    private static JSONObject getPlan(JSONObject lot) {
        return (JSONObject) ((JSONArray) lot.get("Plans")).get(0);
    }

    private static JSONObject getLot(Object object2) {
        return (JSONObject) object2;
    }

    private static JSONArray getLots(JSONObject trdBuy) {
        return (JSONArray) trdBuy.get("Lots");
    }

}
