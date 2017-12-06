package com.test.client;

import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.client.code.ClientCode;

/**
 * 
 * @author skan
 *
 */
public class HttpClientConnector {

	private Log logger = LogFactory.getLog(HttpClientConnector.class);

	private static String DEAFULT_URL = "http://127.0.0.1"; // "";

	static {
	}

	public static final String ERROR_403 = DEAFULT_URL + "/index.exe";
	public static final String ERROR_404 = DEAFULT_URL + "/ext/index.html";
	public static final String ERROR_500 = DEAFULT_URL + "/ext/kkk.500";
	
	public static final String HELLO = DEAFULT_URL + "/Hello";
	public static final String SERVICE_HELLO = DEAFULT_URL + "/service.Hello";

	/**
	 * Header 생성 Session Key, Security Key
	 * 
	 * @return
	 * @throws Exception
	 */
	public HttpHeaders createHeader(MediaType mediaType, List<MediaType> acceptableMediaTypes, boolean tokenYN)
			throws Exception {

		HttpHeaders requestHeaders = new HttpHeaders();
		// tokenYN GetToken : 처음 토큰을 생성할때는 False 로 셋팅하여 사용한다.
		if (tokenYN) {
			requestHeaders.set("X-ALLTHAT-API-TOKEN", "API TOKEN OOO");
		}
		// 미디어 컨텐트 타입 삽입 기본 TEXT/HTML
		if (mediaType == null || acceptableMediaTypes == null) {
			requestHeaders.setContentType(MediaType.TEXT_HTML);
		} else {
			if (CollectionUtils.isNotEmpty(acceptableMediaTypes)) {
				requestHeaders.setAccept(acceptableMediaTypes);
			}
			requestHeaders.setContentType(mediaType);
		}

		return requestHeaders;
	}

	/**
	 * HTTP.GET Rest 전송 parameter Message
	 * 
	 * @param url
	 * @param clazz
	 * @param method
	 * @param uriVariables
	 * @return
	 * @throws Exception
	 */
	public <T> T connecterSendGET(ClientCode clientCode, Class<T> clazz, MultiValueMap<String, ?> uriVariables)
			throws Exception {
		return connecterSend(clientCode, clazz, null, HttpMethod.GET, uriVariables, null);
	}

	/**
	 * HTTP.POST Rest 전송 parameter Message
	 * 
	 * @param url
	 * @param clazz
	 * @param method
	 * @param uriVariables
	 * @return
	 * @throws Exception
	 */
	public <T> T connecterSendPost(ClientCode clientCode, Class<T> clazz, MultiValueMap<String, ?> uriVariables)
			throws Exception {
		return connecterSend(clientCode, clazz, null, HttpMethod.POST, uriVariables, null);
	}

	/**
	 * HTTP.POST RestFull Object 전송
	 * 
	 * @param url
	 * @param clazz
	 * @param method
	 * @param t
	 * @param uriVariables
	 * @return
	 * @throws Exception
	 */
	public <T> T connecterSendPost(ClientCode clientCode, Class<T> clazz, HttpMethod method, T t,
			MultiValueMap<String, ?> uriVariables) throws Exception {
		return connecterSend(clientCode, clazz, t, HttpMethod.POST, uriVariables, null);
	}

	/**
	 * 외부 연동
	 * 
	 * @param url
	 * @param clazz
	 * @param t
	 * @param method
	 * @param uriVariables
	 * @return
	 * @throws Exception
	 * 
	 *             RestFull 사용시 http://localhost/{param1}/{param2} uriVariables
	 *             의 parameter 사용
	 * 
	 */
	@SuppressWarnings({ "unchecked"})
	private <T> T connecterSend(ClientCode clientCode, Class<T> clazz, T t, HttpMethod method,
			MultiValueMap<? extends String, ?> uriVariables, MediaType mediaType) throws Exception {

		try {
			// 1. MessageConverter 초기화
			MessageConverter messageConverter = new MessageConverter();
			if (clazz == null) {
				throw new Exception("잘못된 Response Type 입니다.");
			}

			// 2. URI 빌드, 쿼리파라미터 삽입, 인코딩(기본 UTF-8)
			// GET 의 parameter 셋팅
			UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
					.fromUriString(clientCode.getApiURL());
			if (method == HttpMethod.GET) {
				if (uriVariables != null) {
					Iterator<String> it = (Iterator<String>) uriVariables.keySet().iterator();
					while (it.hasNext()) {
						String keyStr = it.next();
						uriComponentsBuilder.queryParam(keyStr, uriVariables.get(keyStr));
					}
				}
			}

			String url = uriComponentsBuilder.build().encode().toString();

			// 3. HTTP 엔티티 설정 (헤더 정보 삽입)
			HttpEntity<?> requestEntity = null;
			if (t != null) {
				// String strJSON = MessageConverter.convertObjectToJson(t);
				// Post Object Parameter 셋팅
				// requestEntity = new HttpEntity( strJSON ,
				// createHeader(mediaType, null, this.tokenYN));

			} else {

				// requestEntity = new HttpEntity(uriVariables
				// ,createHeader(mediaType, null, this.tokenYN));
			}

			// 4. Template 생성 MessageConvertor
			RestTemplate restTemplate = messageConverter.getRestTemplate();

			// 5. Request 전문
			//logger.trace("uriVariables : " + uriVariables.toString());

			// 6. 외부 API 연결
			ResponseEntity<?> responseEntity = restTemplate.exchange(url, method, requestEntity, clazz, uriVariables);

			switch (responseEntity.getStatusCode()) {
			case OK:
				logger.trace("Response : " + responseEntity.getBody());
				// String responseJSONstr =
				// MessageConverter.convertObjectToJson(responseEntity.getBody());

				break;
			default:

				throw new Exception("내부 응답 알수없는오류");
			}

			// 7. Cookies
			HttpHeaders responseHeaders = responseEntity.getHeaders();
			List<String> cookiesList = responseHeaders.get("Set-Cookie");

			if (cookiesList != null && !cookiesList.isEmpty()) {

				for (String string : cookiesList) {
					String cookiesStr = string;
					logger.trace("Cookies read from response : " + cookiesStr);

					String[] cookiesSplit = cookiesStr.split(";");

					for (String cookieStr : cookiesSplit) {
						String[] keyValueSplit = cookieStr.split("=");
						// TODO OKEN 정보에 KeyValueSplit 사용
					}
				}
			}

			return (T) responseEntity.getBody();

		} catch (HttpClientErrorException e) {

			logger.error("HttpClientErrorException", e);

			switch (e.getStatusCode()) {
			case BAD_REQUEST:
			case UNAUTHORIZED:
			case NOT_FOUND:
				throw new Exception("잘못된 요청 실패 : " + e.getResponseBodyAsString());
			default:
				throw new Exception("잘못된 요청의 알수없는오류 : " + e.getResponseBodyAsString());
			}

		} catch (HttpServerErrorException e) {
			logger.error("HttpServerErrorException", e);

			switch (e.getStatusCode()) {
			case INTERNAL_SERVER_ERROR:
			case SERVICE_UNAVAILABLE:
				throw new Exception("내부 응답 실패 : " + e.getResponseBodyAsString());
			default:
				throw new Exception("내부 응답 실패-5XX :" + e.getResponseBodyAsString());
			}
		} catch (ResourceAccessException e) {
			if (e.getRootCause() instanceof ConnectTimeoutException) {
				logger.error("ConnectTimeoutException", e);
				throw new Exception("ConnectTimeoutException ");
			} else if (e.getRootCause() instanceof SocketTimeoutException) {
				logger.error("SocketTimeoutException", e);
				throw new Exception("SocketTimeoutException ");
			} else {
				logger.error("ResourceAccessException", e);
				throw new Exception("외부 연결실패3-IO :" + e.getMessage());
			}

		} catch (RestClientException e) {
			logger.error("RestClientException", e);
			throw new Exception("외부 연결실패 : " + e.getMessage());
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new Exception("알수없는 오류 : " + e.getMessage());
		}
	}

}
