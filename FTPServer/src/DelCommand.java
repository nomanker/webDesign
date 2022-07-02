import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

/**
 * 删除文件或目录
 * */

public class DelCommand implements Command{

    @Override
    public void getResult(String data, Writer writer, ControllerThread t) {

        String desDir = t.getNowDir() + data;
        File file = new File(desDir);
        System.out.println("删除文件"+desDir);
        if(file.exists())
        {
            try {

                deleteFile(file);

                writer.write("213 文件删除成功...\r\n");
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                writer.write("213 该文件不存在\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void deleteFile(File file) {
        if(file.isFile()) {     //文件
            file.delete();
        }
        else{                   //文件夹
            File[] listFiles = file.listFiles();
            for (File SubFile : listFiles) {
                deleteFile(SubFile);            //如果文件夹非空，则递归删除
            }
            file.delete();
        }
    }
}