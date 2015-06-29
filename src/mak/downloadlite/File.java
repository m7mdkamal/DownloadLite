package mak.downloadlite;

import mak.downloadlite.FileInfo;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class File {

	private StringProperty name;
	private StringProperty url;
	private StringProperty status;
	private IntegerProperty size;
	private IntegerProperty downloaded;
	private StringProperty dir;
	private StringProperty progress;
	public Thread thread;
	private Download down;

	public File(String url, String d) {
		FileInfo info = new FileInfo(url, d);
		if (info.info()) {
			this.url = new SimpleStringProperty(info.getFileURL());
			this.dir = new SimpleStringProperty(info.getSaveDir());
			this.name = new SimpleStringProperty(info.getFileName());
			this.size = new SimpleIntegerProperty(info.getContentLength());
			this.downloaded = new SimpleIntegerProperty(0);
			this.progress = new SimpleStringProperty(
					(this.getDownloaded() / this.getSize()) + "");
			this.status = new SimpleStringProperty("DOWNLOADING");
			startDownload();
		} else {
			this.url = new SimpleStringProperty(info.getFileURL());
			this.status = new SimpleStringProperty("Error");
		}
	}

	public void startDownload() {
		down = new Download(url.get(), dir.get(), this);
		thread = new Thread(down);
		thread.start();
	}

	public void resumeDownload() {
		this.down.run = true;
		synchronized (down) {
			down.notify();
		}
	}

	public void pauseDownload() {
		this.down.run = false;
	}

	public void stopDownload() {
		resumeDownload();
		this.down.stop = true;
	}

	public String getName() {
		return name.get();
	}

	public String getUrl() {
		return url.get();
	}

	public String getStatus() {
		return status.get();
	}

	public int getSize() {
		return size.get();
	}

	public int getDownloaded() {
		return downloaded.get();
	}

	public String getDir() {
		return this.dir.get();
	}

	public StringProperty getProgressProperty() {
		return progress;
	}

	public void setName(String str) {
		this.name.set(str);
	}

	public void setDownloaded(int i) {
		this.downloaded.set(i);
	}

	public void setUrl(String str) {
		this.url.set(str);
	}

	public void setStatus(String str) {
		this.status.set(str);
	}

	public void setSize(int i) {
		this.size.set(i);
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public StringProperty urlProperty() {
		return this.url;
	}

	public IntegerProperty sizeProperty() {
		return size;
	}

	public StringProperty sizeMegaProperty() {
		double size = getSize();
		int unit = 0;
		while (size >= 1024) {
			size /= 1024;
			unit++;
		}
		switch (unit) {
		case 0:
			return new SimpleStringProperty(String.format("%.2f", size)
					+ " Bytes");
		case 1:
			return new SimpleStringProperty(String.format("%.2f", size) + " KB");
		case 2:
			return new SimpleStringProperty(String.format("%.2f", size) + " MB");
		case 3:
			return new SimpleStringProperty(String.format("%.2f", size) + " GB");
		default:
			return new SimpleStringProperty(String.format("%.2f", size) + " TB");

		}
	}

	public StringProperty downloadMegaProperty() {
		double size = getDownloaded();
		int unit = 0;
		while (size >= 1024) {
			size /= 1024;
			unit++;
		}
		switch (unit) {
		case 0:
			return new SimpleStringProperty(String.format("%.2f", size)
					+ " Bytes");
		case 1:
			return new SimpleStringProperty(String.format("%.2f", size) + " KB");
		case 2:
			return new SimpleStringProperty(String.format("%.2f", size) + " MB");
		case 3:
			return new SimpleStringProperty(String.format("%.2f", size) + " GB");
		default:
			return new SimpleStringProperty(String.format("%.2f", size) + " TB");

		}
	}

	public StringProperty statusProperty() {
		return status;
	}

	public void setProgressProperty() {
		this.progress
				.set(String.format("%.2f",
						((double) this.getDownloaded() / (double) this
								.getSize()) * 100)
						+ "");
	}

	public String getProgress() {
		return this.progress.get();
	}

	public StringProperty dirProperty() {
		return dir;
	}
}
