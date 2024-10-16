package com.redhat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@SessionScoped
public class FileUploadBean implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadBean.class);

    private final String tmpdir = System.getProperty("java.io.tmpdir");

    private UploadedFile file;

    public void upload() {

        if (file != null && file.getFileName() != null) {
            FacesMessage message = new FacesMessage("Success!", "File " + file.getFileName() + " was uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            LOG.info("Uploaded File Name :: {}, Uploaded File Size :: {} bytes", file.getFileName(), file.getSize());

            try {
                InputStream is = file.getInputStream();
                Path outputPath = Paths.get(tmpdir, file.getFileName());
                IOUtils.copy(is, Files.newOutputStream(outputPath));

                LOG.info(outputPath.toString());
                assert Files.exists(outputPath);

                File outputFile = outputPath.toFile();
                LOG.info("{}", outputFile.length());
                assert file.getSize() == outputFile.length();
            } catch (IOException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        } else {
            FacesMessage message = new FacesMessage("Warning", "Please select a file to upload.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            LOG.info("The user did not select a file to upload");
        }
    }

    public UploadedFile getFile() {

        return file;
    }

    public void setFile(UploadedFile file) {

        this.file = file;
    }

}
