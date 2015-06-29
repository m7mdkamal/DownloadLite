package mak.downloadlite;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileInfo {
	
	String fileURL;
	String saveDir;
	int contentLength;
	String fileName;
	String contentType;
	
	public FileInfo(String url, String dir) {
		this.fileURL = url;
		this.saveDir = dir;		
	}

	public String getFileName() {
		return fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public int getContentLength() {
		return contentLength;
	}

	public String getSaveDir() {
		return saveDir;
	}

	public String getFileURL() {
		return fileURL;
	}

	public boolean info() {
		URL url;
		HttpURLConnection httpConn = null;
		int responseCode = 0;
		try {
			url = new URL(fileURL);
			httpConn = (HttpURLConnection) url.openConnection();
			responseCode = httpConn.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}

		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10,
							disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
						fileURL.length());
			}
			// save name url size into file then return it to here

			this.contentType = contentType;
			this.contentLength = contentLength;
			this.fileName = fileName;
			httpConn.disconnect();
			return true;
		} else {
			System.out
					.println("No file to download. Server replied HTTP code: "
							+ responseCode);
			httpConn.disconnect();
			return false;
		}
	}
}