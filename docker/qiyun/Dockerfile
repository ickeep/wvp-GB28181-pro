# 保留启云现有镜像的运维工具与配置布局，仅替换经过验证的运行时和应用。
ARG JAVA_RUNTIME_IMAGE=eclipse-temurin:21-jre@sha256:227118764cccd3c3294617061aaa93a08309c63a395eefa83e6b35032992ea99
ARG WVP_BASE_IMAGE=registry.cn-gdgz1.ctyun.cn/paas/wangwuli-wvp@sha256:254dd9a274f4c20ed81469d74b1cafa6c7a846d0c74c0bcd941d4c9cd9434997
FROM ${JAVA_RUNTIME_IMAGE} AS java_runtime
FROM ${WVP_BASE_IMAGE}

ARG SOURCE_REVISION
ARG SOURCE_PATCH_SHA256
LABEL org.opencontainers.image.source="https://github.com/648540858/wvp-GB28181-pro" \
      org.opencontainers.image.revision="${SOURCE_REVISION}" \
      com.qiyun.source.patch-sha256="${SOURCE_PATCH_SHA256}"

COPY --from=java_runtime /opt/java/openjdk /opt/java/openjdk
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="/opt/java/openjdk/bin:${PATH}"
WORKDIR /opt/wvp

# 不允许旧版 Jar 或字节码覆盖补丁在新应用旁继续生效。
RUN rm -f /opt/wvp/wvp-pro-*.jar /opt/wvp/recording-fix.jar \
    && java -version
COPY target/qiyun-wvp.jar /opt/wvp/wvp.jar

# 部署负责以参数传入环境配置；镜像默认入口不启动内置数据库或媒体服务。
ENTRYPOINT ["java", "-jar", "/opt/wvp/wvp.jar", "--spring.config.location=/opt/wvp/config/application.yml"]
