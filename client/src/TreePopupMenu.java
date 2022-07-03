import org.apache.commons.net.ftp.FTPFile;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TreePopupMenu extends JFrame implements MouseListener, ActionListener {
    //初始化参数--------------------------------
    static FTPFile[] file;
    static String FTP="127.0.0.1";
    static String username="liyz";
    static String password="000000";
    //初始化参数--------------------------------


    static Ftp_by_me_active ftp;
    public static Ftp_by_me_active getFtp() {
        return ftp;
    }
    public static FTPFile[] getFile(){
        return file;
    }



    //    private static final long serialVersionUID = 1L;
    JTree tree;
    JPopupMenu popMenu;//右键菜单
    JMenuItem delItem;//删除
    JMenuItem editItem;//修改
    JMenuItem downLoadItem;//下载

    //树形文件目录
    DefaultTreeModel treeModel;
    DefaultMutableTreeNode root;
    JScrollPane scrollPane;

    //文件夹点击事件
    JPopupMenu fileMenu;//文件夹右键菜单
    JMenuItem renameFile;//重命名文件
    JMenuItem deleteFile;//删除文件夹
    JMenuItem createFile;//创建文件夹
    JMenuItem uploadFile;//上传文件到指定文件目录底下

    public TreePopupMenu() {
        initialize();
    }

    private void initialize() {
//        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Frame_Main.class.getResource("/com/sun/java/swing/plaf/windows/icons/UpFolder.gif")));
        setTitle("FTP 客户端");
        setBounds(100, 100, 470, 534);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);


        //显示基本信息(FTP username)-----------------------------------------------
        JLabel lblNewLabel = new JLabel("FTP IP地址");
        lblNewLabel.setBounds(32, 8, 70, 15);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("用户名");
        lblNewLabel_1.setBounds(32, 25, 70, 15);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("密码");
        lblNewLabel_2.setBounds(32, 40, 70, 15);
        getContentPane().add(lblNewLabel_2);

        JTextField url = new JTextField("127.0.0.1");   //FTP服务地址
        url.setBounds(110,8,82,15);
        getContentPane().add(url);

        JTextField usernameField = new JTextField("admin"); //用户名
        usernameField.setBounds(110,25,82,15);
        getContentPane().add(usernameField);

        JPasswordField passwordField = new JPasswordField("000000");  //密码
        passwordField.setBounds(110,40,82,15);
        getContentPane().add(passwordField);


        //登录按钮------------------------------------------------
        JButton login=new JButton("登录");
        login.setFont(new Font("宋体", Font.PLAIN, 12));
        login.setBackground(UIManager.getColor("Button.highlight"));
        login.setBounds(210, 15, 82, 23);
        getContentPane().add(login);
        login.addActionListener(e -> {
            System.out.println("登录==============");
            try {
                FTP=url.getText().trim();
                username=usernameField.getText().trim();
                password=passwordField.getText().trim();

                ftp=new Ftp_by_me_active(FTP,username,password);
                if(Ftp_by_me_active.isLogined)
                {
                    //获取所有文件，
                    file=ftp.getAllFile();
                    setTableInfo();//显示所有文件信息
                    //设置不可更改
                    url.setEditable(false);
                    usernameField.setEditable(false);
                    passwordField.setEditable(false);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showConfirmDialog(null, "用户名或者密码错误\n username："+username, "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
            }
        });


        //上传按钮--------------------------------------------------
        //上传按钮显示逻辑
//        JButton upload = new JButton("上传");
//        upload.setFont(new Font("宋体", Font.PLAIN, 12));
//        upload.setBackground(UIManager.getColor("Button.highlight"));
//        upload.setBounds(312, 45, 82, 23);
//        getContentPane().add(upload);
//        upload.addActionListener(arg0 -> {
            //上传点击按钮触发------------------------------------
//            System.out.println("上传！！！！！");
//            int result = 0;
//            File file = null;
//            String path = null;
//            JFileChooser fileChooser = new JFileChooser();
//            FileSystemView fsv = FileSystemView.getFileSystemView();
//            System.out.println(fsv.getHomeDirectory());                //得到桌面路径
//            fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
//            fileChooser.setDialogTitle("请选择要上传的文件...");
//            fileChooser.setApproveButtonText("确定");
//            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            result = fileChooser.showOpenDialog(null);
//            if (JFileChooser.APPROVE_OPTION == result) {
//                path=fileChooser.getSelectedFile().getPath();
//                System.out.println("path: "+path);
//                try {
//                    //下载
//                    ftp.upload(path);
//                    System.out.println("文件上传成功");
//                } catch (Exception e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//            }
            //上传点击按钮触发------------------------------------
//        });

        //上传按钮--------------------------------------------------



        //刷新按钮--------------------------------------------------
        JButton refresh = new JButton("刷新");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    file=ftp.getAllFile();
                    setTableInfo();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        refresh.setFont(new Font("宋体", Font.PLAIN, 12));
        refresh.setBackground(UIManager.getColor("Button.highlight"));
        refresh.setBounds(312, 15, 82, 23);
        getContentPane().add(refresh);
        //刷新按钮--------------------------------------------------


        //
        popMenu = new JPopupMenu();
        delItem = new JMenuItem("删除");
        delItem.addActionListener(this);
        editItem = new JMenuItem("重命名");
        editItem.addActionListener(this);
        downLoadItem = new JMenuItem("下载");
        downLoadItem.addActionListener(this);

        popMenu.add(delItem);
        popMenu.add(editItem);
        popMenu.add(downLoadItem);

        fileMenu = new JPopupMenu();
        createFile = new JMenuItem("创建新文件夹");
        createFile.addActionListener(this);
        renameFile = new JMenuItem("重命名文件夹");
        renameFile.addActionListener(this);
        deleteFile = new JMenuItem("删除文件夹");
        deleteFile.addActionListener(this);
        uploadFile = new JMenuItem("上传文件");
        uploadFile.addActionListener(this);

        fileMenu.add(createFile);
        fileMenu.add(renameFile);
        fileMenu.add(deleteFile);
        fileMenu.add(uploadFile);
    }

    public void mouseClicked(MouseEvent e) {
        //获取距离最近的点进行处理
        if (SwingUtilities.isRightMouseButton(e)) {
            //判断是否是叶子节点，叶子节点是
            int row = tree.getClosestRowForLocation(e.getX(), e.getY());
            tree.setSelectionRow(row);
            popMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        TreePath path = tree.getPathForLocation(e.getX(), e.getY());
        if (path == null) {
            return;
        }
        tree.setSelectionPath(path);
        if (e.getButton() == 3) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node.isLeaf())  popMenu.show(tree, e.getX(), e.getY());
            else{
                fileMenu.show(tree,e.getX(),e.getY());
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        //判断是否是文件夹
        if (node.getChildCount()!=0){
            //这个代表是文件夹
            if (e.getSource()==renameFile){
                //重命名
//                System.out.println("重命名文件夹");
                int PathLength = tree.getSelectionPath().getPathCount();
                //获取前缀
                String prefix="/";
                for (int i =1;i<PathLength-1;i++){
                    prefix+=tree.getSelectionPath().getPathComponent(i).toString()+"/";
                }
                String old_file_name=prefix+node.toString();
                String new_file=JOptionPane.showInputDialog("请输入新文件名:");
                ftp.renameFile(old_file_name,new_file);
                node.setUserObject(new_file);
                System.out.println("重命名成功");
            }else if (e.getSource()==deleteFile){
                //获取文件名
                String prefix = TreeNode2FilePathPrefix();
                String fileName = node.toString();
                //删除服务器对应的文件
                ftp.deleteFile(prefix+fileName);
                //删除视图层的数据
                ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);
            }else if (e.getSource()==createFile){
                //新建文件夹
                String prefix = TreeNode2FilePathPrefix();
                String fileName = node.toString();
                //弹出窗口确定
                String newDictionary=JOptionPane.showInputDialog("请输入新文件名:");
                ftp.createNewFile(prefix+fileName+"/"+newDictionary);
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(newDictionary);
                node.add(child);
                System.out.println("新建成功");
            }else if (e.getSource()==uploadFile){
                //上传文件按钮
                int result = 0;
                File file = null;
                String path = null;
                JFileChooser fileChooser = new JFileChooser();
                FileSystemView fsv = FileSystemView.getFileSystemView();
                System.out.println(fsv.getHomeDirectory());                //得到桌面路径
                fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
                fileChooser.setDialogTitle("请选择要上传的文件...");
                fileChooser.setApproveButtonText("确定");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                result = fileChooser.showOpenDialog(null);
                if (JFileChooser.APPROVE_OPTION == result) {
                    path=fileChooser.getSelectedFile().getPath();
                    System.out.println("path: "+path);
                    try {
                        //获取当前的目录前缀
                        String prefix = TreeNode2FilePathPrefix();
                        String dirName = node.toString();
                        ftp.upload(prefix+dirName+"/",path);
                        System.out.println("文件上传成功");
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    //分割文件拿到最后一个
                    String[] fileNames = path.split("\\\\");
                    node.add(new DefaultMutableTreeNode(fileNames[fileNames.length-1]));
                    ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(node);
                }
            }
            return ;
        }
//        if (e.getSource() == addItem) {
//            ((DefaultTreeModel) tree.getModel()).insertNodeInto(new DefaultMutableTreeNode("新建文件"),
//                    node, node.getChildCount());
//            tree.expandPath(tree.getSelectionPath());
//        } else
        if (e.getSource() == delItem) {
            if (node.isRoot()) {
                return;
            }
            String prefix = TreeNode2FilePathPrefix();
            String fileName = node.toString();
            ftp.deleteFile(prefix+fileName);
            ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);
        } else if (e.getSource() == editItem) {
            //代表修改文件
            //获取前缀
            String prefix=TreeNode2FilePathPrefix();
            //旧文件名
            String old_file_name=prefix+node.toString();
            //获取旧文件的后缀
            String suffix = node.toString().split("\\.")[1];
            String new_file=JOptionPane.showInputDialog("请输入新文件名:");
            String new_file_name = prefix+new_file;
            //修改完前端的名字，得传输对应的名字给后端，进行相应的处理
            //服务端需要修改的文件名，传递的是相对路径的文件Path
            //服务端重命名之后的文件名是什么？
            //更新树节点对应的名字
            //服务器重新命名
            ftp.renameFile(old_file_name,new_file_name);
            node.setUserObject(new_file+"."+suffix);
            System.out.println("修改成功");
            ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(node);
        } else if (e.getSource()==downLoadItem){
            System.out.println("start download file");
            System.out.println(tree.getSelectionPath());

            //拿到路径长度
            int PathLength = tree.getSelectionPath().getPathCount();
            String from_file_name="";
            for (int i =1;i<PathLength;i++){
                from_file_name+="/"+tree.getSelectionPath().getPathComponent(i).toString();
            }
            //
            String local_file_name=tree.getSelectionPath().getLastPathComponent().toString();
            int result = 0;
            File file = null;
            String path = null;
            JFileChooser fileChooser = new JFileChooser();
            //调出系统界面
            FileSystemView fsv = FileSystemView.getFileSystemView();
            fsv.createFileObject(from_file_name);
            //System.out.println(fsv.getHomeDirectory());
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //fileChooser.setCurrentDirectory(new File(from_file_name));
            fileChooser.setDialogTitle("另存为:");
            //fileChooser.setApproveButtonText("保存");
            result = fileChooser.showSaveDialog(null);
            if (JFileChooser.APPROVE_OPTION == result) {
                path=fileChooser.getSelectedFile().getPath()+"\\"; //加"\\"是为了防止在桌面的时候C:destop最后没有\
                System.out.println("path: "+path);
                System.out.println("from_file_name:"+from_file_name);
                try {
                    //这是原本就封装好的service
                    ftp.download(from_file_name,local_file_name, path);
                    System.out.println("下载成功! ");
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finally{

//                Frame_Main.getFtp().close_connection();
                }
            }
        }
    }

    //显示基本信息-----------------------------------------------
    private void setTableInfo()
    {
        //初始化文件根节点
        root = new DefaultMutableTreeNode("FTP服务器根目录");
        treeModel = new DefaultTreeModel(root);
        //递归添加文件树
        for(int i=0;i<file.length;i++){
            if (file[i].isDirectory()){
                //递归添加
                try {
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(file[i].getName());
                    recurDir(child,ftp.getTargetFile("/"+file[i].getName()));
                    root.add(child);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(file[i].getName());
                root.add(child);
            }
        }

        //我真的佛了，重新绘制，记住一定得重新绘制，不然就掺了，然后控件基本只是声明一次，全局变量
        if (tree!=null) scrollPane.remove(tree);
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addMouseListener(this);
        tree.setCellEditor(new DefaultTreeCellEditor(tree, new DefaultTreeCellRenderer()));


        //重新绘制，我真的是佛了
        if (scrollPane!=null) getContentPane().remove(scrollPane);
        scrollPane = new JScrollPane();
        scrollPane.setBounds(32, 73, 400, 384);
        scrollPane.setVisible(true);
        getContentPane().add(scrollPane);
        scrollPane.setViewportView(tree);
        scrollPane.setVisible(true);

    }

    //设置根节点，设置当前的FTP目录
    public  void recurDir(DefaultMutableTreeNode root, FTPFile[] curFile) throws Exception {
        for (int i=0;i<curFile.length;i++){
            if (curFile[i].isDirectory()){
                //获取当前目录的所有
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(curFile[i].getName());
                root.add(child);
                //如果是文件夹，递归循环
                System.out.println(TreePrefix(root));
                recurDir(child,ftp.getTargetFile( TreePrefix(root)+"/"+curFile[i].getName()));
            }else{
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(curFile[i].getName());
                root.add(child);
            }
        }
    }

    //递归读取文件夹的时候获取前缀
    public String TreePrefix(DefaultMutableTreeNode root){
        StringBuilder sb = new StringBuilder();
        var path = root.getPath();
        for (var p:path){
            sb.append("/").append(p.toString());
        }
        return sb.toString();
    }

    /**
     * 获取点击的树节点的文件前缀
     * @return
     */
    public String TreeNode2FilePathPrefix() {
        int PathLength = tree.getSelectionPath().getPathCount();
        //获取前缀
        StringBuilder prefix= new StringBuilder("/");
        for (int i =1;i<PathLength-1;i++){
            prefix.append(tree.getSelectionPath().getPathComponent(i).toString()).append("/");
        }
        return prefix.toString();
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TreePopupMenu frame = new TreePopupMenu();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}