FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine


ARG TIME_ZONE=Europe/Amsterdam

RUN apk add -Uuv less openssl tzdata
RUN cp /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime

RUN apk --purge -v del py-pip && \
    rm /var/cache/apk/*

WORKDIR /gatling

COPY target/ ./target
COPY bin/run.sh .
COPY ./g2i .
RUN ["chmod", "+x", "./g2i"]


ENTRYPOINT ["sh","./run.sh"]
