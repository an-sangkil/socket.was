# WAS

#INFO
Socket���� ���� ������ WAS �Դϴ�.
403,404,500,501 Error�� ������ ������
��  DocumentRoot ���� ���� �ٸ� Error Message �� errorfile.json ���� ���� �Ҽ� �ֽ��ϴ�.


#���� ����� ������ �����ϴ�.

>mvn install ����

wasProject ���� �Ʒ� lib������ was.jar  ������ �����˴ϴ�.(��ƿ�� Junit Code ����) 

>java -jar was.jar
���� WAS�� ���� �մϴ�.

>java -jar was.jar "documetRoot" "port"

documentRoot �� ��� ������ �ش� ������ "/src/main/webapp" ���丮�� Root�� �����մϴ�.
port�� ��� ������ 80 port�� �����˴ϴ�.

#�߰�����
*Header Parser�� ����� ������ Ȯ���մϴ�.(requestProcess)
*Log level Trace

#������ ������ �����غ��ϴ�.
http://127.0.0.1:80/Hello
http://127.0.0.1:80/service.Hello
http://127.0.0.1:80/service.TimeNow		
