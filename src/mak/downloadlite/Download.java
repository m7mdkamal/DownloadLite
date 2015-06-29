package mak.downloadlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Download extends Thread {
	private static final int BUFFER_SIZE = 128;
	String fileURL;
	String saveDir;
	mak.downloadlite.File file;
	public boolean run = true;
	public boolean stop = false;

	public Download(String url, String dir, mak.downloadlite.File file2) {
		this.fileURL = url;
		this.saveDir = dir;
		this.file = file2;
	}

	@Override
	public void run() {

		// data

		try {
			this.download();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void doWait() {
		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public void download() throws IOException {
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
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

			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			InputStream inputStream = httpConn.getInputStream();
			int count = 0;
			File f;
			String saveFilePath;
			do {
				if (count == 0)
					saveFilePath = saveDir + File.separator + fileName;
				else
					saveFilePath = saveDir + File.separator + "(Copy" + count
							+ ")" + fileName;
				f = new File(saveFilePath);
				count++;
			} while (f.exists() && !f.isDirectory());

			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while (!stop && (bytesRead = inputStream.read(buffer)) != -1) {
				synchronized (this) {
					while (!run)
						try {
							wait();
						} catch (InterruptedException e) {
							System.out.println("InterruptedException caught");
						}
					outputStream.write(buffer, 0, bytesRead);
					file.setDownloaded(file.getDownloaded() + bytesRead);
					file.setProgressProperty();
					System.out.println(file.getDownloaded() + "/"
							+ file.getSize() + " " + file.getProgress());
				}
			}
			
			outputStream.close();
			inputStream.close();
			if(stop){
				this.file.setStatus("Canceled");
				System.out.println("File canceled");
			}else{
				this.file.setStatus("Finished");
				System.out.println("File downloaded");
			}
		} else {
			System.out
					.println("No file to download. Server replied HTTP code: "
							+ responseCode);
		}
		httpConn.disconnect();
	}
}