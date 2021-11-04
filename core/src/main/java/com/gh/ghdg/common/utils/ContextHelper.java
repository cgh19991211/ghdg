package com.gh.ghdg.common.utils;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.common.BaseService;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.core.service.system.UserService;
import com.gh.ghdg.common.utils.constant.Constants;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContextHelper implements ApplicationContextAware {

    // 请求级

    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }
    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 取到如 http://127.0.0.1:8080/
     * @return
     */
    public static String getBasePath() {
        HttpServletRequest req = getRequest();
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
    }

    public static boolean isAjax() {
        String header = getRequest().getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header);
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static boolean isIE() {
        String[] IESignals = {"MSIE", "Trident"}; // "Edge"
        String userAgent = getRequest().getHeader("User-Agent");
        for (String signal : IESignals) {
            if (userAgent.contains(signal)){
                return true;
            }
        }
        return false;
    }

    public static String getRemoteIp() {
        HttpServletRequest request = getRequest();
        String ip;
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        return ip;
    }

    public static String getToken() {
        HttpServletRequest req = getRequest();
        Enumeration<String> hns = req.getHeaderNames();
        while(hns.hasMoreElements()) {
            String name = hns.nextElement();
            if( Constants.TOKEN.equals(name)) {
                return req.getHeader(name);
            }
        }
        return null;
    }

    public static String getTokenType() {
        HttpServletRequest req = getRequest();
        Enumeration<String> hns = req.getHeaderNames();
        while(hns.hasMoreElements()) {
            String name = hns.nextElement();
            if( Constants.TOKEN_TYPE.equals(name)) {
                return req.getHeader(name);
            }
        }
        return null;
    }


    // Cookie

    public static void setCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(ContextHelper.getRequest().getContextPath());
        ContextHelper.getResponse().addCookie(cookie);
    }

    public static String getCookie(String name) {
        Cookie[] cookies = getRequest().getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if(name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    // 会话级

    public static boolean isLogin() {
        return getCurUser() != null;
    }
    public static boolean isFirstLogin() {
        Boolean b = ContextHelper.getFromSession( Constants.FIRST_LOGIN);
        return b == null ? true : b;
    }
    public static void setCurUser(User user) {
        setInSession( Constants.CUR_USER, user);
    }
    public static User getCurUser() {
        User cur = getFromSession( Constants.CUR_USER);
        if(cur != null && StrUtil.isNotEmpty(getToken()) && "2".equals(getTokenType())) {
            return getBean(UserService.class).one(cur.getId()).get();
        }
        return cur;
    }
    /*public static String getCurCompanyId() {
        return getCurCompany().getId();
    }*/
//    public static Company getCurCompany() {
//        return getCurUser().getCompany();
//    }
    /*public static Corp getCurCorp() {
        Corp corp = getCurUser().getPerson().getCorp();
        if (null == corp) {
            throw new RuntimeException("非公司用户");
        }
        return corp;
    }*/
    public static String getCurUserId() {
        return getCurUser().getId();
    }
    public static String getCurUsername() {
        return getCurUser().getUsername();
    }
    /*public static Person getCurPerson() {
        Person person = getCurUser().getPerson();
        if(person == null) {
            throw new RuntimeException("当前用户未关联人员");
        }
        return person;
    }
    public static String getCurPersonId() {
        return getCurPerson().getId();
    }
    public static String getCurPersonName() {
        return getCurPerson().getPersonName();
    }*/

    public static List<Menu> getCurMenus() {
        return (List<Menu>) getFromSession( Constants.CUR_MENUS);
    }
    public static void setCurMenus(List<Menu> menus) {
        setInSession( Constants.CUR_MENUS, menus);
    }

    public static List<Menu> getCurAppMenus() {
        return (List<Menu>) getFromSession( Constants.CUR_APP_MENUS);
    }
    public static void setCurAppMenus(List<Menu> menus) {
        setInSession( Constants.CUR_APP_MENUS, menus);
    }

    public static List<Menu> getCurMenuRoots() {
        return (List<Menu>) getFromSession( Constants.CUR_MENU_ROOTS);
    }
    public static void setCurMenuRoots(List<Menu> roots) {
        setInSession( Constants.CUR_MENU_ROOTS, roots);
    }

    public static List<Menu> getCurAppMenuRoots() {
        return (List<Menu>) getFromSession( Constants.CUR_APP_MENU_ROOTS);
    }
    public static void setCurAppMenuRoots(List<Menu> roots) {
        setInSession( Constants.CUR_APP_MENU_ROOTS, roots);
    }

    public static SimpleAuthorizationInfo getCurAuthInfo() {
        return (SimpleAuthorizationInfo) getFromSession( Constants.CUR_AUTH_INFO);
    }
    public static void setCurAuthInfo(SimpleAuthorizationInfo authorizationInfo) {
        setInSession( Constants.CUR_AUTH_INFO, authorizationInfo);
    }

    public static Map<String, SimpleAuthorizationInfo> getCurAuthInfoMap() {
        return (Map<String, SimpleAuthorizationInfo>) getFromSession( Constants.CUR_AUTH_INFO_MAP);
    }
    public static void setCurAuthInfoMap(Map<String, SimpleAuthorizationInfo> authorizationInfoMap) {
        setInSession( Constants.CUR_AUTH_INFO_MAP, authorizationInfoMap);
    }

    public static void setInSession(String key, Object obj) {
        getSession().setAttribute(key, obj);
    }
    public static <T>T getFromSession(String key) {
        return (T)getSession().getAttribute(key);
    }

    public static HttpSession getSession() {
        return getRequest().getSession(); // SecurityUtils.getSubject().getSession(true);
    }


    // 应用级

    public static ServletContext servletContext;
    public static ApplicationContext applicationContext;
    public static Map<String, BaseService> entityServiceMap = new HashMap<>();// com.kl.domain.user -> userService
    public static Map<String, BaseService> serviceMap = new HashMap<>();      // userService -> userService

    public static boolean isProd() {
        return "prod".equals(getProfle());
    }

    private static String getProfle() {
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }

    public static void setInApplication(String key, Object obj) {
        if(servletContext != null) {
            servletContext.setAttribute(key, obj);
        }
    }
    public static Object getFromApplication(String key) {
        return servletContext.getAttribute(key);
    }

//    public static void put2ServiceMap4Audit(String name, BaseService service) {
//        serviceMap4Audit.put(name, service);
//    }

    public static BaseService getService(String key) {
        BaseService service = entityServiceMap.get(key);
        if(service != null) {
            return service;
        }
        return serviceMap.get(key);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
    
    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    
    // 管理移动端APP 应用级
//    public static Map<String, Object> manageServiceMap = Maps.newHashMap();

//    public static void putManageServiceMap(User user) {
//        manageServiceMap.put("session", user);
//    }

}
