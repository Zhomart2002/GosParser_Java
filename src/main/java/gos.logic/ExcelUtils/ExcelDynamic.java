package gos.logic.ExcelUtils;

import gos.logic.CompanyUtil.Company;
import gos.logic.ResponseUtils.Lot;
import gos.logic.ResponseUtils.TrdBuy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelDynamic {
    private ExcelDynamic() {
    }

    public static void parseToExcel(List<TrdBuy> trdBuyList, List<Company> companyList) throws IOException {
        for (TrdBuy trdBuy : trdBuyList) {
            Map<Company, ExcelFile> excelTemplateMap = ExcelStatic.getExcelTemplates(companyList);
            workWithLots(trdBuy, excelTemplateMap);
            ExcelStatic.saveExcelTemplates(trdBuy);
        }
    }

    private static void workWithLots(TrdBuy trdBuy, Map<Company, ExcelFile> excelTemplateMap) {
        for (Lot lot : trdBuy.getLots()) {
            for (Company company : excelTemplateMap.keySet()) {
                if (company.containsCode(lot.getCode())) {
                    ExcelFile currentExcelFile = excelTemplateMap.get(company);
                    currentExcelFile.insertLotToExcel(lot);
                }
            }
        }
    }
}
