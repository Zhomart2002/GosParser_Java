package gos.logic.Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LotUtils {

    private final static List<String> searchLotNames = new ArrayList<>();
    private final static List<String> blockedLotNames = new ArrayList<>();

//    private static boolean isChangedAnything = false;

    private LotUtils() {
    }

//   test public static void addSearchLotNames(String lotName){
//        searchLotNames.add(lotName);
//    }
//
//   test public static void blockLotName(String lotName){
//        blockedLotNames.add(lotName);
//    }
//
//   test public static boolean isChangedAnything(){
//        return isChangedAnything;
//    }

    public static List<String> getSearchLotNames() {
        if (searchLotNames.size() == 0)
            updateAll();
        return searchLotNames;
    }

    public static boolean containsBlockedLotNames(String nameOfLot) {
        if (blockedLotNames.size() == 0)
            updateAll();
        return blockedLotNames.contains(nameOfLot.toLowerCase());
    }

    private static void updateAll() {
        clearLists();
        JSONObject lotJson = ReadJsonFromFile.readAsJsonObject("gos.logic/LotsUtil.json");
        searchLotNames.addAll((JSONArray) lotJson.get("searchLotNames"));
        blockedLotNames.addAll((JSONArray) lotJson.get("notAccess"));
    }

    private static void clearLists() {
        searchLotNames.clear();
        blockedLotNames.clear();
    }

}
