//package com.inventory.service.impl;
//
//import com.inventory.service.FileUploadService;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//public class FileUploadServiceImpl implements FileUploadService {
//    @Override
//    public String uploadImage(String path, MultipartFile file) {
//        String orginalFileName = file.getOriginalFilename();
//        String randomImageName = UUID.randomUUID().toString();
//        String randomImageNameWithExtention = randomImageName.concat(orginalFileName.substring(orginalFileName.lastIndexOf(".")));
//          String fullpath =  File.separator+randomImageNameWithExtention ;
//          File folderFile = new File(path);
//          if(!folderFile.exists()){
//             folderFile.mkdir();
//             try {
//                 Files.copy(file.getInputStream(), Paths.get(fullpath));
//             } catch (IOException e){
//                 e.printStackTrace();
//             }
//          }
//
//        return randomImageNameWithExtention;
//    }
//}
