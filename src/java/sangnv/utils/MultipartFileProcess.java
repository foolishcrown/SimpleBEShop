/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author Shang
 */
public class MultipartFileProcess {

    private static final String UPLOAD_DIRECTORY = "images";

    public static HashMap<String, String> getParams(ServletContext getServletContext, HttpServletRequest request) throws FileUploadException, Exception {
        //Create save path
        String uploadDirPath = getServletContext.getRealPath("").replace("\\build", "")
                + File.separator
                + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        ArrayList<FileItem> fileItems;
        HashMap<String, String> params = new HashMap<>();
        fileItems = (ArrayList<FileItem>) fileUpload.parseRequest(new ServletRequestContext(request));
        for (FileItem fileItem : fileItems) {
            if (fileItem.isFormField()) {
                params.put(fileItem.getFieldName(), fileItem.getString());
            } else {
                String fullPathFileName = fileItem.getName();
                if (!fullPathFileName.trim().isEmpty()) {
                    String fileName = fullPathFileName
                            .substring(fullPathFileName.lastIndexOf("\\") + 1);
                    String rewriteFileName = RandomId.generateId() + "-" + fileName;
                    String fullPath = uploadDirPath + File.separator + rewriteFileName;
                    fileItem.write(new File(fullPath));
                    String imageURL = "images/" + rewriteFileName;
                    params.put(fileItem.getFieldName(), imageURL);
                    params.put("fullPathURL", fullPath);
                }
            }
        }
        return params;
    }

}
