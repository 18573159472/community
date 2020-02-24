package life.pangs.communitydev.provider;


import com.alibaba.fastjson.JSON;
import life.pangs.communitydev.dto.AccessTokenDTO;
import life.pangs.communitydev.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class GitHubProvider {


    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        String json = JSON.toJSONString(accessTokenDTO);
        String url = "https://github.com/login/oauth/access_token";

        final MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(json, mediaType);
        Request request;
        request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String backStr = response.body().string();
            String token = getParam(backStr);

            //System.out.println(backStr);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(str,GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getParam(String backParam){
        Map<String,String> map = new HashMap<String, String>();
        String[] params = backParam.split("&");
        if(params != null && params.length>0){
            for(String s : params){
                String[] keyAndValues = s.split("=");
                if(keyAndValues != null && keyAndValues.length>=2){
                    map.put(keyAndValues[0],keyAndValues[1]);
                }
            }
        }
        Set<String> set = map.keySet();
        for(String key :set){
            if("access_token".equals(key)){
                return map.get(key);
            }
        }
        return null;
    }

}
