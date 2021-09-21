package gos.logic;

import gos.logic.ClientUtil.Client;
import gos.logic.ClientUtil.ClientFactory;
import gos.logic.CompanyUtil.Company;
import gos.logic.CompanyUtil.CompanyFactory;
import gos.logic.ExcelUtils.ExcelDynamic;
import gos.logic.ResponseUtils.TrdBuy;

import java.io.IOException;
import java.util.List;

public class Runner {

    private Runner() {
    }

    public static void run() {
        Client client = ClientFactory.createRequestWithToken("");
        try {
            /* Getting List of all available companies from json file */
            List<Company> companies = CompanyFactory.getAllCompanyAsList();

            /* Getting List of trdBuy from post request */
            List<TrdBuy> trdBuyList = RequestResponseManager.getListTrdBuyFromClient(client);

            ExcelDynamic.parseToExcel(trdBuyList, companies);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
