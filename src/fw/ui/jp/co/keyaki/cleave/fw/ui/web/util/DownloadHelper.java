package jp.co.keyaki.cleave.fw.ui.web.util;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class DownloadHelper {

	public static final String HEADER_USER_AGENT = "User-Agent";

	public static final String AGENT_MSIE = "MSIE";

	public static final String AGENT_FIREFOX = "FIREFORX";

	public static final String AGENT_CHROME = "CHROME";

	public static final String AGENT_SAFARI = "SAFARI";

	public static final String PERIOD = ".";

	public static final String DEFAULT_FILE_EXTENSION = "bin";

	public static final String DEFAULT_MIME_TYPE = "application/octet-stream";

	public DownloadHelper() {
	}

	public HttpServletResponse wrap(HttpServletRequest request,
			HttpServletResponse response, String responseFileName) {
		return new FileDownLoadResponse(request, response, responseFileName);
	}

	protected static String getFilenameExtension(String fileName) {
		int hit = fileName.lastIndexOf(PERIOD);
		if (hit < 0 || (fileName.length() == (hit + 1))) {
			return DEFAULT_FILE_EXTENSION;
		}
		return fileName.substring(hit + 1).toLowerCase();
	}

	protected static String getMimeType(ServletContext context,
			String fileExtension) {
		String mimeType = context.getMimeType(fileExtension);
		if (mimeType == null) {
			return DEFAULT_MIME_TYPE;
		}
		return mimeType;
	}

	protected static String getResponseFileName(HttpServletRequest request,
			String fileName) {

		String responseFileName = fileName;
		String agent = request.getHeader(HEADER_USER_AGENT).toUpperCase();

		if (agent.indexOf(AGENT_MSIE) > -1) {
			// responseFileName = URLEncoder.encode(fileName, "UTF-8");
		} else if (agent.indexOf(AGENT_FIREFOX) > -1
				|| agent.indexOf(AGENT_CHROME) > -1) {
			// responseFileName = MimeUtility.encodeWord(fileName,
			// "ISO-2022-JP", "B");
		} else if (agent.indexOf(AGENT_SAFARI) > -1) {
		} else {

		}
		return responseFileName;
	}

	public class FileDownLoadResponse extends HttpServletResponseWrapper {

		private HttpServletRequest request;

		private String responseFileName;

		public FileDownLoadResponse(HttpServletRequest request,
				HttpServletResponse response, String responseFileName) {
			super(response);
			this.request = request;
			this.responseFileName = responseFileName;

		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return new FileDownLoadServletOutputStream(super.getOutputStream(), this);
		}

		public HttpServletRequest getRequest() {
			return request;
		}

		public String getResponseFileName() {
			return responseFileName;
		}

	}

	public class FileDownLoadServletOutputStream extends ServletOutputStream {

		/** VIEW����MIME��Content-Disposition */
		public static final String CONTENT_DISPOSITION_VIEW = "inline";
		/** DOWNLOAD����MIME��Content-Disposition */
		public static final String CONTENT_DISPOSITION_DOWN = "attachment";

		private FileDownLoadResponse response;

		private ServletOutputStream original;

		private boolean isHeaderOutput = false;

		public FileDownLoadServletOutputStream(ServletOutputStream original,
				FileDownLoadResponse response) {
			this.original = original;
			this.response = response;
		}

		@Override
		public void write(int paramInt) throws IOException {
			if (!isHeaderOutput) {
				isHeaderOutput = true;
				setDownloadSetting();

			}
			original.write(paramInt);
		}

		private void setDownloadSetting() {
			HttpServletRequest request = response.getRequest();
			String dispositionFilename = DownloadHelper.getResponseFileName(request, response.getResponseFileName());
			String filenameExtension = DownloadHelper.getFilenameExtension(response.getResponseFileName());
			String mimeType = DownloadHelper.getMimeType(request.getSession().getServletContext(), filenameExtension);
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", CONTENT_DISPOSITION_DOWN + "; filename=\"" + dispositionFilename + "\"");
		}

		public void flush() throws IOException {
			original.flush();
		}

		public void close() throws IOException {
			original.close();
		}

	}
}
