package gos.logic.ResponseUtils;

public class Lot {
    private final String lotNumber;
    private final String nameRu;
    private final Long count;
    private final Long price;
    private final String code;
    private final String descRu;
    private final String extraDescRu;

    public Lot(String lotNumber, String nameRu, Long count, Long price, String code, String descRu, String extraDescRu) {
        this.lotNumber = lotNumber;
        this.nameRu = nameRu;
        this.count = count;
        this.price = price;
        this.code = code.split("[.]", 2)[0];
        this.descRu = descRu;
        this.extraDescRu = extraDescRu;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public String getNameRu() {
        return nameRu;
    }

    public Long getCount() {
        return count;
    }

    public Long getPrice() {
        return price;
    }

    public String getCode() {
        return this.code;
    }

    public String getCodeWithDot() {
        return new StringBuilder(this.code).insert(2, ".").insert(5, ".").toString();
    }

    public String getDescRu() {
        return descRu;
    }

    public String getExtraDescRu() {
        return extraDescRu;
    }
}
