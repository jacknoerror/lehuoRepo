package com.lehuo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.lehuozu.util.JackUtils;

import android.content.Context;

public class ChangeNameIO {
	/*public static void main(String[] args) throws IOException {
//		readFile();
//		changeName();
//		getNames();
	}*/

	/**
	 * @param c
	 * @return
	 */
	public static String readFromSomeWhere(Context c){
		
		
		return JackUtils.readFromFile(c, "fromSomewhere.xml");
	}
	public static String readFile(final String filepath){
//		final String filePath = "C:/Users/taotao/Desktop/log.txt";
		try {
            String encoding="UTF-8";
            File file=new File(filepath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = "";
                StringBuffer lineTxt = new StringBuffer();
                String line = "";
                while((line = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
                    lineTxt.append(line);
                }
                read.close();
                return lineTxt.toString();
    }else{
        System.out.println("找不到指定的文件");
    }
    } catch (Exception e) {
        System.out.println("读取文件内容出错");
        e.printStackTrace();
    }
		return filepath;
		
	}
	private static void getNames() {
		final String dirPath = "C:/Users/taotao/Desktop/temp/person";
		File dir = new File(dirPath);
		  if(!dir.exists())
		  {
//			  oldFile.createNewFile();
			  print("no file");
			  return ;
		  }
		
		  File[] files = dir.listFiles();
		  for(File file : files){
			 String name = file.getName();
			 if(!name.startsWith("category")){//规则
				 
				 System.out.println("R.id."+file.getName().replace(".png", ","));//呈现
			 }
		  }
	}

	private static void changeName() throws IOException {

		final String dirPath = "C:/Users/taotao/Desktop/pl_svc";//路径
		 
		  File dir = new File(dirPath);
		  if(!dir.exists())
		  {
//			  oldFile.createNewFile();
			  print("no file");
			  return ;
		  }
		  print ("文件夹存在："+dir.getName());
		  
		  File[] files = dir.listFiles();//遍历获得文件夹所有文件
		  for(File file : files){
			  System.out.println("修改前："+file.getName());
			  Scanner scan = new Scanner(System.in); //等待控制台输入指令
			  if(scan.nextInt()<1) continue;
			  String newFilename = file.getName().replace("websvc", "mobile");//经过自定义规则处理后的文件名
			  File newFile = new File(dirPath + File.separator + newFilename);
			  boolean result = file.renameTo(newFile);
			  print((result?"成功改名":"失败改名")+":"+newFilename);
		  }
		  /*
		   * 
		   */
		  /*String rootPath = oldFile.getParent();
		  System.out.println("根路径是："+rootPath);
		  File newFile = new File(rootPath + File.separator + "PMSTmp");
		  System.out.println("修改后文件名称是："+newFile.getName());
		  if (oldFile.renameTo(newFile)) 
		  {
			  print("修改成功!");
		  } 
		  else 
		  {
			  print("修改失败");
		  }*/
	}
	
	private static void print(String str){
		System.out.println(str);
	}
}
