package gos.logic.CompanyUtil;

import java.util.List;

public class Company {
    private final String companyName;
    public final List<String> companyCodes;


    protected Company(String companyName, List<String> companyCodes) {
        this.companyName = companyName;
        this.companyCodes = companyCodes;
    }

    public String getCompanyName() {
        return companyName;
    }

    public boolean containsCode(String code) {
        return companyCodes.contains(code);
    }
}
