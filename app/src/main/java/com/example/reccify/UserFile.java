package com.example.reccify;


import static com.example.reccify.AccountActivity.getContext;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class UserFile extends AppCompatActivity {
    private ArrayList<String> fileStats = new ArrayList<String>();
    private ArrayList<String> fileStrings = new ArrayList<String>();
    private String fileName = "";
    private String userName = "";
    private String password = "";

    public UserFile(String nameOfFile, String nameOfUser, String userPassword) {
        fileName = nameOfFile;
        userName = nameOfUser;
        password = userPassword;
        fileStats.add("File Name: " + fileName);
        fileStats.add("Username: " + userName);
        fileStats.add("Password: " + password);
        fileStats.add("Saved Artists:; ; ");
        fileStats.add("Saved Songs:* * ");
        fileStats.add("Prefferred Genres:, , ");
        if (!fileFound(fileName)) {
            writeFile("");

        }


    }

    public String getFileName() {
        return fileName;
    }

    public String getUsername() {
        return userName;
    }

    public void writeFile(String content) {
        boolean found = false;
        String str = "";
        if (fileFound(fileName)) {
            str = readFromFile(fileName);
            found = true;
        }
        String newContent = "";
        File path = getContext().getFilesDir();
        try {

            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            if (!found) {
                for (String s : fileStats) {
                    newContent = newContent + s + " ";
                }
            }
            newContent = newContent + str + content;
            writer.write(newContent.getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean fileFound(String nameOfFile) {
        File path = getContext().getFilesDir();
        File readFrom = new File(path, nameOfFile);
        try {
            FileInputStream stream = new FileInputStream(readFrom);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setFileText(String fileName, String content) {
        String newContent = content;
        File path = getContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            writer.write(newContent.getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String nameOfFile) {

        File path = getContext().getFilesDir();
        File readFrom = new File(path, nameOfFile);
        byte[] content = new byte[(int) readFrom.length()];
        try {

            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            return new String(content);
        } catch (Exception e) {

            return "";
        }
    }

    public static boolean searchForFile(String nameOfFile, String nameOfUser, String userPassword) {
        String fileContent = readFromFile(nameOfFile);
        Log.d("sd", fileContent);
        int uIndex = fileContent.indexOf("Username: ");
        int pIndex = fileContent.indexOf("Password: ");
        int sIndex = fileContent.indexOf("Saved Artists:");
        if (uIndex <= -1 && pIndex <= -1) {
            return false;
        }


        String usernameKey = fileContent.substring(uIndex + 10, pIndex - 1);
        String passwordKey = fileContent.substring(pIndex + 10, sIndex - 1);
        boolean verifyUsername = usernameKey.equals(nameOfUser);
        boolean verifyPassword = passwordKey.equals(userPassword);
        if (fileFound(nameOfFile) && verifyUsername && verifyPassword) {
            return true;
        } else {
            return false;
        }


    }
}
