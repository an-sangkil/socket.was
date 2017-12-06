package com.test.was.sample.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 헤더 파서  개행처리하여 헤더를 파싱한다.
 * @author skan
 *
 */
public class HeaderParse {
	public static Map<String, String> parseHTTPHeaders(InputStream inputStream) throws IOException {
		int charRead;
		StringBuffer sb = new StringBuffer();
		while (true) {
			sb.append((char) (charRead = inputStream.read()));
			if ((char) charRead == '\r') { 							// if we've got a '\r'
				sb.append((char) inputStream.read()); 				// then write '\n'
				charRead = inputStream.read(); 						// read the next char;
				if (charRead == '\r') { 							// if it's another '\r'
					sb.append((char) inputStream.read());			// write the '\n'
					break;
				} else {
					sb.append((char) charRead);
				}
			}
		}

		String[] headersArray = sb.toString().split("\r\n");
		Map<String, String> headers = new HashMap<>();
		if(headersArray != null) {
			String firstStr = headersArray[0];
			String[] firstStrAr = firstStr.split(" ");
			headers.put("method", firstStrAr[0]);
			headers.put("fileName", firstStrAr[1]);
			headers.put("version", firstStrAr[2]);
		}
		for (int i = 1; i < headersArray.length - 1; i++) {
			headers.put(headersArray[i].split(": ")[0], headersArray[i].split(": ")[1]);
		}

		return headers;
	}
}
