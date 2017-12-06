# WAS

#INFO
Socket으로 만든 간단한 WAS 입니다.
403,404,500,501 Error를 가지고 있으며
각  DocumentRoot 마다 각기 다른 Error Message 를 errorfile.json 에서 설정 할수 있습니다.


#실행 방법은 다음과 같습니다.

>mvn install 실행

wasProject 폴더 아래 lib폴더와 was.jar  파일이 생성됩니다.(유틸성 Junit Code 실행) 

>java -jar was.jar
으로 WAS를 구동 합니다.

>java -jar was.jar "documetRoot" "port"

documentRoot 가 비어 있으면 해당 폴더의 "/src/main/webapp" 디렉토리를 Root로 생성합니다.
port가 비어 있으면 80 port로 생성됩니다.

#추가정보
*Header Parser로 헤더의 내용을 확인합니다.(requestProcess)
*Log level Trace

#생성된 서버로 접속해봅니다.
http://127.0.0.1:80/Hello
http://127.0.0.1:80/service.Hello
http://127.0.0.1:80/service.TimeNow		
