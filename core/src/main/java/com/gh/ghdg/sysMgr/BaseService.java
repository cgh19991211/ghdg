package com.gh.ghdg.sysMgr;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.common.cache.CacheName;
import com.gh.ghdg.common.commonVo.DynamicSpecifications;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.ReflectHelper;
import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.bean.Unique;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * value:当前缓存分组名称
 * key：缓存key
 * unless：使用场景，如果unless的表达式返回null则不缓存
 * @Cacheable(value = CacheName.APPLICATION, key = "#root.targetClass.simpleName+':'+#id",unless = "#result == null")
 *
 *
 * @CacheEvict(value = CacheName.APPLICATION,key = "#root.targetClass.simpleName+':'+#id")
 * @param <T>
 * @param <D>
 */
public abstract class BaseService<T extends BaseEntity, D extends BaseDao<T>> {
    static{

    }
    @Autowired
    private EntityManager entityManager;

    @Autowired
    protected D dao;
    
    private Class<T> klass;

    //获取实体的clazz
    public BaseService(){
        klass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
    @Transactional
    public T save(T t) throws Exception{
        //TODO:检验可否保存

        //TODO:检查外键
        checkForeignKey(t);
        //TODO:检查唯一
        checkUnique(t);

        return dao.save(t);
    }
    
    @Transactional
    public List<T> batchSave(List<T> list)throws Exception{
        List<T> response = new ArrayList<>();
        for(T t:list){
            //TODO:检查外键
            checkForeignKey(t);
            //TODO:检查唯一
            checkUnique(t);
            response.add(t);
            dao.save(t);
        }
        return response;
    }
    
    @Transactional
    public T modifyInfo(T a) throws Exception {
        T b = dao.findById(a.getId()).get();
        List<String> fieldNames = ReflectHelper.getFieldNames(klass);
        for(String fieldName:fieldNames){
            Object fieldValue = ReflectHelper.getFieldValue(a, fieldName);
            if(!StrUtil.isEmptyIfStr(fieldValue)){
                ReflectHelper.setValue(b,fieldName,fieldValue);
            }
        }
        return dao.save(b);
    }

    /**
     * 删除: 删除应该有返回值，表格重新加载数据
     */
    @Transactional
//    @CacheEvict(value = CacheName.APPLICATION,key = "#root.targetClass.simpleName+':'+#id")
    public List<T> delete(T t) throws Exception{
        //TODO:判断是否可以删除

        dao.delete(t);
        return null;
    }

    /**
     * 批量删除：根据前端表单传来的多选的id，先进行查找再删除
     * @param ids
     * @throws Exception
     */
    @Transactional
//    @CacheEvict(value = CacheName.APPLICATION,key = "#root.targetClass.simpleName+':'+#id")
    public void batchDelete(String ids) throws Exception{
        //传入的ids按逗号分割
        for (String id : StringHelper.split(",",ids)) {
            T t = dao.getOne(id);
            delete(t);
        }
    }


    
    /**
     * 根据id查一条记录
     * @param id
     * @return
     * @Cacheable 缓存，如果，该注解所在类实现了某个接口，那这个方法也得出现在接口里，否则缓存无效
     */
//    @Cacheable(value = CacheName.APPLICATION, key = "#root.targetClass.simpleName+':'+#id",unless = "#result == null")
    public Optional<T> one(String id) {
        Optional<T> byId = dao.findById(id);
        return byId;
    }
    
    /**
     * 根据一条规范查一条规范
     * @param spec
     * @return
     */
//    @Cacheable(value = CacheName.APPLICATION, key = "#root.targetClass.simpleName+':'+#id",unless = "#result == null")
    public T getOne(SearchFilter filter) {
    
        List<T> list = queryAll(filter);
        return list.isEmpty()?null:list.get(0);
    }
    
    /**
     * 根据多条过滤条件查询一条
     * @param filters
     * @return
     */
//    @Cacheable(value = CacheName.APPLICATION, key = "#root.targetClass.simpleName+':'+#id",unless = "#result == null")
    public T getOne(List<SearchFilter> filters){
        List<T> list = queryAll(filters);
        return list.isEmpty()?null:list.get(0);
    }

    /**
     * select *
     * @return
     */
//    @Cacheable(value = CacheName.APPLICATION, key = "#root.targetClass.simpleName+':'+#id",unless = "#result == null")
    public List<T> queryAll() {
        return dao.findAll();
    }
    
    /**
     * 列表
     * @param t
     * @return
     * @throws Exception
     */
//    @Cacheable(value = CacheName.APPLICATION, key = "#root.targetClass.simpleName+':'+#id",unless = "#result == null")
    public List<T> queryAll(List<SearchFilter> filters) {
        return queryAll(filters,null);
    }
    
//    @Cacheable(value = CacheName.APPLICATION, key = "#root.targetClass.simpleName+':'+#id",unless = "#result == null")
    public List<T> queryAll(SearchFilter filter){
        return queryAll(filter,null);
    }
    
    /**
     * 列表 复杂查询
     * @param t
     * @param spec
     * @return 返回符合过滤条件的所有行
     */
//    @Cacheable(value = CacheName.APPLICATION, key = "#root.targetClass.simpleName+':'+#id",unless = "#result == null")
    public List<T> queryAll(List<SearchFilter> filters, Sort sort) {
        //TODO:添加过滤条件，以及sort
    
        Specification<T> specification = DynamicSpecifications.bySearchFilter(filters,getDataClass());
        if(sort==null){
            return dao.findAll(specification);
        }
        return dao.findAll(specification,sort);
    }
    
    public List<T> queryAll(SearchFilter filter, Sort sort) {
        if(filter!=null){
            return queryAll(CollUtil.newArrayList(filter),sort);
        }else {
            return queryAll(CollUtil.newArrayList(), sort);
        }
    }
    

    /**
     * 分页查询
     * 不使用缓存，因为查询条件(username,nickname)可能每次查都不一样，要是走缓存的话查询条件就没用了，每次取的缓存都一样。
     * @param page
     * @return page
     */
//    @Cacheable(value = CacheName.APPLICATION, key = "#root.targetClass.simpleName+':'+#id",unless = "#result == null")
    public Page<T> queryPage(Page<T> page) {
        Pageable pageable = null;
        if(page.getSort()!=null) {//按照自定义的sort来排序
            pageable = PageRequest.of(page.getCurrent()-1, page.getSize(), page.getSort());
        }else{//默认根据id排序
            pageable = PageRequest.of(page.getCurrent()-1,page.getSize(), Sort.Direction.DESC,"id");
        }
        //获取当前实体的类型
//        Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Specification<T> specification = DynamicSpecifications.bySearchFilter(page.getFilters(),klass);
        org.springframework.data.domain.Page<T> pageResult  = dao.findAll(specification,pageable);
        page.setTotal(Integer.valueOf(pageResult.getTotalElements()+""));
        page.setRecords(pageResult.getContent());
        return page;
    }

    /**
     * 外键字段没有设置,则赋值为null
     * @param t
     * @throws Exception
     */
    protected void checkForeignKey(T t) throws Exception {
        for (Field field : t.getClass().getDeclaredFields()) {
            if(!field.getName().contains("$")) { // 域不包含$
                Object value = ReflectHelper.getValue(t, field);
                if(value instanceof BaseEntity) {
                    if(StrUtil.isEmpty(((BaseEntity)value).getId())) {
                        ReflectHelper.setValue(t, field, null);
                    }
                }
            }
        }
    }

    /**
     * 判断唯一
     * @param t
     * @throws Exception
     */
    protected void checkUnique(T t) throws Exception {
        Class<?> clazz = t.getClass();
        while(null != clazz) {
            for(Field field : clazz.getDeclaredFields()) {
                Unique unique = field.getAnnotation(Unique.class);
                if(null != unique) {
                    // 属性
                    Object value = ReflectHelper.getValue(t, field);
                    // 额外
                    String[] extraFields = unique.extraFields();
                    Object[] extraValues = new Object[extraFields.length];
                    for(int i = 0; i < extraFields.length; i++) {
                        extraValues[i] = ReflectHelper.getDotedValue(t, extraFields[i]);
                    }
                    // Specification
                    Specification spec = (root, query, cb) -> {
                        List<Predicate> list = new ArrayList<>();
                        // 属性
                        Predicate predicate = cb.equal(root.get(field.getName()), value);
                        list.add(predicate);
                        // 额外
                        for(int i = 0; i < extraFields.length; i++) {
                            Path extraPath = null;
                            for(String p : StringHelper.split("\\.",extraFields[i])) {//以点分割字符串，
                                extraPath = (null == extraPath ? root : extraPath).get(p);
                            }

                            Object extraValue = extraValues[i];
                            if(extraValue == null || extraValue instanceof BaseEntity && StrUtil.isEmpty(((BaseEntity)extraValue).getId())) {
                                predicate = cb.isNull(extraPath);
                            } else {
                                predicate = cb.equal(extraPath, extraValue);
                            }
                            list.add(predicate);
                        }

                        Predicate[] ps = new Predicate[list.size()];
                        return cb.and(list.toArray(ps));
                    };
                    List all = dao.findAll(spec);
                    if(0 < all.size()) {
                        throw new MyException(unique.name() + " [" + value + "] 已存在");
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * 验证时间戳,看数据是否是最新数据，避免使用脏读数据
     * @param t
     */
    public void checkTimestamp(T t) {
        if(StrUtil.isNotEmpty(t.getId())) {
            if(DateUtil.compare(t.getLastModifiedDate(), t.getLastModifiedDate0())!=0) {
                throw new MyException("数据不是最新，请刷新后再操作");
            }
        }
    }
    
    /**
     * 获取entity类型
     * @return
     */
    public Class<T> getDataClass(){
        Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }
}
