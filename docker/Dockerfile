FROM openjdk:8u151-jre

WORKDIR /knuca-platform

ENV TAR knucaTelegram-0.0.1-SNAPSHOT.tgz

COPY knuca-bot/${TAR} knuca-bot/app.tgz

RUN mkdir knuca-bot/app \
        && tar -xvzf knuca-bot/app.tgz --strip-components=1 -C knuca-bot/app

EXPOSE 8080

ENV NAME knuca-bot

ENTRYPOINT knuca-platform/app/bin/knuca-bot
