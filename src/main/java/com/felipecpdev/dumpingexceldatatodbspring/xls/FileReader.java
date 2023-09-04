package com.felipecpdev.dumpingexceldatatodbspring.xls;

import com.felipecpdev.dumpingexceldatatodbspring.entity.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class FileReader {

    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }


    public static List<Product> convertExcelToListOfProduct(InputStream is) {
        List<Product> list = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("data");

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();

                int cid = 0;

                Product p = new Product();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    /*if (cell.getCellType() == CellType.BLANK) {
                        // Handle empty cell
                        cid++;
                        continue;
                    }*/
                    switch (cid) {
                        case 0:
                            p.setId((long) cell.getNumericCellValue());
                            break;
                        case 1:
                            p.setActive(Boolean.parseBoolean(cell.getStringCellValue()));
                            break;
                        case 2:
                            p.setDateCreated(LocalDateTime.parse(cell.getStringCellValue().replace("'", ""), formatter));
                            break;
                        case 3:
                            p.setDescription(cell.getStringCellValue());
                            break;
                        case 4:
                            p.setImageUrl(cell.getStringCellValue());
                            break;
                        case 5:
                            p.setLastUpdated(LocalDateTime.parse(cell.getStringCellValue().replace("'", ""), formatter));
                            break;
                        case 6:
                            p.setName(cell.getStringCellValue());
                            break;
                        case 7:
                            p.setPrice(BigDecimal.valueOf(Double.parseDouble(cell.getStringCellValue())));
                            break;
                        case 8:
                            p.setSku(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
