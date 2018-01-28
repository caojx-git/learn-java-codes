package excel.poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

/**
 * POI读取Excel，格式是.xlsx这是Office 2007+支持的Excel格式
 */
public class POIReadExcel2 {

    /**
     * POI解析Excel文件的内容
     * @param args
     */
    public static void main(String[] args) {
        //1.需要解析的Excel文件
        File file = new File("poi_test2.xlsx");
        try {
            //2.创建工作薄,读取Excel文件内容
            XSSFWorkbook workbook = new XSSFWorkbook(FileUtils.openInputStream(file));
            //3.确定sheet
            //获取指定名称的sheet
            //Sheet sheet = workbook.getSheet("Sheet0");
            //根据索引获取sheet
            Sheet sheet = workbook.getSheetAt(0);
            int firstRowNum = 0;
            //获取sheet中最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i < lastRowNum; i++) {
                Row row = sheet.getRow(i);
                //获取当前最后单元格列号
                int lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j);
                    String value = cell.getStringCellValue();
                    System.out.print(value+" ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
