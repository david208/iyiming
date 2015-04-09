package com.scnet.iyiming.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;



import com.google.common.base.Splitter;
import com.google.common.io.ByteStreams;

public class FileUtil {

	private static final String rootPath = new PropertiesLoader("application.properties").getProperty("upload.root.path");

	/**
	 * 构建文件上传的路径
	 * 
	 * @author SM
	 * @param
	 * 
	 * @return 上传的路径
	 */
	public static String builderUploadPath(String type, Long id) {
		// 从配置文件里取得文件上传的根路径:如,d:\\upload
		// 这里是组成文件上传的目录,组合方式:类型/ID/文件名
		return rootPath + "/" + type + "/" + id + "/";
	}

	/**
	 * 上传文件
	 * 
	 * @author SM
	 * @param byte[] 需要上传文件的比特
	 * @param uploadPath
	 *            上传路径
	 * @param uploadFileName
	 *            上传的文件名(即重命名的文件名)
	 * @return 是否上传成功
	 * @throws IOException
	 */
	public static String upload(String uploadPath, String fileName, InputStream inputStream) throws IOException {
		String allPath = uploadPath + fileName;
		File file = new File(allPath);// 构建上传文件
		mkDir(uploadPath);// 判断目录是否存在,创建一个目录，并修改目录权限为777
		InputStream bufferInputStream = new BufferedInputStream(inputStream);
		OutputStream bufferOutputStream = new BufferedOutputStream(new FileOutputStream(file));
		try {
			ByteStreams.copy(bufferInputStream, bufferOutputStream);
		} finally {
			if (null != bufferInputStream) {
				bufferInputStream.close();
			}
			if (null != bufferOutputStream) {
				bufferOutputStream.close();
			}
		}
		return allPath;
	}

	/**
	 * 根据目录名创建目录，目录权限为777
	 */
	public static void mkDir(String pathName) {
		File file = new File(pathName);
		if (file.exists()) {
			return;
		}
		if (file.mkdirs()) {
			file.setExecutable(true, false);
			file.setReadable(true, false);
			file.setWritable(true, false);
			return;
		}
		/*
		 * File canonFile = null; try { canonFile = file.getCanonicalFile(); }
		 * catch (IOException e) { return; } File parent =
		 * canonFile.getParentFile(); mkDir(parent.getPath());
		 * mkDir(canonFile.getPath());
		 */
	}

	/**
	 * 构建新的文件名,文件名生成规则,UUID+原文件名后缀。
	 * 
	 * @author SM
	 * @param fileName
	 *            原文件名
	 * @return 新生成的文件名
	 */
	public static String builderNewFileName(String fileName) {
		return Identities.uuid() + "." + getExtension(fileName);
	}

	/**
	 * 返回文件的后缀名
	 * 
	 * @author SM
	 * @param fileName
	 *            文件名
	 * @return 后缀名
	 */
	public static String getExtension(String fileName) {

		Iterator<String> iterable = Splitter.on('.').split(fileName).iterator();
		String postfix = "";
		while (iterable.hasNext()) {
			postfix = iterable.next().trim();
		}
		return postfix;

	}

	/**
	 * 下载文件
	 * 
	 * @author SM
	 * @param downloadPath
	 *            下载的地址
	 * @return 文件流
	 */
	public static byte[] download(String downloadPath) throws IOException {
		File file = new File(downloadPath);// 构建下载文件
		InputStream bufferInputStream = new BufferedInputStream(new FileInputStream(file));
		try {
			return ByteStreams.toByteArray(bufferInputStream);
		} finally {
			if (null != bufferInputStream) {
				bufferInputStream.close();
			}

		}
	}
	
	public static byte[] downloadNio(String downloadPath) throws IOException{
		RandomAccessFile file = new RandomAccessFile(downloadPath, "rw");// 构建下载文件
		FileChannel fileChannel = file.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(48);
		int bytesRead = fileChannel.read(buffer);
		while(bytesRead != -1){
			buffer.flip();
		}
		return null;
	}

}
