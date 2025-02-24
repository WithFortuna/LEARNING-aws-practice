#IDLE한 jar파일이 잘 수행되었다면 switch_proxy한다.

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # real문자열이 있는지 검증
    echo "> Health check 성공"
    switch_proxy
    break
  else
    echo "> Health check의 응답을 알수없거나 실행상태가 아닙니다"
    echo "> health check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health check 실패"
    echo "> 엔진 엑스에 연결하지않고 배포를 종료합니다"
    exit 1
  fi
  echo "> Health check 연결실패. 재시도.."
  sleep 10
done