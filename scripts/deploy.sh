#배포 스크립트

#!/bin/bashs

REPOSITORY=/root/app/step2
PROJECT_NAME=awsPractice

echo "> Build 파일 복사"
sudo chmod 777 $REPOSITORY/
sudo chmod 777 $REPOSITORY
sudo chmod 777 $REPOSITORY/zip
sudo chmod 777 /root/app/application-oauth.properties /root/app/application-real-db.properties

sudo cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f1 awsPractice | grep jar | awk '{print $1}')

echo "현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다"

else
  echo "> kill -15  $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | grep -v "plain.jar" | tail -n 1)
echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar -DSpring.config.location=classpath:/application.properties,classpath:/application-real.properties,classpath:/application-oauth.properties,classpath:/application-real-db.properties -DSpring.profiles.active=real $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
