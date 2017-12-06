package com.test.was.sample.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.was.sample.model.DetailInformation;
import com.test.was.sample.model.ErrorMessage;
import com.test.was.sample.utils.ContextErrorMessage;

/**
 * 
 * @author skan
 *
 */
public class Response {
	
	private Logger logger = LoggerFactory.getLogger(ContextErrorMessage.class);
	
	private static List<ErrorMessage> errorMessageList = null;

	static {
		ContextErrorMessage contextErrorMessage = new ContextErrorMessage();
		errorMessageList = contextErrorMessage.contextErrorMessage();
	}

	private OutputStream raw;
	private Writer out;

	public OutputStream getRaw() {
		return raw;
	}

	public Writer getOut() {
		return out;
	}

	public Response(OutputStream raw, Writer out) {
		this.raw = raw;
		this.out = out;
	}

	public Response() {}

	public void createHeader(String statusCode) throws Exception {
		this.createHeader(statusCode, null);
	}
	
	public void createHeader(String statusCode, String rootDir) throws Exception {
		this.createHeader(statusCode, rootDir, null, 0, null);
	}
	
	public void createHeader(String statusCode, String rootDir, String contentType ,int bodyLength, String body) throws Exception {

		if (errorMessageList == null) {
			
			throw new Exception("헤더 생성중 에러가 발생하였습니다.");
		}

		ErrorMessage responseMessage = this.search(rootDir);
		if(responseMessage != null ){
			DetailInformation detailInformation = this.search(responseMessage.getDetail(), statusCode);
			if(StringUtils.isEmpty(body)) {
				body = detailInformation.getMessage();
			}
		}

		switch (statusCode) {
		case "200" :
			
			sendHeader(out, "HTTP/1.0 200 OK", contentType, bodyLength);
			break;
		case "403":
			
			sendHeader(out, "HTTP/1.0 403 Forbidden", "text/html; charset=utf-8", body.length());
			out.write(body);
			out.flush();

			break;
		case "404":

			sendHeader(out, "HTTP/1.0 404 File Not Found", "text/html; charset=utf-8", body.length());
			out.write(body);
			out.flush();

			break;
		case "500":

			sendHeader(out, "HTTP/1.0 500 Internal Server Error", "text/html; charset=utf-8", body.length());
			out.write(body);
			out.flush();

			break;
		case "501":

			// if (version.startsWith("HTTP/")) { // send a MIME header
			sendHeader(out, "HTTP/1.0 501 Not Implemented", "text/html; charset=utf-8", body.length());
			// }
			out.write(body);
			out.flush();

			break;
		default:
			break;
		}
	}
	
	
	public void createBody(String contentType, String body) throws Exception {
		this.createHeader("200", null, contentType, body.length(), null);
		raw.write(body.getBytes());
		raw.flush();
	}
	
	public void createBody(String contentType, File theFile, String root) throws Exception {
		byte[] theData = Files.readAllBytes(theFile.toPath());
		//if (version.startsWith("HTTP/")) { 
		this.createHeader("200", root, contentType, theData.length, null);
		//}

		raw.write(theData);
		raw.flush();
	}
	
	
	
	

	public List<ErrorMessage> searchFilter(String rootDir) {
		return errorMessageList.stream().filter(p -> p.getRootDir().equals(rootDir)).collect(Collectors.toList());
	}

	public ErrorMessage search(String rootDir) {
		for (ErrorMessage errorMessage : errorMessageList) {
			if (StringUtils.defaultString(errorMessage.getRootDir()).equals(rootDir)) {
				return errorMessage;
			}
		}
		return null;
	}

	public DetailInformation search(DetailInformation[] detailInformationAr, String statusCode) {
		for (DetailInformation detailInformation : detailInformationAr) {
			if (detailInformation.getErrorCode().equals(statusCode)) {
				return detailInformation;
			}
		}
		return null;
	}
	
	/**
	 * 헤더정보 
	 * @param out
	 * @param responseCode
	 * @param contentType
	 * @param length
	 * @throws IOException
	 */
	private void sendHeader(Writer out, String responseCode, String contentType, int length) throws IOException {
		out.write(responseCode + "\r\n");
		Date now = new Date();
		out.write("Date: " + now + "\r\n");
		out.write("Server: JHTTP 2.0\r\n");
		out.write("Content-length: " + length + "\r\n");
		out.write("Content-type: " + contentType + "\r\n\r\n");
		out.flush();
	}
}
