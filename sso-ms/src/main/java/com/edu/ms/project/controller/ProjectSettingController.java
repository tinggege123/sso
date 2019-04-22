package com.edu.ms.project.controller;

import com.edu.ms.common.bean.BaseController;
import com.edu.ms.common.bean.EduResult;
import com.edu.ms.project.po.ProjectSettingPO;
import com.edu.ms.project.service.ProjectSettingService;
import com.edu.utils.SsoEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

//import com.edu.utils.SsoEncrypt;

/**
 * 子项目配置
 *
 * @author wst
 * @date 2019/1/5 11:43
 **/
@Slf4j
@Controller
@RequestMapping("projectSetting")
public class ProjectSettingController extends BaseController<ProjectSettingService, ProjectSettingPO> {

    /**
     * 项目文件绝对路径
     */
    private final String projectRealPath = System.getProperty("user.dir") + "\\"+"ssoclient" + ".properties";

    @Resource
    private ProjectSettingService projectSettingService;

    @ResponseBody
    @RequestMapping("saveProject")
    public EduResult saveData(ProjectSettingPO projectSettingPO) throws IOException {
        log.info("[保存子系统数据] 开始 req={}", projectSettingPO);
        try {
            //保存数据
            List<String> list = SsoEncrypt.generatorKeyPair();
            projectSettingPO.setPublicKey(list.get(0));
            projectSettingPO.setPrivateKey(list.get(1));
            projectSettingService.save(projectSettingPO);
            return EduResult.ok(projectSettingPO.getProjectName());
        }catch (DuplicateKeyException e){
            log.error("[保存子系统数据] 异常 req={}", projectSettingPO, e);
            return EduResult.build(507,"子系统名已存在");
        } catch (Exception e) {
            log.error("[保存子系统数据] 异常 req={}", projectSettingPO, e);
            return EduResult.error();
        }
    }

    @RequestMapping("downloadFile")
    public String download(String fileName, HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=utf-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        ProjectSettingPO projectSettingPO = projectSettingService.queryByCondition(ProjectSettingPO.builder().projectName(fileName).build());
        try {
            createProperties(projectSettingPO, projectRealPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;
        String downLoadPath = projectRealPath;

        try {
            long fileLength = new File(downLoadPath).length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String("ssoclient.properties".getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    /**
     * 创建子系统文件
     *
     * @param projectSettingPO
     * @return
     * @throws IOException
     */
    public static String createProperties(ProjectSettingPO projectSettingPO, String realPath) throws IOException {

        try{
            File file = new File(realPath);
            if(file.exists()){
                file.delete();
            }
        }catch (Exception e){
            log.error("[删除properties文件] 异常",e);
        }

        //创建Properties
        Properties properties = new Properties();
        properties.setProperty("sso.projectName", projectSettingPO.getProjectName());
        properties.setProperty("sso.clientDomain", projectSettingPO.getClientDomain());
        properties.setProperty("sso.clientPort", projectSettingPO.getClientPort());
        properties.setProperty("sso.serverDomain", projectSettingPO.getServerDomain());
        properties.setProperty("sso.serverPort", projectSettingPO.getServerPort());
        properties.setProperty("sso.clientPort", projectSettingPO.getClientPort());
        properties.setProperty("sso.publicKey", projectSettingPO.getPublicKey());

        //遍历Properties
        Set<Entry<Object, Object>> entrys = properties.entrySet();
        for (Entry<Object, Object> entry : entrys) {
            System.out.println("键：" + entry.getKey() + "值：" + entry.getValue());
        }
        String filePathName = realPath;
        properties.store(new FileWriter(filePathName), "sso setting");
        return projectSettingPO.getProjectName() + ".properties";
    }

}
