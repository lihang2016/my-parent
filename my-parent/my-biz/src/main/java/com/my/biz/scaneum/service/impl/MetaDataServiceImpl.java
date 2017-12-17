package com.my.biz.scaneum.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.google.common.collect.Lists;
import com.my.biz.common.udc.domain.entity.UDCType;
import com.my.biz.scaneum.dto.EntityInfoDto;
import com.my.biz.scaneum.dto.EnumListDto;
import com.my.biz.scaneum.dto.FieldInfoDto;
import com.my.biz.scaneum.exception.Exceptions;
import com.my.biz.scaneum.service.MetaDataService;
import com.my.biz.scaneum.support.Flow;
import com.my.biz.scaneum.support.Reference;
import com.my.biz.scaneum.util.Reflections;
import com.my.common.mapper.filter.SearchFilter;
import com.my.common.mapper.pages.data.DatabaseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:lihang
 * @Description:枚举扫描类
 * @Date Create in 16:21 2017/10/30
 */
@Service
@Slf4j
public class MetaDataServiceImpl implements MetaDataService {
    private static final String CLASS_RESOURCE_PATTERN = "/**/*.class";

    /**
     * 枚举实例缓存
     */
    private Map<String, List<Enum>> enumInstanceMap = new ConcurrentHashMap<>();
    /**
     * 工作流实体元数据缓存
     */
    private Map<String, List<EntityInfoDto>> flowEntityMap = new ConcurrentHashMap<>();


    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private static final Set<TypeFilter> entityFlowTypeFilters;
    private static final Set<TypeFilter> enumTypeFilters;

    static {
        entityFlowTypeFilters = new LinkedHashSet<>(1);
        entityFlowTypeFilters.add(new AnnotationTypeFilter(Flow.class, true));
        enumTypeFilters = new LinkedHashSet<>(1);
        enumTypeFilters.add(new AssignableTypeFilter(Enum.class));
    }

    /**
     * 获取单个实体元数据信息
     *
     * @param className 类全路径
     * @return 实体字段的元数据信息
     */
    @Override
    public List<FieldInfoDto> getEntityMetaDataByName(String className) {
        List<Class> classList = new ArrayList<>();
        try {
            Class aClass = Class.forName(className);
            classList.add(aClass);
            return getEntityMetaDataByClass(classList).get(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("未找到类:" + className);
            return null;
        }
    }

    /**
     * 通过反射获取实体元数据信息
     *
     * @param classList 传入格式 :  [com.zds.boot.City.class,com.zds.boot.Customer.class]
     * @return 以Class为key, Class字段元数据信息为list的map
     */
    public Map<Class, List<FieldInfoDto>> getEntityMetaDataByClass(List<Class> classList) {
        checkIsEntity(classList);
        Map<Class, List<FieldInfoDto>> classListMap = new HashMap<>();
        for (Class aClass : classList) {
            List<FieldInfoDto> fieldInfoDtoList = new ArrayList<>();
            Set<Field> fieldSet = Reflections.getFields(aClass);
            for (Field field : fieldSet) {
                FieldInfoDto fieldInfoDto = new FieldInfoDto();
                fieldInfoDto.setFieldName(field.getName());
                int index = field.getType().toString().lastIndexOf(".");
                fieldInfoDto.setFieldType(field.getType().toString().substring(index + 1));
                //设置组名 方案id
                Reference reference = field.getDeclaredAnnotation(Reference.class);
                if (reference != null) {
                    fieldInfoDto.setGroupName(reference.groupName());
                    fieldInfoDto.setSchemeId(reference.schemeId());
                }
                //设置字段中文名
                Column column = field.getDeclaredAnnotation(Column.class);
                if (column != null) {
                    String columnDefinition = column.columnDefinition();
                    if (((columnDefinition == null) || (columnDefinition.length() == 0))) {
                        int commentIndex = columnDefinition.indexOf("COMMENT") == -1 ? columnDefinition.indexOf("comment") : columnDefinition.indexOf("COMMENT");
                        if (commentIndex != -1) {
                            String commentString = columnDefinition.substring(commentIndex);
                            fieldInfoDto.setDisplayName(commentString.substring(commentString.indexOf('\'') + 1, commentString.lastIndexOf("\'")));
                        }
                    }
                }
                fieldInfoDtoList.add(fieldInfoDto);
            }
            classListMap.put(aClass, fieldInfoDtoList);
        }
        return classListMap;
    }


    /**
     * @return 实体@Flow注解上标明的模块名set
     */
    @Override
    public Set<String> getFlowModuleList() {
        return flowEntityMap.keySet();
    }

    /**
     * 根据moduleName返回相应实体元数据信息
     *
     * @param moduleName 实体@Flow注解上标明的模块名
     * @return
     */
    @Override
    public List<EntityInfoDto> getEntityInfoByModuleName(String moduleName) {
        return flowEntityMap.get(moduleName);
    }

    /**
     * 扫描所有带@Flow注解的实体,并获取实体元数据信息
     * <p>
     * 以实体@Flow注解上标明的模块名为key,实体元数据为list的map
     * </p>
     */
    public void scanFlowEntity() {
        if (flowEntityMap == null || flowEntityMap.isEmpty()) {
            flowEntityMap = new HashMap<>();
            String packagesToScan[] = {"com.example.**.entity"};
            if (packagesToScan != null && packagesToScan.length > 0) {
                for (String pkg : packagesToScan) {
                    try {
                        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
                        for (Resource resource : geResources(pkg)) {
                            if (resource.isReadable()) {
                                MetadataReader reader = readerFactory.getMetadataReader(resource);
                                String className = reader.getClassMetadata().getClassName();
                                if (matchesFilter(reader, readerFactory, entityFlowTypeFilters)) {
                                    Class aClass = Class.forName(className);
                                    Flow flow = (Flow) aClass.getDeclaredAnnotation(Flow.class);
                                    if (flow != null) {
                                        List<EntityInfoDto> list = flowEntityMap.get(flow.moduleName());
                                        if (list == null) {
                                            list = new ArrayList<>();
                                            flowEntityMap.put(flow.moduleName(), list);
                                        }
                                        EntityInfoDto entityInfoDto = new EntityInfoDto();
                                        entityInfoDto.setEntityFullName(className);
                                        entityInfoDto.setEntityDisplayName(flow.displayName());
                                        entityInfoDto.setEntityModuleName(flow.moduleName());
                                        list.add(entityInfoDto);
                                    }
                                }
                            }
                        }
                    } catch (IOException ex) {
                        throw new PersistenceException("Failed to scan classpath for unlisted entity classes", ex);
                    } catch (ClassNotFoundException e) {
                        throw Exceptions.runtimeException("未扫描到工作流相关实体类", e);
                    }
                }
            }
        }
    }


    /**
     * 根据单个枚举类名,获取该枚举的所有实例
     *
     * @param classSimpleName 例： CustomerType
     * @return
     */
    @Override
    public List<Enum> getByEnumClassSimpleName(String classSimpleName) {
        return enumInstanceMap.get(classSimpleName);
    }

    /**
     * 根据多个枚举类名,获取枚举的所有实例
     *
     * @param simpleNameList
     * @return EnumDto集合
     */
    public List<EnumListDto> getByEnumClassSimpleName(List<String> simpleNameList) {
        List<EnumListDto> enumListDtoList = new ArrayList<>();
        simpleNameList.forEach(simpleName -> {
            EnumListDto enumListDto = new EnumListDto();
            enumListDto.setSimpleName(simpleName);
            enumListDto.setEnumsList(enumInstanceMap.get(simpleName));
            enumListDtoList.add(enumListDto);
        });
        return enumListDtoList;
    }

    /**
     * 扫描类修饰符为public的枚举类
     * <p>
     * 枚举类名为key,枚举实例为list的map
     * </p>
     */
    @Override
    public void scanAllEnum() {
        if (enumInstanceMap == null || enumInstanceMap.isEmpty()) {
            enumInstanceMap = new HashMap<>();
            List<String> packageList = Lists.newArrayList("com.my.biz");
//            if(!Apps.getBasePackage().contains(Apps.COMPONENTS_PACKAGE)){
//                packageList.add(Apps.getBasePackage());
//            }
            log.info("扫描枚举开始");
            for (String pkg : packageList) {
                try {
                    for (Resource resource : geResources(pkg)) {
                        if (resource.isReadable()) {
                            MetadataReader reader = getMetadataReaderFactory().getMetadataReader(resource);
                            String className = reader.getClassMetadata().getClassName();
                            if (matchesFilter(reader, getMetadataReaderFactory(), enumTypeFilters)) {
                                Class aClass = Class.forName(className);
                                //排除private的枚举
                                if (Modifier.isPublic(aClass.getModifiers())) {
                                    Method method = aClass.getMethod("values");
                                    Enum inter[] = (Enum[]) method.invoke(null, null);
                                    List<Enum> enumList = new ArrayList<>();
                                    for (Enum enumMessage : inter) {
                                        enumList.add(enumMessage);
                                    }
                                    if (filterEnum(aClass)) {
                                        if (enumInstanceMap.containsKey(aClass.getSimpleName())) {
                                            throw Exceptions.runtimeException("存在类名相同的枚举,请修改:" + aClass.getSimpleName());
                                        }
                                        enumInstanceMap.put(aClass.getSimpleName(), enumList);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    throw Exceptions.runtimeException("扫描所有枚举类型出错", ex);
                }
            }
            log.info("扫描枚举结束");
        }
    }

    private static Boolean filterEnum(Class<? extends Enum> enumClass) {
        if (enumClass == SearchFilter.Operator.class) {
            return false;
        } else if (enumClass == UDCType.Category.class) {
            return false;
        } else if (enumClass == DatabaseType.class) {
            return false;
        }

        return true;
    }

    private static String toJson(Class<? extends Enum> enumClass) {
        Method methodValues = null;
        try {
            methodValues = enumClass.getMethod("values");
            Object invoke = methodValues.invoke(null);

            int length = java.lang.reflect.Array.getLength(invoke);
            List<Object> values = new ArrayList<Object>();
            for (int i = 0; i < length; i++) {
                values.add(java.lang.reflect.Array.get(invoke, i));
            }

            SerializeConfig config = new SerializeConfig();
            config.configEnumAsJavaBean(enumClass);
            return JSON.toJSONString(values, config);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验传入的是不是实体类型
     *
     * @param classList
     */
    private void checkIsEntity(List<Class> classList) {
        classList.forEach(aClass -> {
            Entity entity = (Entity) aClass.getAnnotation(Entity.class);
            if (entity == null) {
                throw Exceptions.runtimeException("传入的Class必须标注@Entity注解");
            }
        });
    }

    /**
     * 解析资源路径
     *
     * @param pkg 指定包名
     * @return
     */
    private Resource[] geResources(String pkg) {
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(pkg) + CLASS_RESOURCE_PATTERN;
        try {
            return resourcePatternResolver.getResources(pattern);
        } catch (IOException e) {
            throw new PersistenceException("Failed to scan classpath for unlisted entity classes", e);
        }
    }

    private MetadataReaderFactory getMetadataReaderFactory() {
        return new CachingMetadataReaderFactory(this.resourcePatternResolver);
    }

    private boolean matchesFilter(MetadataReader reader, MetadataReaderFactory readerFactory, Set<TypeFilter> typeFilters) throws IOException {
        for (TypeFilter filter : typeFilters) {
            if (filter.match(reader, readerFactory)) {
                return true;
            }
        }
        return false;
    }


}
