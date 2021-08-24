package gos.logic.ExcelUtils;

import gos.logic.ResponseUtils.Lot;
import gos.logic.Utils.LotUtils;
import gos.logic.Utils.ReadFileFromResources;
import org.apache.poi.ss.usermodel.*;

import java.io.BufferedInputStream;
import java.io.IOException;

public class ExcelFile {
    private Workbook workbook;
    private Sheet sheet;
    private int lastRowNumber = 6;
    private boolean used = false;

    public ExcelFile() {
        try (BufferedInputStream inputStream = new BufferedInputStream(ReadFileFromResources.readFile("gos.logic/copy.xlsx"))) {
            workbook = WorkbookFactory.create(inputStream);
            sheet = workbook.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertLotToExcel(Lot lot) {

        if (lotNameIsBlocked(lot))
            return;

        this.used = true;
        Row row = sheet.getRow(lastRowNumber);
        insertToCell(row, lot.getCodeWithDot(), 1);
        insertToCell(row, lastRowNumber - 5, 2);
        insertToCell(row, lot.getLotNumber(), 3);
        insertToCell(row, lot.getNameRu(), 4);
        insertToCell(row, lot.getPrice(), 5);
        insertToCell(row, lot.getCount(), 6);
        insertToCell(row, lot.getPrice() * lot.getCount(), 7);
        row.getCell(9).setCellFormula(String.format("G%d*I%d", lastRowNumber + 1, lastRowNumber + 1)); // currentRow + 1 => In Excel currentRow
        insertToCell(row, lot.getDescRu(), 10);
        insertToCell(row, lot.getExtraDescRu(), 11);
        lastRowNumber++;
    }

    private boolean lotNameIsBlocked(Lot lot) {
        return LotUtils.containsBlockedLotNames(lot.getNameRu());
    }

    private void insertToCell(Row row, String value, int column) {
        Cell cell = row.getCell(column);
        cell.setCellValue(value);
    }

    private void insertToCell(Row row, double value, int column) {
        Cell cell = row.getCell(column);
        cell.setCellValue(value);
    }

    public boolean isUsed() {
        return used;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void writeGeneralInformation(String numAnno, String endDate, String organizer) {
        Row numAnnoAndOrganizerRow = sheet.getRow(1);
        Row endDateRow = sheet.getRow(2);

        insertToCell(numAnnoAndOrganizerRow, numAnno, 4);
        insertToCell(numAnnoAndOrganizerRow, organizer, 6);
        insertToCell(endDateRow, endDate, 4);

        Row sumRow = sheet.getRow(lastRowNumber);
        sumRow.getCell(7).setCellFormula(String.format("SUM(H7:H%d)", lastRowNumber));
        sumRow.getCell(9).setCellFormula(String.format("SUM(J7:J%d)", lastRowNumber));

        setBordersStyle();
        setColorToSumCells();
    }

    private void setBordersStyle() {
        Row firstRowWithExistsStyle = sheet.getRow(6);
        for (int currentRowNumber = 7; currentRowNumber < lastRowNumber; currentRowNumber++) {
            Row currentRow = sheet.getRow(currentRowNumber);
            for (int currentColumnNumber = 2; currentColumnNumber <= 9; currentColumnNumber++) {
                Cell currentCell = currentRow.getCell(currentColumnNumber);
                currentCell.setCellStyle(firstRowWithExistsStyle.getCell(currentColumnNumber).getCellStyle());
            }
        }
    }

    private void setColorToSumCells() {
        short blue = 40;
        setColorToCell(lastRowNumber, 7, blue);
        setColorToCell(lastRowNumber, 9, blue);
    }

    private void setColorToCell(int row, int column, short color) {
        CellStyle st = workbook.createCellStyle();
        st.cloneStyleFrom(sheet.getRow(6).getCell(7).getCellStyle()); // Clone style of formatted cell to new cell style
        st.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        st.setFillForegroundColor(color);

        Row currentRow = sheet.getRow(row);
        currentRow.getCell(column).setCellStyle(st);
    }
}
