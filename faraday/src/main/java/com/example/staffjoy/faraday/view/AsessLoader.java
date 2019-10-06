package com.example.staffjoy.faraday.view;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Component
public class AsessLoader {
     private static final String FAVINCON_ICO = "/static/assets/images/favicon.ico";
     private static final String IMAGER_ICO_PATH = "/static/assets/images/favicon.ico";

     public static String imageBase64;
     public static byte[] favinconBytes;

     @PostConstruct
     public void init() throws IOException {
         InputStream favinconStream  = this.getImageFile(FAVINCON_ICO);
         favinconBytes = IOUtils.toByteArray(favinconStream);

         InputStream imagerico = this.getImageFile(IMAGER_ICO_PATH);
         byte[] imageBytes = IOUtils.toByteArray(imagerico);
         imageBase64 = Base64Utils.encodeToString(imageBytes);
     }

     public InputStream getImageFile(String path) throws IOException {
         return new ClassPathResource(path).getInputStream();
     }
}
