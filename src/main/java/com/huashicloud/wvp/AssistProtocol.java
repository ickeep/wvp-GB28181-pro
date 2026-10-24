package com.huashicloud.wvp;

import java.net.URI;

/** 为现网使用明文 HTTP 的内部 Assist 端口恢复正确协议。 */
public final class AssistProtocol {

    // 只改写显式配置的内部端点，保留外部 HTTPS、其他端口及带认证信息的 URL。
    public static String normalize(String endpoint, String mediaServerIp, int assistPort) {
        if (endpoint == null) {
            return null;
        }
        URI uri = URI.create(endpoint);
        if ("https".equalsIgnoreCase(uri.getScheme())
                && mediaServerIp != null && mediaServerIp.equalsIgnoreCase(uri.getHost())
                && assistPort == uri.getPort() && uri.getUserInfo() == null) {
            return "http" + endpoint.substring(endpoint.indexOf(':'));
        }
        return endpoint;
    }
}
