package com.zyf.boot.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RepeatedlyReadRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String contentType = request.getContentType();
        if (StrUtil.equalsIgnoreCase(contentType, "application/json")) {
            RepeatedlyReadRequestWrapper repeatedlyReadRequestWrapper = new RepeatedlyReadRequestWrapper(request);
            req = repeatedlyReadRequestWrapper;
            chain.doFilter(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }


    /**
     * 封装Request，提供InputStream重复读取支持
     */
    public class RepeatedlyReadRequestWrapper extends HttpServletRequestWrapper {

        private final String body;
        private Map<String , String[]> params = new HashMap<>();

        RepeatedlyReadRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] chunk = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(chunk)) > 0) {
                    stringBuilder.append(chunk, 0, bytesRead);
                }
            }
            body = stringBuilder.toString();
            JSONObject json = JSONUtil.parseObj(body);
            for (Map.Entry<String, Object> stringObjectEntry : json.entrySet()) {
                String key = stringObjectEntry.getKey();
                Object value = stringObjectEntry.getValue();
                addParameter(key, value);
            }
        }

        public String getBody() {
            return body;
        }

        /**
         * 重新getInputStream，缓存Body数据
         */
        @Override
        public ServletInputStream getInputStream() {

            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
            return new ServletInputStream() {
                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                    throw new RuntimeException("Not implemented");
                }
            };
        }

        /**
         * 读取缓存数据
         */
        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        @Override
        public String getParameter(String name) {//重写getParameter，代表参数从当前类中的map获取
            Object v = params.get(name);
            if (v == null) {
                return null;
            } else {
                String[] strArr = (String[]) v;
                if (strArr.length > 0) {
                    return strArr[0];
                } else {
                    return null;
                }
            }
        }

        @Override
        public String[] getParameterValues(String name) {//同上
            Object v = params.get(name);
            if (v == null) {
                return null;
            } else {
                return (String[]) v;
            }
        }

        public void addAllParameters(Map<String, Object> otherParams) {//增加多个参数
            for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
                addParameter(entry.getKey(), entry.getValue());
            }
        }

        public void addParameter(String name, Object value) {//增加参数
            if (value != null) {
                if (value instanceof String[]) {
                    params.put(name, (String[]) value);
                } else if (value instanceof String) {
                    params.put(name, new String[]{(String) value});
                } else {
                    params.put(name, new String[]{String.valueOf(value)});
                }
            }
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return params;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            Vector l = new Vector(params.keySet());
            return l.elements();

        }
    }

}