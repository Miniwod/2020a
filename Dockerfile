# �������Լ��İ汾
FROM openjdk:11
WORKDIR /app/
COPY ./* ./
RUN javac analy.java