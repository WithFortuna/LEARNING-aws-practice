ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/root/app/step3
PROJECT_NAME=awsPractice
sudo chmod -R 777 $REPOSITORY/
sudo chmod -R 777 $REPOSITORY
sudo chmod -R 777 $REPOSITORY/zip

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY"

sudo cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | grep -v "plain.jar" | tail -n 1)

echo "> JAR Name: $JAR_NAME"
echo "> $JAR_NAME 에 실행권한 추가"
sudo chmod 777 /root/app/step3/zip/$JAR_NAME

echo "> $JAR_NAME 실행"
IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 을 profile=$IDLE_PROFILE 로 실행합니다."
sudo nohup java -jar -DSpring.config.location=classpath:/application.yml,classpath:/application-$IDLE_PROFILE.properties,file:./application-oauth.properties,file:./application-real-db.properties -DSpring.profiles.active=$IDLE_PROFILE $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

