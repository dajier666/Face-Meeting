package com.ourwork.meetingsystem.Utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * 人脸注册
 */
public class BaiduAiUtils {
    // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
    private final static String accessToken = "24.f2ea225619c9649f4b57c54d74e47e2a.2592000.1752995652.282335-119117257";

    //    https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/delete删除用户
    public static String add(Integer userID, String photo) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            // 处理base64图片格式，移除data:image/jpeg;base64,前缀
            String processedPhoto = processBase64Image(photo);
            
            Map<String, Object> map = new HashMap<>();
            map.put("image", processedPhoto);
            map.put("group_id", "group02");
            map.put("user_id", String.valueOf(userID));
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);


            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String faceSearch(String photo) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            // 处理base64图片格式，移除data:image/jpeg;base64,前缀
            String processedPhoto = processBase64Image(photo);
            
            Map<String, Object> map = new HashMap<>();
            map.put("image", processedPhoto);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "group02");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);


            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String faceGetList(Integer userID) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/face/getlist";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", String.valueOf(userID));
            map.put("group_id", "group01");

            String param = GsonUtils.toJson(map);


            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String groupGetusers() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/getusers";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("group_id", "group01");

            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String groupAdd() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/add";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("group_id", "group02");

            String param = GsonUtils.toJson(map);



            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer jsonPaser(String jsonStr) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // 第一步：解析根 JSON 字符串为 Map
            Map<String, Object> rootMap = mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {});

            // 第二步：获取 result 字段（假设其为 Map 类型）
            Object resultObj = rootMap.get("result");
            if (!(resultObj instanceof Map)) {
                throw new IllegalArgumentException("result 字段必须为对象类型");
            }
            Map<String, Object> resultMap = (Map<String, Object>) resultObj;

            // 第三步：获取 user_list 字段（假设其为 List 类型）
            Object userListObj = resultMap.get("user_list");
            if (!(userListObj instanceof List)) {
                throw new IllegalArgumentException("user_list 字段必须为数组类型");
            }
            List<Map<String, Object>> userList = (List<Map<String, Object>>) userListObj;

            // 第四步：校验 userList 非空
            if (userList.isEmpty()) {
                return null;
            }

            // 第五步：处理第一个用户
            Map<String, Object> user = userList.get(0);
            Object userIdObj = user.get("user_id");
            Object scoreObj = user.get("score");

            // 校验 userId 和 score 的有效性
            if (userIdObj == null || scoreObj == null) {
                return null;
            }

            // 转换数据类型
            String userId = userIdObj.toString(); // 统一转为字符串，避免类型问题
            double score = (Double) scoreObj;     // JSON 数值默认转为 Double

            // 业务逻辑判断
            if (!userId.isEmpty() && score >= 90) {
                return Integer.parseInt(userId); // 确保 userId 可转为整数
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException("JSON 解析失败：" + e.getMessage(), e);
        }


    }
    
    /**
     * 处理前端传来的base64图片数据
     * 移除data:image/jpeg;base64,等前缀，只保留纯base64编码
     * @param base64Image 前端传来的base64图片数据
     * @return 处理后的纯base64编码
     */
    private static String processBase64Image(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) {
            return base64Image;
        }
        
        // 检查是否包含data:image前缀
        if (base64Image.startsWith("data:image/")) {
            // 找到base64,后的位置
            int commaIndex = base64Image.indexOf(",");
            if (commaIndex != -1 && commaIndex < base64Image.length() - 1) {
                // 返回逗号后面的纯base64编码
                return base64Image.substring(commaIndex + 1);
            }
        }
        
        // 如果没有前缀，直接返回原始数据
        return base64Image;
    }


    public static Map<String, Object> faceMessageCheck(String jsonStr) throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        // 使用 TypeReference 指定 Map 泛型类型
        return mapper.<Map<String, Object>>readValue(
                jsonStr,
                new TypeReference<Map<String, Object>>() {}
        );
    }
}