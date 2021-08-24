package gos.logic.ResponseUtils;

import java.util.ArrayList;
import java.util.List;

public class TrdBuy {

    private final String numberAnnounce;
    private final String endDateWithTime;
    private final String endDate;
    private final String organizer;

    private final List<Lot> lots;

    public TrdBuy(String numberAnnounce, String endDate, String organizer) {
        this.numberAnnounce = numberAnnounce;
        this.endDateWithTime = endDate;
        this.endDate = endDate.split("[ ]")[0];
        this.organizer = organizer;
        this.lots = new ArrayList<>();
    }


    public String getNumberAnnounce() {
        return numberAnnounce;
    }

    public String getEndDateWithTime() {
        return endDateWithTime;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void addToLots(Lot lot) {
        this.lots.add(lot);
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getEndDate() {
        return endDate;
    }
}
