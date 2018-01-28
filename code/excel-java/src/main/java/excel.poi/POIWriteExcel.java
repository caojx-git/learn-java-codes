package excel.poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * POI创建Excel，格式是.xls
 */
public class POIWriteExcel {

    /**
     * POI创建Excel文件
     * @param args
     */
    public static void main(String[] args) {
        //定义标题
        String[] title = {"id","name","sex"};
        //1.创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建工作表
        HSSFSheet sheet = workbook.createSheet();
        //设置标题
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i); //i所在列
            cell.setCellValue(title[i]); //设置列的内容
        }

        //追加数据
        for (int i = 1; i < 10; i++) {
            HSSFRow nextRow = sheet.createRow(i);
            HSSFCell cell2 = nextRow.createCell(0); //第一列
            cell2.setCellValue("a"+i);
            cell2 = nextRow.createCell(1); //第二列
            cell2.setCellValue("user"+i);
            cell2 = nextRow.createCell(2); //第三列
            cell2.setCellValue("男"+i);
        }

        //创建一个文件
        File file = new File("poi_test.xls");
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
