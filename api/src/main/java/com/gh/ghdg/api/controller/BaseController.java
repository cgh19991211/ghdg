package com.gh.ghdg.api.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gh.ghdg.sysMgr.BaseDao;
import com.gh.ghdg.sysMgr.BaseEntity;
import com.gh.ghdg.sysMgr.BaseService;
import com.gh.ghdg.api.utils.ApiConstants;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.security.JwtUtil;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.constant.Constants;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 获取用户id
 * 保存
 * 删除
 * 获取ip
 * 获取JSON
 * 获取请求负载
 * 获取请求
 * 获取会话
 * @param <T>
 * @param <S>
 * @param <R>
 */
public abstract class BaseController<T extends BaseEntity, S extends BaseDao<T>, R extends BaseService<T, S>> {

    @Autowired
    protected R service;

    /**
     * 根据token获取用户id，如果不存在则抛出异常
     *
     * @param request
     * @return
     */
    public String getIdUser(HttpServletRequest request) {
        String token = request.getHeader(Constants.ACCESS_TOKEN_NAME);

        String idUser = JwtUtil.getUserId(token);
        if (idUser == null) {
            throw new RuntimeException("用户不存在");
        }
        return idUser;
    }

    /**
     *保存
     * @param t
     * @return
     * @throws Exception
     */
    public Result save(@ModelAttribute("t") T t)throws Exception{
        service.checkTimestamp(t);
        return Result.saveSuc(service.save(t));
    }

    /**
     * 删除
     * @param t
     * @return
     * @throws Exception
     */
    public Result delete(@ModelAttribute("t")T t)throws Exception{
        List<T> ts = service.delete(t);
        return Result.delSuc(ts);
    }

    /**
     * 获取客户端ip
     *
     * @param req
     * @return
     */
    public String getRealIp(HttpServletRequest req) {
        String ip = req.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || ApiConstants.IP_UNKNOW.equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ApiConstants.IP_UNKNOW.equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ApiConstants.IP_UNKNOW.equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || ApiConstants.IPV6_LOCALHOST.equals(ip)) {
            ip = ApiConstants.IPV4_LOCALHOST;
        }
        return ip;
    }

    /**
     * 获取前端传递过来的json字符串<br>
     * 如果前端使用axios的data方式传参则使用改方法接收参数
     *
     * @return
     */
    public String getjsonReq() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(HttpKit.getRequest().getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            if (sb.length() < 1) {
                return "";
            }
            String reqBody = URLDecoder.decode(sb.toString(), "UTF-8");
            reqBody = reqBody.substring(reqBody.indexOf("{"));
            return reqBody;
        } catch (IOException e) {

            return "";
        }
    }

    public <T> T getFromJson(Class<T> klass) {
        String jsonStr = getjsonReq();
        if (StrUtil.isEmpty(jsonStr)) {
            return null;
        }
        JSONObject json = JSONObject.parseObject(jsonStr);
        return JSON.toJavaObject(json, klass);
    }

    protected  String getRequestPayload( ){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = getRequest().getReader();) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    protected  <T>T getRequestPayload(  Class<T> klass)     {
        String json = getRequestPayload();
        try {
            T result = null;
            if(klass== Map.class||klass==null){
                result = (T) Json.fromJson(json);
            }else {
                result = Json.fromJson( klass,json);
            }
            return result;
        }catch (Exception e){

        }
        return null;
    }

    protected HttpServletRequest getRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return sra.getRequest();
    }

    protected Object getSession(String key){
        return getRequest().getSession().getAttribute(key);
    }
    protected  void setSession(  String key,Object val){
        getRequest().getSession().setAttribute(key,val);
    }

    public String getIp(){
        String ip = getRequest().getHeader("x-forwarded-for");
        if(null==ip || "".equals(ip)){
            //测试ip
            ip = "101.81.121.39";
        }
        return ip;

    }
    
    /**
     * 获取客户端token
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(Constants.ACCESS_TOKEN_NAME);
    }
    
    /*
    注册将字符串转换为Date的属性编辑器，该编辑器仅仅对当前controller有效
	 */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        /**
         * 方式二：使用WebDataBinder注册一个自定义的编辑器，编辑器是日期类型
         * 使用属性编辑器实现：重载setAsText,getAsText
         */
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public String getAsText() {
                return new SimpleDateFormat("yyyy-MM-dd hh:mm")
                        .format((Date) getValue());
            }
            @Override
            public void setAsText(String text) {
                try {
                    setValue(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(text));
                } catch (Exception e) {
                    e.printStackTrace();
                    setValue(null);
                }
            }
        });
    }
    
    private class TimestampEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Timestamp ts = null;
            if(StrUtil.isNotEmpty(text)) {
                ts = Timestamp.valueOf(text);
            }
            super.setValue(ts);
        }
    }
    
}
