package gos.logic.ExcelUtils;

import gos.logic.CompanyUtil.Company;
import gos.logic.ResponseUtils.TrdBuy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelStatic {
    private ExcelStatic() {
    }

    private final static Map<Company, ExcelFile> excelTemplateMap = new HashMap<>();

    public static Map<Company, ExcelFile> getExcelTemplates(List<Company> companyList) {
        excelTemplateMap.clear();
        for (Company company : companyList)
            excelTemplateMap.put(company, new ExcelFile());
        return excelTemplateMap;
    }

    public static void saveExcelTemplates(TrdBuy currentTrdBuy) throws IOException {
        for (Company company : excelTemplateMap.keySet()) {
            ExcelFile currentExcelFile = excelTemplateMap.get(company);
            /* Don't save if Excel file doesn't have any information */
            if (!currentExcelFile.isUsed())
                continue;

            currentExcelFile.writeGeneralInformation(
                    currentTrdBuy.getNumberAnnounce(),
                    currentTrdBuy.getEndDateWithTime(),
                    currentTrdBuy.getOrganizer());

            String fileName = currentTrdBuy.getNumberAnnounce() + " " + company.getCompanyName() + ".xlsx";
            createFolderIfNotExists(currentTrdBuy.getEndDate());

            saveFile(currentExcelFile, getOrderFolder() + "\\" + currentTrdBuy.getEndDate() + "\\" + fileName);
        }
    }

    private static void saveFile(ExcelFile currentExcelFile, String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        currentExcelFile.getWorkbook().write(fileOutputStream);
        fileOutputStream.close();
        currentExcelFile.getWorkbook().close();
    }

    private static void createFolderIfNotExists(String folderName) {
        File theDir = new File(getOrderFolder() + "\\" + folderName);
        if (!theDir.exists())
            theDir.mkdirs();
    }

    private static String getOrderFolder() {
        return System.getProperty("user.dir") + "\\Заявки";
    }
}
