package com.baidu.imc.impl.im.transaction.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextUtils;

import com.baidu.im.frame.pb.EnumFileType;
import com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReq;
import com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem;
import com.baidu.im.frame.utils.FileUtil;
import com.baidu.im.frame.utils.HexUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.message.BDHI_FILE_TYPE;
import com.baidu.imc.impl.im.message.BDHiFile;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;

public class IMFileGetUploadSignRequest extends IMBaseRequest {

    private static final String TAG = "UploadFileGetSignRequest";

    private String bosHost;
    private String filePath;
    private String fileType;
    private String md5;
    private String bmd5;
    private BDHiFile bdfile;

    public IMFileGetUploadSignRequest(String bosHost, BDHiFile bdFile,String filePath, String fileType) {
        if (TextUtils.isEmpty(bosHost) || TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileType)) {
            throw new InitializationException();
        }
        this.bosHost = bosHost;
        this.filePath = filePath;
        this.fileType = fileType;
        this.bdfile = bdFile;

    }

    @Override
    public BinaryMessage createRequest() {
        File file = new File(filePath);
        LogUtil.printIm(getRequestName(), "FilePath:" + filePath + " fileType:" + fileType);
        if (file.exists() && !TextUtils.isEmpty(fileType)) {
            InputStream fin = null;
            try {
                // Prepare fileInputStream
                fin = new FileInputStream(file);
                // Prepare md5 & bmd5
                MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");
                MessageDigest md5Digest = MessageDigest.getInstance("MD5");

                // Prepare fileLength
                int fileLength = 0;

                byte[] buffer = new byte[50 * 1024];
                int readBytes;
                while ((readBytes = fin.read(buffer)) >= 0) {
                    if (readBytes > 0) {
                        fileLength += readBytes;
                        sha256Digest.update(buffer, 0, readBytes);
                        md5Digest.update(buffer, 0, readBytes);
                    }
                }
                fin.close();
                fin = null;

                final String sha256 = HexUtil.hex(sha256Digest.digest());
                final String md5 = HexUtil.hex(md5Digest.digest());
                String fileName = file.getName();
                LogUtil.printIm(getRequestName(), "FileName:" + fileName + " fileType:" + fileType + " sha256:"
                        + sha256 + " md5:" + md5 + " fileLength:" + fileLength);

                this.md5 = md5;
                this.bmd5 = sha256;

//                // Rename this file into it's md
//                bdfile.setoldLocaleFilePath(file.getPath());
//
//                if (fileName != null && !fileName.equalsIgnoreCase(md5)) {
//                    File md5File = new File(file.getParent(), md5);
//                    // if md5File already exists then use it directly
//
//                    if (md5File.exists() && md5File.length() != 0) {
//
//                    	/*if (file.delete()) {
//                            LogUtil.w(TAG, "failed to delete old temp file: " + file.getPath());
//                        } else {
//                            LogUtil.printIm("Delete old Temp File: " + file.getPath());
//                        }*/
//                    }else
//                    if (file.renameTo(md5File)) {
//                        // rename ok, continue override file and fileName
//                        file = md5File;
//                        fileName = file.getName();
//                        LogUtil.printIm("Renamed to Temp File: " + file.getPath());
//                    } else {
//                        LogUtil.printIm("Rename to Temp File Failed: " + file.getPath());
//                    }
//
//                }
//
//                bdfile.setMD5(md5);
//                bdfile.setLocaleFilePath(file.getPath());
//                bdfile.setFileName(fileName);

                // Prepare GetUploadSignReqItem
                GetUploadSignReqItem itemBuilder = new GetUploadSignReqItem(); // migrate from builder
                itemBuilder.setFileName(fileName);
                itemBuilder.setFileSize(fileLength);
                itemBuilder.setMd5(md5);
                itemBuilder.setBmd5(sha256);
                itemBuilder.setBossHost(bosHost);

                switch (BDHI_FILE_TYPE.parse(fileType)) {
                    case IMAGE:
                        itemBuilder.setFileType(EnumFileType.FILE_IMAGE);
                        break;
                    case VOICE:
                        itemBuilder.setFileType(EnumFileType.FILE_VOICE);
                        break;
                    default:
                        itemBuilder.setFileType(EnumFileType.FILE_CUSTOM);
                        break;
                }

                // Prepare GetUploadSignReq
                GetUploadSignReq getUploadSignReqBuilder = new GetUploadSignReq(); // migrate from builder
                getUploadSignReqBuilder.addReqList(itemBuilder);
                GetUploadSignReq getUploadSignReq = getUploadSignReqBuilder;

                // Prepare BinaryMessage
                BinaryMessage binaryMessage = new BinaryMessage();
                binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_FILE.getName());
                binaryMessage.setMethodName(MethodNameEnum.GET_UPLOAD_SIGN.getName());
                binaryMessage.setData(getUploadSignReq.toByteArray());
                return binaryMessage;
            } catch (FileNotFoundException e) {
                LogUtil.printImE(getRequestName(), "Can not find the file.", e);
            } catch (NoSuchAlgorithmException e) {
                LogUtil.printImE(getRequestName(), "Can not get md5 or sha256.", e);
            } catch (IOException e) {
                LogUtil.printImE(getRequestName(), "Can not read file.", e);
            } finally {
                if (null != fin) {
                    try {
                        fin.close();
                    } catch (IOException e) {
                        LogUtil.printImE(getRequestName(), "Can not close fileInputStream.", e);
                    }
                }
            }
        } else {
            LogUtil.printIm(getRequestName(), "File is not existed.");
        }
        return null;
    }

    @Override
    public String getRequestName() {
        return TAG;
    }

    public String getMd5() {
        return md5;
    }

    public String getBmd5() {
        return bmd5;
    }
}
