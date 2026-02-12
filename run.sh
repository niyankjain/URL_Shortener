#!/bin/bash

APP_HOME=$(dirname "$0")

java \
-cp "$APP_HOME/url-shortener-1.0.0-plain.jar:$APP_HOME/lib/*" \
-Dspring.config.location=file:$APP_HOME/external-config/ \
com.io.infracloud.urlshortener.URLShortenerApplication