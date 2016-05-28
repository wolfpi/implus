package com.baidu.imc.impl.im.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.text.TextUtils;

import com.baidu.imc.callback.ResultCallback;
import com.baidu.imc.client.LocalResourceManager;
import com.baidu.imc.impl.im.util.FileUtil;

/**
 *
 * <b>本地资源管理器</b>
 * <p>
 * 用于本地文件资源的读取和保存，SDK提供默认本地资源管理器，开发者可使用自己的本地资源管理器替代系统默认的
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public class BDHiLocalResourceManager implements LocalResourceManager {
    private String saveRootPath = BDHiIMConstant.LOCAL_RES_PATH;

    public void saveFile(InputStream is, String fileName, ResultCallback<String> result) {
        String filePath = null;
        Exception exception = null;
        if (null != is) {
            File rootFile = FileUtil.getSDRootFile();
            if (null != rootFile) {
                FileOutputStream fos = null;
                try {
                    File saveFile = getTargetSaveFile(rootFile, fileName);
                    fos = new FileOutputStream(saveFile);
                    int bufferSize = 50*1024;
                    byte[] buffer = new byte[bufferSize];
                    int readBytes;
                    while ((readBytes = is.read(buffer, 0, bufferSize)) >= 0) {
                        fos.write(buffer, 0, readBytes);
                    }
                    filePath = saveFile.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                    exception = e;
                } finally {
                    if (null != fos) {
                        try {
                            fos.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            exception = e;
                        }
                    }
                }
            }
        }
        if (null != result) {
            result.result(filePath, exception);
        }
    }

    @Override
    public void saveFile(InputStream is, ResultCallback<String> result) {
        String filePath = null;
        Exception exception = null;
        if (null != is) {
            File rootFile = FileUtil.getSDRootFile();
            if (null != rootFile) {
                FileOutputStream fos = null;
                try {
                    File saveFile = getTargetSaveFile(rootFile);
                    fos = new FileOutputStream(saveFile);
                    int bufferSize = 50*1024;
                    byte[] buffer = new byte[bufferSize];
                    int readBytes;
                    while ((readBytes = is.read(buffer, 0, bufferSize)) >= 0) {
                        fos.write(buffer, 0, readBytes);
                    }
                    filePath = saveFile.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                    exception = e;
                } finally {
                    if (null != fos) {
                        try {
                            fos.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            exception = e;
                        }
                    }
                }
            }
        }
        if (null != result) {
            result.result(filePath, exception);
        }
    }

    /**
     * 
     * <b>使用文件流保存数据</b>
     * 
     * 
     * @param bytes 文件数据流
     * @param result 保存完毕后返回文件路径
     * @since 1.0
     */
    @Override
    public String saveFile(byte[] bytes) {
        String filePath = null;
        if (null != bytes && bytes.length > 0) {
            File rootFile = FileUtil.getSDRootFile();
            if (null != rootFile) {
                FileOutputStream fos = null;
                try {
                    File saveFile = getTargetSaveFile(rootFile);
                    fos = new FileOutputStream(saveFile);
                    fos.write(bytes);
                    filePath = saveFile.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filePath;
    }

    private File getTargetSaveFile(File rootFile) throws IOException {
        long fileIndex = System.currentTimeMillis();
        File saveFile = new File(rootFile + saveRootPath + "/" + fileIndex);
        while (saveFile.exists()) {
            fileIndex++;
            saveFile = new File(rootFile + saveRootPath + "/" + fileIndex);
        }
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdir();
        }
        saveFile.createNewFile();
        return saveFile;
    }

    private File getTargetSaveFile(File rootFile, String fileName) throws IOException {
        File saveFile = new File(rootFile + saveRootPath + "/" + fileName);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }
        return saveFile;
    }

    /**
     *
     * <b>读取文件</b>
     * <p>
     * 使用给定filePath获取文件内容
     * </p>
     *
     * @since 1.0
     *
     * @param filePath 文件本地路径
     * @return 文件内容
     */
    @Override
    public byte[] loadFile(String filePath) {
        byte[] bytes = null;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(filePath);
                    int length = fis.available();
                    bytes = new byte[length];
                    fis.read(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != fis) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return bytes;
    }

    /**
     *
     * <b>清除所有本地临时文件</b>
     * <p>
     * 该方法SDK不会使用，开发者可酌情实现
     * </p>
     *
     * @since 1.0
     *
     */
    @Override
    public void emptyFiles() {
        File rootFile = FileUtil.getSDRootFile();
        if (null != rootFile) {
            File dir = new File(rootFile + saveRootPath);
            if (dir.exists() && dir.isDirectory()) {
                File files[] = dir.listFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }

    /**
     *
     * <b>读取文件</b>
     * <p>
     * 使用给定filePath获取文件读取流
     * </p>
     *
     * @since 1.0
     *
     * @param filePath 文件本地路径
     * @return 文件读取流
     */
    @Override
    public InputStream getFileInputStream(String filePath) {
        InputStream in = null;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    in = new FileInputStream(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return in;
    }

    /**
     * <b>获得文件真实本地路径</b>
     * 
     * @param filePath 文件本地资源管理器路径
     * @return
     */
    @Override
    public String getRealFilePath(String filePath) {
        return filePath;
    }

    /**
     * <b>获取临时文件使用容量</b>
     * 
     * @since 1.1
     * @return 总容量，单位byte
     */
    @Override
    public long getTotalSize() {
        long size = 0;
        File rootFile = FileUtil.getSDRootFile();
        if (null != rootFile) {
            File dir = new File(rootFile + saveRootPath);
            size += getFolderSize(dir);
        }
        return size;
    }

    private long getFolderSize(File dir) {
        long size = 0;
        if (dir.exists() && dir.isDirectory()) {
            try {
                File[] fileList = dir.listFiles();
                for (File file : fileList) {
                    if (file.isFile()) {
                        size += file.length();
                    } else if (file.isDirectory()) {
                        size += getFolderSize(file);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return size;
    }
}
