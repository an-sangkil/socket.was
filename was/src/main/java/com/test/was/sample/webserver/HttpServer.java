package com.test.was.sample.webserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cybaek on 15. 5. 22..
 */
public class HttpServer {

	private static Logger logger = LoggerFactory.getLogger(HttpServer.class);
	private static final int NUM_THREADS = 50;
	private static final String INDEX_FILE = "index.html";
	private final File rootDirectory;
	private final int port;

	public HttpServer(File rootDirectory, int port) throws IOException {
		if (!rootDirectory.isDirectory()) {
			throw new IOException(rootDirectory + " does not exist as a directory");
		}
		this.rootDirectory = rootDirectory;
		this.port = port;
	}

	public void start() throws IOException {
		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
		try (ServerSocket server = new ServerSocket(port)) {
			logger.info("Accepting connections on port = {}" ,  server.getLocalPort());
			logger.info("Document Root = {}" , rootDirectory);
			while (true) {
				try {
					Socket request = server.accept();

					Runnable r = new RequestProcessor(rootDirectory, INDEX_FILE, request);
					pool.submit(r);
				} catch (IOException ex) {
					logger.warn("Error accepting connection = {}", ex);
				}
			}
		}
	}

	public static void main(String[] args) {
		// get the Document root
		File docroot;
		try {
			if(args == null || args.length == 0) {
				String currentDir = System.getProperty("user.dir");
				docroot = new File(currentDir + "/src/main/webapp");
				if(!docroot.exists()) {
					logger.trace("신규 docbase 를 생성하였습니다. = {} " , docroot.getPath());
					docroot.mkdirs();
				}
			} else {
				docroot = new File(args[0]);
			}
			
		} catch (ArrayIndexOutOfBoundsException ex) {
			logger.error("Usage: java JHTTP docroot port");
			return;
		}

		// set the port to listen on
		int port;
		try {
			port = Integer.parseInt(args[1]);
			if (port < 0 || port > 65535)
				port = 80;
		} catch (RuntimeException ex) {
			port = 80;
		}
		try {
			HttpServer webserver = new HttpServer(docroot, port);
			webserver.start();
		} catch (IOException ex) {
			logger.info("Server could not start = {}", ex);
		}
	}
}