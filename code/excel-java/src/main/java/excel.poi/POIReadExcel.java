package excel.poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;

/**
 * POI读取Excel，格式是.xls
 */
public class POIReadExcel {

    /**
     * POI解析Excel文件的内容
     * @param args
     */
    public static void main(String[] args) {
        //1.需要解析的Excel文件
        File file = new File("poi_test.xls");
        try {
            //2.创建工作薄,读取Excel文件内容
            HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
            //3.确定sheet
            //获取指定名称的sheet
            //HSSFSheet sheet = workbook.getSheet("Sheet0");
            //根据索引获取sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            int firstRowNum = 0;
            //获取sheet中最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i < lastRowNum; i++) {
                HSSFRow row = sheet.getRow(i);
                //获取当前最后单元格列号
                int lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    HSSFCell cell = row.getCell(j);
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
