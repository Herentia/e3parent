package cn.e3mall.controller;

import com.e3mall.common.utils.FastDFSClient;
import com.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author haohan
 * 03/06/2019 - 04:14 下午
 * 图片上传到fastDFS服务器
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @ResponseBody
    @RequestMapping("/pic/upload")
    public String uploadFile(MultipartFile uploadFile) {

        try {
            //获取图片服务器客户端，把图片上传到上面
            FastDFSClient dfsClient = new FastDFSClient("classpath:conf/client.conf");
            //获取文件扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //上传图片，等到图片的地址和文件名
            String url = dfsClient.uploadFile(uploadFile.getBytes(), extName);
            //拼接成完整的URL
            url = IMAGE_SERVER_URL + url;
            System.out.println(url);
            //封装到map中返回
            Map<String, Object> map = new HashMap<>();
            map.put("error", 0);
            map.put("url", url);
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("error", 1);
            map.put("url", "图片上传失败...");
            return JsonUtils.objectToJson(map);
        }
    }

}
