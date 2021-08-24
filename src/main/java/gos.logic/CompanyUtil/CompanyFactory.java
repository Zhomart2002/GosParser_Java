package gos.logic.CompanyUtil;

import gos.logic.Utils.ReadJsonFromFile;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompanyFactory {

    private CompanyFactory() {
    }

    public static List<Company> getAllCompanyAsList() {
        List<Company> companyList = new ArrayList<>();

        JSONObject companiesJson = ReadJsonFromFile.readAsJsonObject("gos.logic/companies.json");
        for (Object objectKey : companiesJson.keySet()) {
            JSONObject company = (JSONObject) companiesJson.get(objectKey);
            String companyName = (String) objectKey;
            boolean accessCompany = (boolean) company.get("access");
            if (accessCompany) {
                List<String> codes = (List<String>) company.get("codes");
                companyList.add(new Company(companyName, codes));
            }
        }
        return companyList;
    }
}
