FROM redhat/ubi9-minimal

ENV WILDFLY_VERSION=27.0.1.Final
ENV WILDFLY_USER=cybergrid
ENV WILDFLY_PASSWORD=password123
ENV WILDFLY_HOME=/opt/wildfly

# install dependencies
RUN microdnf update -y && \
    microdnf install -y java-21-openjdk-headless vim wget tar gzip

# install wildfly
RUN wget https://github.com/wildfly/wildfly/releases/download/${WILDFLY_VERSION}/wildfly-${WILDFLY_VERSION}.tar.gz && \
    tar xvf wildfly-${WILDFLY_VERSION}.tar.gz && \
    mv wildfly-${WILDFLY_VERSION} $WILDFLY_HOME

# setup wildfly group and user
RUN groupadd --system wildfly && \
    useradd -s /sbin/nologin --system -d $WILDFLY_HOME  -g wildfly wildfly && \
    chown -R wildfly:wildfly $WILDFLY_HOME

# add wildfly admin user
RUN $WILDFLY_HOME/bin/add-user.sh $WILDFLY_USER $WILDFLY_PASSWORD --silent

USER wildfly

# copy relevant content
#COPY --chown=wildfly ./startup.sh /home/wildfly/startup.sh
COPY --chown=wildfly ./standalone.conf $WILDFLY_HOME/bin
COPY --chown=wildfly ./cybergrid-interview-product-service-impl-0.0.1-SNAPSHOT.war $WILDFLY_HOME/standalone/deployments/

# ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

ENTRYPOINT exec /opt/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0