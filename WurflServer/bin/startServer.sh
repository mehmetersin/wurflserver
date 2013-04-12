#JAVA_HOME="/opt/java6/jre"
MEM_ARGS="-Xms256m"

CLASSPATH=""

for i in ../target/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done


for i in ../dependencies/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done

for i in ../conf/*; do
    CLASSPATH=$CLASSPATH:$i
done



RUNCMD="${JAVA_HOME}/bin/java -DWurfleServer ${MEM_ARGS} -Dlog4j.configuration=file:../conf/log4j.properties -cp ${CLASSPATH} com.infovas.services.wurfleserver.WurflServerStarter"

$RUNCMD &
