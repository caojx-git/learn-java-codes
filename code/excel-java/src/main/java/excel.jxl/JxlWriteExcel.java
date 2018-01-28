package excel.jxl;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;

/**
 * POI创建Excel，格式是.xls，不支持2007+以上的Excel，即不支持.xlsx结尾的Excel
 */
public class JxlWriteExcel {

    /**
     * JXL创建Excel文件
     * @param args
     */
    public static void main(String[] args) {
        //定义标题
        String[] title = {"id","name","sex"};
        //1.创建一个Excel文件
        File file = new File("jxl_test.xls");
        try {
            file.createNewFile();
            //2.创建工作薄
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            //3.创建sheet
            WritableSheet sheet = workbook.createSheet("sheet1",0);

            //4.设置标题
            Label label = null;
            for (int i = 0; i < title.length; i++) {
                label = new Label(i,0,title[i]); //三个参数分别为：所在列、所在行，数据值
                //将标题设置到对应的sheet中的单元格
                sheet.addCell(label);
            }
            //5.追加数据
            for (int i = 1; i < 10; i++) {
                label = new Label(0, i,"a"+i);
                sheet.addCell(label);
                label = new Label(1, i,"user"+i);
                sheet.addCell(label);
                label = new Label(2, i,"男");
                sheet.addCell(label);
            }

            //写入数据
            workbook.write();
            //关闭流资源
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
