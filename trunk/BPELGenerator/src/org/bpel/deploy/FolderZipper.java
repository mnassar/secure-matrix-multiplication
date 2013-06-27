package org.bpel.deploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
 
public class FolderZipper 
{

	
    List<String> fileList;
    private String output_ZIP_Path;
    public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public String getOutput_ZIP_Path() {
		return output_ZIP_Path;
	}

	public void setOutput_ZIP_Path(String output_ZIP_Path) {
		this.output_ZIP_Path = output_ZIP_Path;
	}

	public String getSource_Folder() {
		return source_Folder;
	}

	public void setSource_Folder(String source_Folder) {
		this.source_Folder = source_Folder;
	}

	private String source_Folder ;
 
    public FolderZipper(String folderPath, String zipPath){
    	fileList = new ArrayList<String>();
    	source_Folder = new String(folderPath);
    	output_ZIP_Path = new String(zipPath);
    }
 
    public static void main( String[] args )
    {
    	FolderZipper appZip = new FolderZipper("/home/farida/workspace2/WF_Process/bpelContent","/home/farida/Documents/ZippedProcess.zip");
    	appZip.generateFileList(new File(appZip.getSource_Folder()));
    	appZip.zipIt(appZip.getOutput_ZIP_Path());
    }
 
    
    public void zipIt(){
    	this.zipIt(output_ZIP_Path);
    }
    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile){
 
     byte[] buffer = new byte[1024];
 
     try{
 
    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);
 
    	System.out.println("Output to Zip : " + zipFile);
 
    	for(String file : this.fileList){
 
    		System.out.println("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
 
        	FileInputStream in = 
                       new FileInputStream(source_Folder + File.separator + file);
 
        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}
 
        	in.close();
    	}
 
    	zos.closeEntry();
    	//remember close it
    	zos.close();
 
    	System.out.println("Done");
    }catch(IOException ex){
       ex.printStackTrace();   
    }
   }
 
    /**
     * Traverse a directory and get all files,
     * and add the file into fileList  
     * @param node file or directory
     */
    public void generateFileList(File node){
 
    	//add file only
	if(node.isFile()){
		fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
	}
 
	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename));
		}
	}
 
    }
 
    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
    	return file.substring(source_Folder.length()+1, file.length());
    }
}
