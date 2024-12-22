package org.example;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import com.youdao.aicloud.translate.Translate;
public class Main {
    //调用翻译接口的函数
    public  static String actionPerformed(String op) {
        try
        {
            String a = Translate.use(op);//使用有道翻译
            JSONObject jsonObject = new JSONObject(a);
            JSONArray explainsArray = jsonObject.getJSONArray("translation");
            String yf = explainsArray.getString(0);
            System.out.println(yf);
            return yf;
        }
        catch (NoSuchAlgorithmException E){
            System.out.print("有道翻译异常\n");
            return "";
        }
    }
    //主函数
    public static void main(String[] args) throws IOException {
       int a = 5001;
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.config")) {
            properties.load(fis);
            String value1 = properties.getProperty("port");
           a =Integer.parseInt(value1);
           //System.out.println(value1);
        } catch (IOException e) {
            System.out.println("配置文件访问错误");
            e.printStackTrace();
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(a), 0);
        server.createContext("/", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream inputStream = exchange.getRequestBody();
                byte[] buffer = new byte[2048];
                int bytesRead;
                StringBuilder content = new StringBuilder();
                while ((bytesRead = inputStream.read(buffer))!= -1) {
                    content.append(new String(buffer, 0, bytesRead));
                }
                String jsonData = content.toString();
                //System.out.println(jsonData);
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    String originalPrompt = jsonObject.getString("prompt");

                    String newPrompt = actionPerformed(originalPrompt);
                    jsonObject.put("prompt", newPrompt);
                    exchange.sendResponseHeaders(200, jsonObject.toString().length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(jsonObject.toString().getBytes());
                    os.close();
                } catch (Exception e) {
                    System.out.println("写入异常");
                    exchange.sendResponseHeaders(500, -1);
                    exchange.getResponseBody().close();
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
                exchange.getResponseBody().close();
            }
        });
        server.start();
    }
}