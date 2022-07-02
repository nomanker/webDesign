import java.io.File;
import java.io.Writer;

/**
 * 重命名文件或目录
 * */

public class RenameCommand implements Command{

    @Override
    public void getResult(String data, Writer writer, ControllerThread t) {

        String[] split = data.split(">");
        String oldName = split[0];
        String newName = "/"+split[1]+getSuffix(oldName);
        String desDir = t.getNowDir();

        System.out.println("重命名"+desDir+oldName+" 为 "+newName);
        File oldFile = new File(desDir+oldName);
        File newFile = new File(desDir+newName);

        try {
            if (newFile.exists()) {  //  确保新的文件名不存在
                writer.write("213 文件名重复\r\n");
            }
            else if (oldFile.renameTo(newFile)) {
                writer.write("213 重命名完成\r\n");
            } else {
                writer.write("213 重命名失败\r\n");
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSuffix(String FileName) {     //获取旧文件后缀名
        String Suffix = "";
        Integer idx = FileName.lastIndexOf('.');
        if(idx!=-1)Suffix = FileName.substring(idx);
        return Suffix;
    }

}