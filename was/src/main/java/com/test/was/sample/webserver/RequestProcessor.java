package com.test.was.sample.webserver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.URLConnection;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.test.was.sample.http.Request;
import com.test.was.sample.http.Response;
import com.test.was.sample.utils.HeaderParse;

public class RequestProcessor implements Runnable {
	private org.slf4j.Logger logger = LoggerFactory.getLogger(RequestProcessor.class.getCanonicalName());
    private File rootDirectory;
    private String indexFileName = "index.html";
    private Socket connection;

    public RequestProcessor(File rootDirectory, String indexFileName, Socket connection) {
        if (rootDirectory.isFile()) {
            throw new IllegalArgumentException("rootDirectory must be a directory, not a file");
        }
        try {
            rootDirectory = rootDirectory.getCanonicalFile();
        } catch (IOException ex) { }
        this.rootDirectory = rootDirectory;
        if (indexFileName != null) {
            this.indexFileName = indexFileName;
        }
        this.connection = connection;
    }

    @Override
    public void run() {
        String root = rootDirectory.getPath();
        logger.trace("root directory = {} " , rootDirectory.getName());
        Response response = null;
        try {
            OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
            Writer out = new OutputStreamWriter(raw);
            response = new Response(raw, out);
            
            Map<String,String> headerMap = HeaderParse.parseHTTPHeaders(connection.getInputStream());
            logger.trace("header Info = {}" , headerMap);

            String method = headerMap.get("method");
            String version = headerMap.get("version");
            
            
            if (method.equals("GET")) {
                String fileName = headerMap.get("fileName");

                if (fileName.endsWith("/")) {
					fileName += indexFileName;
				}
                
                // forbidden 검사 
                boolean forbidden = false;
                if(fileName.lastIndexOf(".") != -1){
                	forbidden = fileName.substring(fileName.lastIndexOf("."), fileName.length()).matches("(?i).*exe.*");
                }
                
                Request request = new Request();
                request.parseUrl(fileName);
                
				// || fileName.matches("(?i).*../.*")
                // TODO 금칙어 폴더에 대한 403 처리 필요
                if(forbidden ) {
                	// 403
                	response.createHeader("403" , rootDirectory.getName());
                	return ;
                }
                
                // 200
                if(fileName.matches(".+\\.(html|jsp|ico|hwp|jpg|zip|tif|alz|bmp|pdf)$")) {
	                String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
	                File theFile = new File(rootDirectory, fileName.substring(1, fileName.length()));
	                if (theFile.canRead() && theFile.getCanonicalPath().startsWith(root)) {
	                    
	                    // SUCCESS
	                		response.createBody(contentType,theFile,root);
	                    
	                    
	                } else {
	                    // 404 파일이 존재 하지 않음
	                	response.createHeader("404" , rootDirectory.getName());
	                }
                } else {
                	request.requestMappling(request.getFileName(), request, response);
                }
                
            } else {
                // 서버에 요청을 수행할 수 있는 기능이 없다. 예를 들어 서버가 요청 메소드를 인식하지 못할 때 이 코드를 표시한다.
            	response.createHeader("501" , rootDirectory.getName());
                
            }
        } catch (IOException ex) {
            logger.error("Error talking to = {}" ,  connection.getRemoteSocketAddress(), ex);
        } catch (Exception e) {
			try {
				// 서버에 에러가 발생한 경우
				logger.error("server error = {}" ,e);
				response.createHeader("500" , rootDirectory.getName());
			} catch (Exception e1) {}

        } finally {
            try {
                connection.close();
            } catch (IOException ex) {
            	ex.getMessage();
            }
        }
    }

}