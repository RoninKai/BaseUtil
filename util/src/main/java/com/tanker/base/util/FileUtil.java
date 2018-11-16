package com.tanker.base.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Tanker
 * @email :zhoukai@zto.cn
 * @date : 2018/08/28
 * @describe : 文件工具类
 */
public class FileUtil {

    /**
     * crash异常信息写入文件
     *
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    @SuppressLint("SimpleDateFormat")
    public static void writeFileAsException(Context context, String filePath, String fileName, Throwable ex) throws IOException, PackageManager.NameNotFoundException {
        if (hasSDCard() && createFolde(filePath)) {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(fileName))));
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            pw.println("发生异常时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            pw.println("应用版本：" + pi.versionName);
            pw.println("应用版本号：" + pi.versionCode);
            pw.println("android版本号：" + Build.VERSION.RELEASE);
            pw.println("android版本号API：" + Build.VERSION.SDK_INT);
            pw.println("手机制造商:" + Build.MANUFACTURER);
            pw.println("手机型号：" + Build.MODEL);
            ex.printStackTrace(pw);
            pw.flush();
            pw.close();
        }
    }

    /**
     * 文件写入
     *
     * @param fileName
     * @param writeStr
     */
    public static void writeFileOld(String filePath, String fileName, String writeStr) {
        String foldeCreatPath = getSDpath() + filePath;
        if (!createFolde(foldeCreatPath)) {
            return;
        }
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(foldeCreatPath + fileName);
            byte[] bytes = writeStr.getBytes();
            fout.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 文件读取
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static String readFileOld(String filePath, String fileName) {
        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(getSDpath() + filePath, fileName);
            bufferedReader = new BufferedReader(new FileReader(file));
            String readline = "";
            while ((readline = bufferedReader.readLine()) != null) {
                sb.append(readline);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();

    }

    /**
     * 创建文件夹
     *
     * @param filePath
     * @return
     */
    private static boolean createFolde(String filePath) {
        File dir = new File(filePath);
        if (dir.exists()) {
            return true;
        }
        return dir.mkdirs();
    }

    /**
     * 获取是否有SD卡
     *
     * @return
     */
    private static boolean hasSDCard() {
        return TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    private static File getSDpath() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 回调将信息写入文件
     *
     * @param filePath
     * @param fileName
     * @param printCallBack
     */
    @TargetApi(Build.VERSION_CODES.N_MR1)
    private static void writeFile(String filePath, String fileName, FilePrintCallBack printCallBack) {
        String foldeCreatPath = getSDpath() + filePath;
        if (!createFolde(foldeCreatPath)) {
            printCallBack.noFindSD();
            return;
        }
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter(foldeCreatPath + fileName);
            pw = new PrintWriter(fw);
            printCallBack.print(pw);
            pw.flush();
            printCallBack.onSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            printCallBack.onError(e);
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    printCallBack.onError(e);
                }
            }
        }
    }

    /**
     * 写入一个文件
     *
     * @param filePath
     * @param fileName
     * @param fileContent
     */
    @TargetApi(Build.VERSION_CODES.N_MR1)
    private static void writeFile(String filePath, String fileName, final String fileContent) {
        writeFile(filePath, fileName, new FilePrintCallBack() {
            @Override
            public void print(PrintWriter printWriter) {
                printWriter.print(fileContent);
            }
        });
    }

    /**
     * 读取文件
     *
     * @param filePath
     * @param fileName
     * @return
     */
    @TargetApi(Build.VERSION_CODES.N_MR1)
    private static String writeReader(String filePath, String fileName) {
        String fileReadPath = getSDpath() + filePath + fileName;
        FileReader fileReader = null;
        StringBuilder content = new StringBuilder();
        try {
            fileReader = new FileReader(fileReadPath);
            int ch = 0;
            while ((ch = fileReader.read()) != -1) {
                content.append((char) ch);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return content.toString();
    }

    public static abstract class FilePrintCallBack {

        public abstract void print(PrintWriter printWriter);

        public void onSuccess(){
        }

        public void onError(IOException exception){
        }

        public void noFindSD(){
        }

    }

}