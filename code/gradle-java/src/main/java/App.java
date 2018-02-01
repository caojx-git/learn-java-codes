import java.util.Scanner;

/**
 * @author caojx
 * Created on 2018/1/30 下午9:50
 */
public class App {

    public static void main(String[] args) {
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        while (++i > 0){
            System.out.println(i+". please input todo item name:");
            TodoItem item = new TodoItem(scanner.nextLine());
            System.out.println(item);
        }
    }
}
