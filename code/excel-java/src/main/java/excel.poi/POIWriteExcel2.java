package excel.poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * .xlsx格式的创建,这是Office 2007+支持的Excel格式，不过我们不知道客户的Office是什么版本，所以我们一般在生产中不会使用.xlsx格式
 */
public class POIWriteExcel2 {

    /**
     * POI创建Excel文件 .xlsx格式
     * @param args
     */
    public static void main(String[] args) {
        //定义标题
        String[] title = {"id","name","sex"};
        //1.创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        //2.创建工作表
        Sheet sheet = workbook.createSheet();
        //设置标题
        Row row = sheet.createRow(0);
        Cell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i); //i所在列
            cell.setCellValue(title[i]); //设置列的内容
        }

        //追加数据
        for (int i = 1; i < 10; i++) {
            Row nextRow = sheet.createRow(i);
            Cell cell2 = nextRow.createCell(0); //第一列
            cell2.setCellValue("a"+i);
            cell2 = nextRow.createCell(1); //第二列
            cell2.setCellValue("user"+i);
            cell2 = nextRow.createCell(2); //第三列
            cell2.setCellValue("男"+i);
        }

        //创建一个文件
        File file = new File("poi_test2.xlsx");
        try {
            file.createNewFile();
            //将数据保存到excel文件中
            FileOutputStream outputStream = FileUtils.openOutputStream(file);
            workbook.write(outputStream);
            //关闭资源
            outputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
