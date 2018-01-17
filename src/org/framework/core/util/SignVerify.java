package org.framework.core.util;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.NetworkInterface;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import nhjkpt.system.util.CommonUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SignVerify {
    public static final String KEY_ALGORITHM = "RSA";  
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final int MAX_DECRYPT_BLOCK = 128;
	/**
	 * 对签名文件进行时间校验
	 * @param f
	 * @return
	 */
	public static Boolean verifyCertificate(String f){
		try{
	        Certificate certificate = getCertificate(f);   
	        X509Certificate x509Certificate = (X509Certificate)certificate;   
            x509Certificate.checkValidity(new Date());
            return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
    private static Certificate getCertificate(String f)   
            throws Exception {   
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");   
        FileInputStream in = new FileInputStream(f);   
        Certificate certificate = certificateFactory.generateCertificate(in);   
        in.close();   
        return certificate;   
    }
    public static String getPublicKey(String certificatePath) throws Exception {   
        Certificate certificate = getCertificate(certificatePath);   
        PublicKey key = certificate.getPublicKey();   
        return encryptBASE64(key.getEncoded());   
    }
    public static String encryptBASE64(byte[] key) throws Exception {  
        return (new BASE64Encoder()).encodeBuffer(key);  
    }
	public static byte[] decryptByFile(byte[] data, String f)  
            throws Exception {
		return decrypt(data,getPublicKey(f));
	}
	/**
	 * 使用公钥进行解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
    public static byte[] decrypt(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);  
  
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    /**
     * base64解码
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {  
        return (new BASE64Decoder()).decodeBuffer(key);  
    }
    /**
     * 数字签名验签
     * @param data
     * @param sign
     * @param f
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String sign,String f) throws Exception {  
    	try{
    		// 获得证书   
        	X509Certificate x509Certificate=null;
        	try{
    			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");   
    	        FileInputStream in = new FileInputStream(f);   
    	        Certificate certificate = certificateFactory.generateCertificate(in);   
    	        in.close();
    	        x509Certificate = (X509Certificate)certificate;   
                x509Certificate.checkValidity(new Date());
    		}catch(Exception e){
    			return false;
    		}
            // 获得公钥   
            PublicKey publicKey = x509Certificate.getPublicKey();   
            // 构建签名   
            Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());   
            signature.initVerify(publicKey);   
            signature.update(data);   
            return signature.verify(decryptBASE64(sign));
    	}catch(Exception e){
    		return false;
    	}
    } 
    /**
     * 读取加密内容
     * @param fileName
     * @return
     */
	public static Object readFile(String fileName){
		FileInputStream fis = null;
		ObjectInputStream oos=null;
		try {
			fis = new FileInputStream(fileName);
			oos = new ObjectInputStream(fis);
			Object obj=oos.readObject();
	        return obj;
		} catch (Exception e) {
			System.err.println("文件读取错误");
			e.printStackTrace();
			return null;
		}finally{
			try {
				oos.close();
				fis.close();
			} catch (IOException e) {
			}
		}
	}
    /**
     * 将文本转成字节
     * @param strIn
     * @return
     * @throws Exception
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception{
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2)
        {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
    /**
     * Jdk6获取本地mac地址
     * @return
     */
    public static List<String> getMacAddress(){
    	List<String> list=new ArrayList<String>();
    	try {
			Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
			while (el.hasMoreElements()) {
				byte[] mac = el.nextElement().getHardwareAddress();
				if (mac == null || mac.length == 0) {
					continue;
				}
				StringBuffer sb = new StringBuffer();
				for (byte b : mac) {
					sb.append(hexByte(b));
					sb.append("-");
				}
				sb.deleteCharAt(sb.length() - 1);
				list.add(sb.toString());
			}
		} catch (Exception e) {
		}
    	return list;
    }
    /**
     * mac地址字节转字符串
     * @param b
     * @return
     */
	private static String hexByte(byte b) {
		String s = Integer.toHexString(b);
		int len = s.length();
		for (int i = len; i < 8; i++) {
			s = "0" + s;
		}
		return s.substring(6).toUpperCase();
	}
    /**
     * 获取本机cpu序列号
     * @return
     */
	public static String getCPUSerial() {
		String result = "";
		try {
			File file = File.createTempFile("tmp", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n"
					+ "   (\"Select * from Win32_Processor\") \n"
					+ "For Each objItem in colItems \n"
					+ "    Wscript.Echo objItem.ProcessorId \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
			file.delete();
		} catch (Exception e) {
		}
		if (result.trim().length() < 1 || result == null) {
			result = "无CPUID被读取";
		}
		return result.trim();
	}
	public static String getCpuSN() {
        BufferedReader bufferedReader = null;  
        Process process = null;  
        try {  
        	String[] cmd = {"/bin/sh", "-c", "dmidecode -t processor | grep 'ID'"}; 
        	process = Runtime.getRuntime().exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(  
                    process.getInputStream()));  
            String line = null;  
            while ((line = bufferedReader.readLine()) != null) { 
            	line=line.trim();
            	if(line.startsWith("ID:")){
            		return line.substring(3).replace(" ", "");
            	}
            }  
        } catch (IOException e) {  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException e1) {  
            }  
            bufferedReader = null;  
            process = null;  
        }  
        return null;
	}
	/**
	 * 获取主板id
	 * @return
	 */
	public static String getBoardSN() {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n"
					+ "   (\"Select * from Win32_BaseBoard\") \n"
					+ "For Each objItem in colItems \n"
					+ "    Wscript.Echo objItem.SerialNumber \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
		}
		return result.trim();
	}
	public static String getBaseboardSN() {
        BufferedReader bufferedReader = null;  
        Process process = null;  
        try {  
        	String[] cmd = {"/bin/sh", "-c", "dmidecode -t baseboard |grep 'Serial Number'"}; 
        	process = Runtime.getRuntime().exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(  
                    process.getInputStream()));  
            String line = null;  
            while ((line = bufferedReader.readLine()) != null) { 
            	line=line.trim();
            	if(line.startsWith("Serial Number:")){
            		return line.substring(14).replace(" ", "").replace(".", "");
            	}
            }  
        } catch (IOException e) {  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException e1) {  
            }  
            bufferedReader = null;  
            process = null;  
        }  
        return null;
	}
	/**
	 * 验证服务器注册码是否正确
	 * @param map
	 * @return
	 */
	public static boolean checkServer(Map<String,Object> map){
		try{
			List<String> list=getMacAddress();
			String os = System.getProperty("os.name").toLowerCase();
			for(String mac:list){
				if(mac.equals(map.get(CommonUtil.MAC_ADDRESS))){
					if(os.equals("linux")){
						if(getCpuSN().equals(map.get(CommonUtil.CPU_ID))){
		        			if(getBaseboardSN().equals(map.get(CommonUtil.BOARD_ID))){
		        				return true;
		        			}
		        		}
					}else{
						if(getCPUSerial().equals(map.get(CommonUtil.CPU_ID))){
		        			if(getBoardSN().equals(map.get(CommonUtil.BOARD_ID))){
		        				return true;
		        			}
		        		}
					}
				}
			}
		}catch(Exception e){
		}
		return false;
	}
	/**
	 * 验证服务器
	 * @param lic
	 * @param cer
	 * @return
	 */
	public static boolean checkServer(String lic,String cer){
		try{
    		Map<String,Object> map=mapLicense(lic,cer);
        	return checkServer(map);
    	}catch(Exception e){
    	}
    	return false;
	}
    /**
     * 验证mac地址
     * @return
     */
    public static boolean checkMacAddress(String lic,String cer){
    	try{
    		Map<String,Object> map=mapLicense(lic,cer);
        	if(getMacAddress().equals(map.get(CommonUtil.MAC_ADDRESS))){
        		return true;
        	}
    	}catch(Exception e){
    	}
    	return false;
    }
    public static boolean checkMacAddress(){
    	try{
    		Map<String,Object> map=mapLicense();
        	if(getMacAddress().equals(map.get(CommonUtil.MAC_ADDRESS))){
        		return true;
        	}
    	}catch(Exception e){
    	}
    	return false;
    }
    /**
     * 解析license
     * @return
     */
    public static Map<String,Object> mapLicense(String lic,String cer){
    	Map<String,Object> map=new HashMap<String, Object>();
    	try {
        	LicenseSign ls=(LicenseSign) SignVerify.readFile(lic);
			String license=new String(decryptByFile(decryptBASE64(ls.getLicense()), cer));
			String msg[]=license.split(CommonUtil.LICENSE_SPLIT);
			String keyVal[]=null;
			for(String str:msg){
				keyVal=str.split(CommonUtil.LICENSE_EQ);
				map.put(keyVal[0], keyVal[1]);
			}
		} catch (Exception e) {
		}
    	return map;
    }
    public static Map<String,Object> mapLicense(){
    	Map<String,Object> map=new HashMap<String, Object>();
    	try {
    		String path=Thread.currentThread().getContextClassLoader().getResource("").getPath();
        	LicenseSign ls=(LicenseSign) SignVerify.readFile(path+"/sign/license.nhjkpt");
			String license=new String(decryptByFile(hexStr2ByteArr(ls.getLicense()), path+"/sign/nhjkpt.cer"));
			String msg[]=license.split(CommonUtil.LICENSE_SPLIT);
			String keyVal[]=null;
			for(String str:msg){
				keyVal=str.split(CommonUtil.LICENSE_EQ);
				map.put(keyVal[0], keyVal[1]);
			}
		} catch (Exception e) {
		}
    	return map;
    }
}
