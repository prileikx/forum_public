package tech.aaaaaa.user;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "uploadServlet", value = "/uploadServlet")
public class uploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();

        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        PrintWriter writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        String uid = null;
        String verifycode = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("uid")) {
                    uid = cookie.getValue();
                } else if (name.equals("verifycode")) {
                    verifycode = cookie.getValue();
                }
            }
        }
        if (uid == null || verifycode == null) {
            writer.print("{\"msg\":\"用户验证错误,请重新登陆\"}");
        } else {
            User user = userMapper.selectuser(Integer.parseInt(uid), verifycode);
            if (user != null) {
                String username = user.getUsername();
                //判断上传的表单是普通表单还是带文件表单
                if (!ServletFileUpload.isMultipartContent(request)) {//如果不是带文件表单
                    return;//终止方法运行,直接返回
                }

                try {
                    //创建上传文件的保存路径,建议在WEB-INF路径下,安全,用户无法直接访问上传的文件.
                    String uploadPath = this.getServletContext().getRealPath("/resource/img/upload");
                    File uploadFile = new File(uploadPath);
                    if (!uploadFile.exists()) {
                        uploadFile.mkdirs();//如果不存在则创建目录
                    }

                    //缓存,临时文件
                    //临时文件,假如文件超出预期大小,就把它放到临时文件夹中,过几天自动删除,或者提醒用户转存为永久文件
                    String tmpPath = this.getServletContext().getRealPath("/resource/img/tmp");
                    File tmpFile = new File(tmpPath);
                    if (!tmpFile.exists()) {
                        tmpFile.mkdirs();//如果不存在则创建临时目录
                    }

                    //1.创建DiskFileItemFactory对象,处理文件上传路径或者大小限制
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    //通过这个工厂设置一个缓冲区,当上传的文件大于这个缓冲区的时候,将它放在临时文件中
                    factory.setSizeThreshold(1024 * 1024);//缓冲区大小为1M
                    factory.setRepository(tmpFile);//临时文件的保存目录

                    //2.获取ServletFileUpload对象
                    ServletFileUpload upload = new ServletFileUpload(factory);

                    //监听文件上传进度
                    upload.setProgressListener(new ProgressListener() {
                        @Override
                        public void update(long l, long l1, int i) {
                            System.out.println("总大小: " + l1 + " 已上传: " + l);
                        }
                    });

                    //处理乱码问题
                    upload.setHeaderEncoding("utf-8");
                    //设置单个文件的最大值
                    upload.setFileSizeMax(1024 * 1024 * 10 * 10);//100M
                    //设置总共能够上传文件的大小
                    upload.setSizeMax(1024 * 1024 * 10 * 10);//100M

                    //3.处理上传文件
                    //把前端请求解析,封装成一个FileItem对象
                    List<FileItem> fileItems = upload.parseRequest(request);

                    for (FileItem fileItem : fileItems) {
                        //判断上传的表单是普通表单还是带文件表单
                        if (fileItem.isFormField()) {
                            String name = fileItem.getFieldName();//获取表单控件的名字
                            String value = fileItem.getString("UTF-8");//获取值,处理乱码
                            System.out.println(name + ": " + value);
                        } else {//文件
                            String uploadFileName = fileItem.getName();//获取上传文件名字(带路径)
                            //可能存在文件名不合法的情况
                            if (uploadFileName == null || uploadFileName.trim().equals("")) {
                                continue;
                            }
                            //截取上传的文件名
                            String FileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);//从最后一个/后开始截取
                            //截取后缀名
                            String fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);//从最后一个.后开始截取

                            //网络传输中的东西,都需要序列化
                            //POJO , 实体类, 如果想要在多个电脑运行, 传输-->需要把对象序列化
                            //JNI= java native Interface
                            //implements Serializable : 标记接口 , JVM-->java栈 本地方法栈 native -->C++

                            System.out.println(FileName);
                            System.out.println(fileExtName);
                            response.getWriter().print(FileName);
                            //可以使用UUID(唯一标识的通用码),保证文件名唯一
                            String uuidPath = UUID.randomUUID().toString();//生成一共随机的uuid
                            System.out.println(uuidPath);

                            //==========================创建存放目录========================//
                            String realPath = uploadPath + "/" + uuidPath;
                            //给每个文件创建一个对应的文件夹
//                    File realPathFile = new File(realPath);
//                    if (!realPathFile.exists()){
//                        realPathFile.mkdirs();
//                    }

                            //==========================文件传输====================================//
                            //获取文件上传的流
                            InputStream inputStream = fileItem.getInputStream();
                            //创建一个输出文件的流
                            FileOutputStream fos = new FileOutputStream(realPath + "." + fileExtName);
                            userMapper.uploadimg(uuidPath + "." + fileExtName, Integer.valueOf(uid));
                            //创建缓冲区
                            byte[] buffer = new byte[1024];
                            //判断是否读取完毕
                            int len = 0;
                            while ((len = ((InputStream) inputStream).read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                            //关闭流
                            fos.close();
                            inputStream.close();
                        }
                    }
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
            }
            else {
                writer.print("{\"msg\":\"用户验证错误,请重新登陆\"}");
            }
        }
    }
}
