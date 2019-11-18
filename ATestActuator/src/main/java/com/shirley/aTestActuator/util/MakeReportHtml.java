package com.shirley.aTestActuator.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年11月1日 下午4:07:59
 */
public class MakeReportHtml {

	public static boolean MakeJsonFile(String dirName, String fileName, String json) {
		boolean flag = false;
		FileOutputStream fileoutputstream = null;
		try {
			String fileame = fileName + ".json";
			fileame = dirName + "//" + fileame;// 生成的html文件保存路径。
			File sourceFile = new File(fileame);
			File fileParent = sourceFile.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
			byte tag_bytes[] = json.getBytes();
			fileoutputstream.write(tag_bytes);
			fileoutputstream.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			try {
				if (null != fileoutputstream)
					fileoutputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public static boolean copyFileScript(String source, String dest) {
		File destPath = new File(dest);
		if (destPath.exists()) {
			return true;
		}
		
		boolean flag = false;
		InputStreamReader isRead=null;
		BufferedReader br =null;
		BufferedWriter bw=null;
		String content="";
		
		try {
			PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = patternResolver.getResources("classpath:" + source);
			
			if (resources != null && resources.length > 0) {
				isRead = new InputStreamReader(resources[0].getInputStream());
				br = new BufferedReader(isRead);
				StringBuffer s = new StringBuffer();
				while ((content=br.readLine()) != null) {
					s.append(content+"\n");
				}
				content = s.toString();
				br.close();
				isRead.close();
		
				if (null != content && !"".equals(content)) {
					destPath.createNewFile();
					FileWriter writer = new FileWriter(destPath);
					bw = new BufferedWriter(writer);
					bw.write(content);
					bw.flush();
					bw.close();
					flag = true;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// 关闭流
			try {
				if (null != br)
					br.close();
				if (null != isRead)
					isRead.close();
				if (null != bw)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

	public static boolean MakeHtml(String dirName, String fileName) {
		FileOutputStream fileoutputstream = null;
		FileInputStream fileinputstream = null;
		boolean flag = false;
		try {
			String title = fileName;
			String templateContent = "";

			PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = patternResolver.getResources("classpath:report/report.html");
			if (resources != null && resources.length > 0) {
				InputStreamReader isRead = new InputStreamReader(resources[0].getInputStream());
				BufferedReader br = new BufferedReader(isRead);
				StringBuffer s = new StringBuffer();
				while ((templateContent=br.readLine()) != null) {
					s.append(templateContent+"\n");
				}
				br.close();
				isRead.close();
				templateContent = s.toString();
				// 把模板页面上的 {{taskName}} 替换成 title 里的内容
				templateContent = templateContent.replaceAll("\\{\\{taskName\\}\\}", title);
				String fileame = fileName + ".html";
				fileame = dirName + "//" + fileame;// 生成的html文件保存路径。
				File sourceFile = new File(fileame);
				File fileParent = sourceFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
				byte tag_bytes[] = templateContent.getBytes();
				fileoutputstream.write(tag_bytes);
				fileoutputstream.close();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			try {
				if (null != fileinputstream)
					fileinputstream.close();
				if (null != fileoutputstream)
					fileoutputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public static boolean fileToZip(String sourceFilePath, String zipFilePath, String zipFileName) {
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		boolean flag = false;

		if (sourceFile.exists()) {
			try {
				File zipFile = new File(zipFilePath + "//" + zipFileName + ".zip");
				File[] sourceFiles = sourceFile.listFiles();
				if (null != sourceFiles && sourceFiles.length > 0) {
					fos = new FileOutputStream(zipFile);
					zos = new ZipOutputStream(new BufferedOutputStream(fos));
					byte[] bufs = new byte[1024 * 10];
					for (int i = 0; i < sourceFiles.length; i++) {
						// 创建ZIP实体，并添加进压缩包
						ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
						zos.putNextEntry(zipEntry);
						// 读取待压缩的文件并写进压缩包里
						fis = new FileInputStream(sourceFiles[i]);
						bis = new BufferedInputStream(fis, 1024 * 10);
						int read = 0;
						while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
							zos.write(bufs, 0, read);
						}
						bis.close();
						fis.close();
					}
					zos.close();
					flag = true;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 关闭流
				try {
					if (null != bis)
						bis.close();
					if (null != zos)
						zos.close();
					if (null != fis)
						fis.close();
					if (null != fos)
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public static boolean delFile(File file) {
		if (!file.exists()) {
			return false;
		}

		if (file.isFile()) {
			return file.delete();
		} else {
			File[] files = file.listFiles();
			for (File f : files) {
				delFile(f);
			}
			return file.delete();
		}
	}
}
