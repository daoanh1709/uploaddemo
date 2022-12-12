/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mypackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author PC
 */
@Named(value = "fileUploadMB")
@RequestScoped
public class FileUploadMB {

    private Part file;
    private final String UPLOAD_DIRECTORY = "uploads";

    public FileUploadMB() {
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void uploadFile() {
        if (file != null) {
            InputStream content = null;
            try {
                content = file.getInputStream();

                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext ec = context.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) ec.getRequest();

                String applicationPath = request.getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + UPLOAD_DIRECTORY;

                File fileSaveDir = new File(uploadFilePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdirs();
                }

                OutputStream outputStream = null;
                try {
                    File outputFilePath = new File(uploadFilePath + File.separator + file.getSubmittedFileName());
                    content = file.getInputStream();
                    outputStream = new FileOutputStream(outputFilePath);
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = content.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                    System.out.println("File uploaded successfully!");
                } catch (Exception ex) {
                    ex.toString();
                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (content != null) {
                        content.close();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUploadMB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    content.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileUploadMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
