package com.csinfotechbd.utillity;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ExcelFileUtils {

    private final NumberUtils numberUtils;
    private final DateUtils dateUtils;

    public List<Sheet> getExcelSheetsFromMultipartFile(MultipartFile file) {
        List<Sheet> sheets = new LinkedList<>();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            int totalSheets = workbook.getNumberOfSheets();

            for (int i = 0; i < totalSheets; i++)
                sheets.add(workbook.getSheetAt(i));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return sheets;
    }


    public String getTextCellValue(Row row, Map<String, Integer> headers, String columnHeader, List<String> errors) {
        String value = getLargeTextCellValue(row, headers, columnHeader, errors);
        return value.length() > 254 ? value.substring(0, 254) : value;
    }

    public String getLargeTextCellValue(Row row, Map<String, Integer> headers, String columnHeader, List<String> errors) {
        String value = "";
        try {
            if (headers.containsKey(columnHeader))
                value = Objects.toString(row.getCell(headers.get(columnHeader)), "").trim();
        } catch (Exception e) {
            listCellValueParseError(row, columnHeader, errors);
        }
        return value.replaceAll("\\s{2,}", " ");
    }

    public Number getNumberCellValue(Row row, Map<String, Integer> headers, String columnHeader, List<String> errors) {
        try {
            if (headers.containsKey(columnHeader))
                return row.getCell(headers.get(columnHeader)).getNumericCellValue();
        } catch (Exception e) {
            listCellValueParseError(row, columnHeader, errors);
        }
        return 0;
    }

    public Date getDateCellValue(Row row, Map<String, Integer> headers, String columnHeader, List<String> errors) {
        Date date = null;
        try {
            if (headers.containsKey(columnHeader))
                date = row.getCell(headers.get(columnHeader)).getDateCellValue();
        } catch (Exception e) {
            try {
                String dateString = getTextCellValue(row, headers, columnHeader, errors);
                String format = columnHeader.substring(columnHeader.indexOf("(") + 1, columnHeader.lastIndexOf(")"));
                date = dateUtils.getFormattedDate(dateString, format.replace("m", "M"));

                if (date == null) {
                    date = dateUtils.getDateUsingNonWordDelimeter(dateString);
                }
            } catch (Exception ex) {

            } finally {
                if (date == null)
                    listCellValueParseError(row, columnHeader, errors);
            }
        }
        return date == null || date.after(dateUtils.getFormattedDate("31-12-3000", "dd-MM-yyyy")) ||
                date.before(dateUtils.getFormattedDate("01-01-1500", "dd-MM-yyyy")) ? null : date;
    }

    public Map<String, Integer> getColumnHeadersAndIndices(Iterator<Row> rows, Map<Integer, String> headerRowIdentifierColumns) {
        Map<String, Integer> headers = new HashMap<>();
        Row row;
        while (rows.hasNext()) {
            if (isHeaderRow(row = rows.next(), headerRowIdentifierColumns)) {
                row.forEach(cell -> headers.put(Objects.toString(cell).trim(), cell.getColumnIndex()));
                break;
            }
        }
        return headers;
    }

    private void listCellValueParseError(Row row, String columnHeader, List<String> errors) {
        errors.add("Invalid value found" + " in " + row.getSheet().getSheetName() + " column " + columnHeader + " at row " + row.getRowNum());
    }

    /**
     * Column count starts form 0
     *
     * @param row
     * @param headerRowIdentifierColumns
     * @return true
     */
    private boolean isHeaderRow(Row row, Map<Integer, String> headerRowIdentifierColumns) {
        for (Map.Entry<Integer, String> entry : headerRowIdentifierColumns.entrySet()) {
            String columnValue = Objects.toString(row.getCell(entry.getKey()), "").trim();
            String value = entry.getValue().trim();
            if (!columnValue.equals(value))
                return false;
        }
        return true;
    }
}
