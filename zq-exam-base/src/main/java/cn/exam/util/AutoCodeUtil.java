package cn.exam.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

//

public class AutoCodeUtil {
    private static final String DRIVE = "com.mysql.jdbc.Driver";

    /**
     * 生成VO
     * @param url
     * @param username
     * @param password
     * @param table
     * @param packageName
     * @param modelName
     * @throws Exception
     */
    public static void autoVO(String url,String username,String password,String table,String packageName,String modelName) throws Exception {
        Class.forName(DRIVE);
        Connection cn = DriverManager.getConnection(url,username, password);
        String sql = "SELECT column_name,column_comment,column_type FROM information_schema.Columns where table_name=?";
        //获取SQL语句预编译对象
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1,table);
        //执行SQL语句
        ResultSet rs = ps.executeQuery();
        //创建对象集合
        List<Table> list = new ArrayList<>();
        //处理结果集
        while(rs.next()){
            Table t=new Table();
            t.setColumn_comment(rs.getString("column_comment"));
            t.setColumn_name(rs.getString("column_name"));
            t.setColumn_type(cover(rs.getString("column_type")));
            list.add(t);
        }
        StringBuffer sb=new StringBuffer();
        StringBuilder sb1=new StringBuilder();
        sb.append("package "+packageName+";\n" +
                "/**\n" +
                " * @File: "+coverUpClass(table)+"DTO\n" +
                " * @Author: ys\n" +
                " * @Date: 2020/5/20 05:20\n" +
                " * @Description:\n" +
                " */\n" +
                "public class "+coverUpClass(table)+"VO implements Serializable {\n");
        for (Table table1:list){
            sb.append("    /**\n" +
                    "     * "+table1.getColumn_comment()+"\n" +
                    "     */\n" +
                    "    private "+table1.getColumn_type()+" "+coverUp(table1.getColumn_name())+";\n");
            sb1.append(" public "+table1.getColumn_type()+" get"+coverUpClass(table1.getColumn_name())+"() {\n" +
                    "        return "+coverUp(table1.getColumn_name())+";\n" +
                    "    }\n" +
                    "\n" +
                    "    public void set"+coverUpClass(table1.getColumn_name())+"("+table1.getColumn_type()+" "+coverUp(table1.getColumn_name())+") {\n" +
                    "        this."+coverUp(table1.getColumn_name())+" = "+coverUp(table1.getColumn_name())+";\n" +
                    "    }");
        }
        sb.append(sb1);
        sb.append("}");
        String resourceUrl = getResourceUrl(packageName,modelName);
        File file = new File(resourceUrl + "\\" + coverUpClass(table) + "VO.java");
        createFile(file,sb,resourceUrl);
    }

    public static void autoSO(String url,String username,String password,String table,String packageName,String modelName) throws Exception {
        Class.forName(DRIVE);
        Connection cn = DriverManager.getConnection(url,username, password);
        String sql = "SELECT column_name,column_comment,column_type FROM information_schema.Columns where table_name=?";
        //获取SQL语句预编译对象
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1,table);
        //执行SQL语句
        ResultSet rs = ps.executeQuery();
        //创建对象集合
        List<Table> list = new ArrayList<>();
        //处理结果集
        while(rs.next()){
            Table t=new Table();
            t.setColumn_comment(rs.getString("column_comment"));
            t.setColumn_name(rs.getString("column_name"));
            t.setColumn_type(cover(rs.getString("column_type")));
            list.add(t);
        }
        StringBuffer sb=new StringBuffer();
        StringBuilder sb1=new StringBuilder();
        sb.append("package "+packageName+";\n" +
                "/**\n" +
                " * @File: "+coverUpClass(table)+"SO\n" +
                " * @Author: ys\n" +
                " * @Date: 2020/5/20 05:20\n" +
                " * @Description:\n" +
                " */\n" +
                "public class "+coverUpClass(table)+"SO implements Serializable {\n");
        for (Table table1:list){
            sb.append("    /**\n" +
                    "     * "+table1.getColumn_comment()+"\n" +
                    "     */\n" +
                    "    private "+table1.getColumn_type()+" "+coverUp(table1.getColumn_name())+";\n");
            sb1.append(" public "+table1.getColumn_type()+" get"+coverUpClass(table1.getColumn_name())+"() {\n" +
                    "        return "+coverUp(table1.getColumn_name())+";\n" +
                    "    }\n" +
                    "\n" +
                    "    public void set"+coverUpClass(table1.getColumn_name())+"("+table1.getColumn_type()+" "+coverUp(table1.getColumn_name())+") {\n" +
                    "        this."+coverUp(table1.getColumn_name())+" = "+coverUp(table1.getColumn_name())+";\n" +
                    "    }");
        }
        sb.append(sb1);
        sb.append("}");
        String resourceUrl = getResourceUrl(packageName,modelName);
        File file = new File(resourceUrl + "\\" + coverUpClass(table) + "SO.java");
        createFile(file,sb,resourceUrl);
    }

    /**
     * 生成Mapper
     * @param table
     * @param packageName
     * @throws UnsupportedEncodingException
     */
    public static void autoMapper(String table,String packageName,String modelName) throws UnsupportedEncodingException {
        StringBuffer sb=new StringBuffer();
        sb.append("/**\n" +
                " * \tCopyright 2020 www.zj.cn\n" +
                " *\n" +
                " * \tAll right reserved\n" +
                " *\n" +
                " * \tCreate on 2020/5/20 05:20\n" +
                " */\n" +
                "package "+packageName+";\n" +
                "\n" +
                "import cn.exam.dao.mapper.base.CommonBaseMapper;\n" +
                "\n" +
                "\n" +
                "/**\n" +
                " * @File: "+coverUpClass(table)+"\n" +
                " * @Author: ys\n" +
                " * @Date: 2020/5/20 05:20\n" +
                " * @Description: \n" +
                " */\n" +
                "public interface "+coverUpClass(table)+"Mapper\n" +
                "        extends CommonBaseMapper<"+coverUpClass(table)+"> {\n" +
                "}");

        String resourceUrl = getResourceUrl(packageName,modelName);
        File file = new File(resourceUrl + "\\" + coverUpClass(table) + "Mapper.java");
        createFile(file,sb,resourceUrl);
    }



    private static void createFile(File file, StringBuffer sb,String resourceUrl){
        try {
            File file1=new File(resourceUrl);
            if(!file1.exists()){//如果文件夹不存在
                file1.mkdir();//创建文件夹
            }

            // write
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception e) {

        }
    }

    /**
     * 生成domain
     * @param url
     * @param username
     * @param password
     * @param table
     * @param packageName
     * @throws Exception
     */
    public static void autoDomain(String url,String username,String password,String table,String packageName,String modelName) throws Exception {
        Class.forName(DRIVE);
        Connection cn = DriverManager.getConnection(url,username, password);
        String sql = "SELECT column_name,column_comment,column_type FROM information_schema.Columns where table_name=?";
        //获取SQL语句预编译对象
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1,table);
        //执行SQL语句
        ResultSet rs = ps.executeQuery();
        //创建对象集合
        List<Table> list = new ArrayList<>();
        //处理结果集
        while(rs.next()){
            Table t=new Table();
            t.setColumn_comment(rs.getString("column_comment"));
            t.setColumn_name(rs.getString("column_name"));
            t.setColumn_type(cover(rs.getString("column_type")));
            list.add(t);
        }
        StringBuilder sb=new StringBuilder();
        String date = DateUtil.getCurrentDateTime();
        sb.append("package "+packageName+";\n" +
                "/**\n" +
                " * @File: "+coverUpClass(table)+"\n" +
                " * @Author: ys\n" +
                " * @Date: "+date+"\n" +
                " * @Description:\n" +
                " */\n" +
                "@Data\n" +
                "@Table(name = \""+table+"\")\n" +
                "public class "+coverUpClass(table)+" implements Serializable {\n");
        for (Table table1:list){
            sb.append("    /**\n" +
                    "     * "+table1.getColumn_comment()+"\n" +
                    "     */\n" +
                    "    @Column(name =\""+table1.getColumn_name()+"\")\n" +
                    "    private "+table1.getColumn_type()+" "+coverUp(table1.getColumn_name())+";\n");
        }
        sb.append("}");
        String resourceUrl = getResourceUrl(packageName,modelName);
        File file1=new File(resourceUrl);
        if(!file1.exists()){//如果文件夹不存在
            file1.mkdir();//创建文件夹
        }
        File file = new File(resourceUrl + "\\" + coverUpClass(table) + ".java");

        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        fw.close();

    }


    private static String coverUpClass(String str){
        List<String> list = Arrays.asList(str.split("_"));
        StringBuilder sb=new StringBuilder();
        for (String s : list) {
            String s1 = s.toUpperCase();
            StringBuilder up = new StringBuilder(s1.substring(0, 1));
            up.append(s.substring(1, s.length()));
            sb.append(up);
        }
        return sb.toString();
    }

    private static String coverUp(String str){
        List<String> list = Arrays.asList(str.split("_"));
        StringBuilder sb=new StringBuilder(list.get(0));
        for (int i = 1;i<list.size();i++){
            String s = list.get(i);
            String s1 = s.toUpperCase();
            StringBuilder up =new StringBuilder(s1.substring(0,1));
            up.append(s.substring(1,s.length()));
            sb.append(up);
        }
        return sb.toString();
    }

    private static String cover(String type){
        if (type.contains("varchar")){
            return "String";
        }
        if (type.contains("int")){
            return "Integer";
        }
        if (type.contains("decimal")){
            return "BigDecimal";
        }
        return null;
    }

    public static String getResourceUrl(String packageName, String modelName) throws UnsupportedEncodingException {
        String replace = packageName.replace(".", "/");
        String decode = URLDecoder.decode(Class.class.getResource("/").toString(), "UTF-8");
        List<String> list = Arrays.asList(decode.split("/"));
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isBlank(modelName)) {
            for (int i = 1; i < list.size() - 2; i++) {
                sb.append(list.get(i)).append("/");
            }
        }else{
            for (int i = 1; i < list.size() - 3; i++) {
                sb.append(list.get(i)).append("/");
            }
            sb.append(modelName);
        }
        sb.append("/src/main/java/").append(replace);
        return sb.toString();
    }


    /**
     * 错误枚举自动生成类
     * @param fromPackage 来源
     * @param toPackage 生成位置
     */
    public static void autoCodeErrorEnum(String fromPackage,String toPackage,Boolean bool) throws UnsupportedEncodingException, MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //获取包下所有类名
        Set<String> className = getClassName(fromPackage, false);
        for (String name:className){
            Object o = Class.forName(name).newInstance();
            print(o,toPackage,bool);
        }
    }

    public static String print(Object obj,String toPackage,Boolean bool) throws UnsupportedEncodingException {
        String className = obj.getClass().getSimpleName();
        className=className.replace("Service","").replace("SO","");
        String classNameLow = WordUtils.uncapitalize(className);
        StringBuffer sb = new StringBuffer();
        sb.append("/**\n" +
                " * \tCopyright 2020 www.zj.cn\n" +
                " * \n" +
                " * \tAll right reserved\n" +
                " * \n" +
                " * \tCreate on 2020/5/20 05:20\n" +
                " */\n" +
                "package "+""+";\n" +
                "/**\n" +
                " * @File: " + className + "Test.java\n" +
                " * @Author: ys\n" +
                " * @Description: \n" +
                " */\n" +
                "public enum " + className + "Enum {"+
                "\n"
        );

        getClassParamName(obj, sb);

        sb.append(
                "\n" +
                        "    " + className + "Enum(String errCode, String errMsg){\n" +
                        "        this.errCode = errCode;\n" +
                        "        this.errMsg = errMsg;\n" +
                        "    }\n" +
                        "    \n" +
                        "    private String errCode;\n" +
                        "    private String errMsg;\n" +
                        "\n" +
                        "    @Override\n" +
                        "    public String getErrCode() {\n" +
                        "        return errCode;\n" +
                        "    }\n" +
                        "\n" +
                        "    @Override\n" +
                        "    public String getErrMsg() {\n" +
                        "        return errMsg;\n" +
                        "    }\n" +
                        "\n" +
                        "}");
        if(bool) {

            File file = new File(toPackage + "\\" + className + "Enum.java");
            try {
                // write
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(sb.toString());
                bw.flush();
                bw.close();
                fw.close();
            } catch (Exception e) {

            }
        }
        return sb.toString();
    }


    /**
     * 自动生成枚举参数
     * @param obj
     * @param sb
     */
    public static void getClassParamName(Object obj, StringBuffer sb) {
        Class cls = obj.getClass();
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {//遍历
            //得到属性
            //打开私有访问
            field.setAccessible(true);
            //获取属性
            String name = field.getName();
            if (name.equals("serialVersionUID")) {
                continue;
            }
            String str = "    " + name.toUpperCase() + "_IS_NULL(\"1001\", \"参数[" + name + "]不能为空\"),\n";
            sb.append(str);
        }
        sb.append("    ;\n");
    }



    /**
     * 获取某包下所有类
     * @param packageName 包名
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    public static Set<String> getClassName(String packageName, boolean isRecursion) throws UnsupportedEncodingException, MalformedURLException {
        Set<String> classNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");

        URL url = loader.getResource(packagePath);
        packagePath = URLDecoder.decode(url.toString(),"utf-8");
        url=new URL(packagePath);
        if (url != null) {
            String protocol = url.getProtocol();
            if (protocol.equals("file")) {
                classNames = getClassNameFromDir(url.getPath(), packageName, isRecursion);
            } else if (protocol.equals("jar")) {
                JarFile jarFile = null;
                try{
                    jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                } catch(Exception e){
                    e.printStackTrace();
                }

                if(jarFile != null){
                    getClassNameFromJar(jarFile.entries(), packageName, isRecursion);
                }
            }
        } else {
            /*从所有的jar包中查找包名*/
            classNames = getClassNameFromJars(((URLClassLoader)loader).getURLs(), packageName, isRecursion);
        }

        return classNames;
    }

    /**
     * 从项目文件获取某包下所有类
     * @param filePath 文件路径
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isRecursion) {
        Set<String> className = new HashSet<>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File childFile : files) {
            if (childFile.isDirectory()) {
                if (isRecursion) {
                    className.addAll(getClassNameFromDir(childFile.getPath(), packageName+"."+childFile.getName(), isRecursion));
                }
            } else {
                String fileName = childFile.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    className.add(packageName+ "." + fileName.replace(".class", ""));
                }
            }
        }

        return className;
    }

    /**
     * @param jarEntries
     * @param packageName
     * @param isRecursion
     * @return
     */
    private static Set<String> getClassNameFromJar(Enumeration<JarEntry> jarEntries, String packageName, boolean isRecursion){
        Set<String> classNames = new HashSet<>();

        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if(!jarEntry.isDirectory()){
                /*
                 * 这里是为了方便，先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug
                 * (FIXME: 先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug)
                 */
                String entryName = jarEntry.getName().replace("/", ".");
                if (entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(packageName)) {
                    entryName = entryName.replace(".class", "");
                    if(isRecursion){
                        classNames.add(entryName);
                    } else if(!entryName.replace(packageName+".", "").contains(".")){
                        classNames.add(entryName);
                    }
                }
            }
        }

        return classNames;
    }


    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     * @param urls URL集合
     * @param packageName 包路径
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<String> getClassNameFromJars(URL[] urls, String packageName, boolean isRecursion) {
        Set<String> classNames = new HashSet<>();

        for (URL url : urls) {
            String classPath = url.getPath();

            //不必搜索classes文件夹
            if (classPath.endsWith("classes/")) {
                continue;
            }

            JarFile jarFile = null;
            try {
                jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (jarFile != null) {
                classNames.addAll(getClassNameFromJar(jarFile.entries(), packageName, isRecursion));
            }
        }

        return classNames;
    }

}

