package excel.jxl;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

/**
 * Jxl读取Excel，格式是.xls 不支持2007+以上的Excel，即不支持.xlsx结尾的Excel
 */
public class JxlReadExcel {

    /**
     * JXL解析Excel文件
     * @param args
     */
    public static void main(String[] args) {
        try {
            //1.创建workbook
            Workbook workbook = Workbook.getWorkbook(new File("jxl_test.xls"));
            //2.获取第一个工作表sheet
            Sheet sheet = workbook.getSheet(0   );
            //获取数据
            for (int i = 0; i < sheet.getRows(); i++) {
                for (int j = 0; j < sheet.getColumns(); j++) {
                    Cell cell = sheet.getCell(j,i); //列，行
                    //获取单元格的内容
                    System.out.print(cell.getContents()+" ");
                }
                System.out.println("");
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

    }
}
