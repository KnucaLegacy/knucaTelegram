FROM openjdk:8u151-jre

WORKDIR /knuca-bot

ENV TAR

COPY knuca-bot/${TAR} knuca-bot/app.tgz

RUN mkdir knuca-bot/app \
        && tar -xvzf knuca-bot/app.tgz --strip-components=1 -C knuca-bot/app

EXPOSE 8080

ENV NAME knuca-bot

ENTRYPOINT knuca-bot/app/bin/knuca-bot
