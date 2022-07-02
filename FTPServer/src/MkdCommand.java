import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class MkdCommand implements Command{

    @Override
    public void getResult(String data, Writer writer, ControllerThread t) {

        String desDir = t.getNowDir() + data;
        File file = new File(desDir);
        System.out.println("新建文件夹"+desDir);
        try {
            if(file.exists()) {
                writer.write("212 文件夹已经存在\r\n");
            }else {
                if(file.mkdirs()) {
                    writer.write("212 创建文件夹成功\r\n");
                } else {
                    writer.write("212 创建文件夹失败\r\n");
                }
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}