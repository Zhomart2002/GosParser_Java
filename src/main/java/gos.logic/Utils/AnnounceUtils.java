package gos.logic.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnnounceUtils {

    private static final List<String> searchAnnounceNames = new ArrayList<>();
    private static final Set<Long> cacheOfAnnNumber = new HashSet<>();
    private static final boolean isChangedSearch = false;

    private AnnounceUtils() {
    }

    public static void addSearchAnnounceNames(String nameOfLot) {
        searchAnnounceNames.add(nameOfLot);
    }

    public static void addCacheOfAnnNumber(Long idOfAnnounce) {
        cacheOfAnnNumber.add(idOfAnnounce);
    }

    public static List<String> getSearchAnnounceNames() {
        if (isChangedSearchNames() || searchAnnounceNames.size() == 0)
            updateSearchNamesList();

        return searchAnnounceNames;
    }

    private static void updateSearchNamesList() {
    }

    public static boolean containsCacheOfAnnNumber(Long id) {
        return cacheOfAnnNumber.contains(id);
    }

    public static boolean isChangedSearchNames() {
        return isChangedSearch;
    }
}
