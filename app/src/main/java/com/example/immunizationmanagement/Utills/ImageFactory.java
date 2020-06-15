package com.example.immunizationmanagement.Utills;

import android.os.Environment;
import android.widget.Toast;

import com.example.immunizationmanagement.Activities.AddBabyActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ImageFactory {

    public static File getImage(String id) {


//        String[] filenames = new String[0];
        File pictureDirPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File path = new File(pictureDirPath + "/VaccinationImages");
//        if (path.exists()) {
//            filenames = path.list();
//        }

        File imageFile = FileUtils.getFile(path,id+ ".jpg");
//        SFile imageFile = null;
//        for (int i = 0; i < filenames.length; i++) {
//            if(filenames[i].contains(id)){
//                imageFile = new SFile(path.getPath() + "/" + filenames[i]);
//                break;
//            }
//
//        }
        return imageFile;
    }
    public static boolean deleteImage(String id) {


        File pictureDirPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File path = new File(pictureDirPath + "/VaccinationImages");

        try {
            FileUtils.forceDelete(new File(path,id+".jpg"));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean saveImage(String selectedImagePath,String imageName){

        File imageFile = new File(selectedImagePath);
        String extention = "jpg";

        File pictureDirPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageSaveDir = new File(pictureDirPath,"VaccinationImages");

        File newImageFile = new File(imageSaveDir,imageName+"."+extention);

        try {
            FileUtils.copyFile(imageFile,newImageFile);
            return true;

        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

}
