package com.example.demo.util;

import org.dom4j.io.DocumentResult;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * JAXB工具类
 */
public final class JAXBUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JAXBUtils.class);

    /**
     * JAXBContext Map
     */
    private static ConcurrentMap<String, JAXBContext> jaxbContextMap = new ConcurrentHashMap<String, JAXBContext>();

    private JAXBUtils() {

    }

    /**
     * put JAXBContext into Map
     *
     * @param clazz Class
     */
    public static void putJAXBContext(Class clazz) {
        try {
            String name = clazz.getName();
            JAXBContext jaxbContext = jaxbContextMap.get(name);
            if (jaxbContext == null) {
                jaxbContextMap.put(name, JAXBContext.newInstance(clazz));
            }
        }
        catch (JAXBException e) {
            LOG.error("create jaxb context error!", e);
        }
    }

    /**
     * put JAXBContext into Map
     *
     * @param classes Class
     */
    public static void putJAXBContext(Class... classes) {
        for (Class clazz : classes) {
            putJAXBContext(clazz);
        }
    }

    /**
     * 对象转XML
     *
     * @param t   参数
     * @param <T> 泛型
     * @return 字符串
     */
    public static <T> String marshall(T t) {
        StringBuilder xml = new StringBuilder();
        try {
            String key = t.getClass().getName();
            JAXBContext jaxbContext = jaxbContextMap.get(key);
            if (jaxbContext == null) {
                jaxbContext = JAXBContext.newInstance(t.getClass());
                jaxbContextMap.put(t.getClass().getName(), jaxbContext);
            }
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            DocumentResult dr = new DocumentResult();
            jaxbMarshaller.marshal(t, dr);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setExpandEmptyElements(true);
            StringWriter stringWriter = new StringWriter();
            XMLWriter writer = new XMLWriter(stringWriter, format);
            writer.write(dr.getDocument());
            xml.append(stringWriter.getBuffer());
        }
        catch (Exception e) {
            LogUtils.error("marshall object error", e);
        }

        return xml.toString();
    }

    /**
     * XML转对象
     *
     * @param xml     xml文件
     * @param clazz   泛型类
     * @param <T>     泛型
     * @return T
     * @throws JAXBException
     */
    public static <T> T unMarshall(String xml, Class<T> clazz) {
        String key = clazz.getName();
        JAXBContext jaxbContext = jaxbContextMap.get(key);
        try {
            if (jaxbContext == null) {
                jaxbContext = JAXBContext.newInstance(clazz);
                jaxbContextMap.put(clazz.getName(), jaxbContext);
            }
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setListener(new JaxbUnmarshallerListener());
            return (T) (jaxbUnmarshaller.unmarshal(new StringReader(xml)));
        }
        catch (JAXBException e) {
            LogUtils.error("unMarshal XML error", e);
        }
        return null;
    }

    /**
     * XML转对象
     *
     * @param is      输入流
     * @param clazz   类的泛型
     * @param handler 校验事件处理者
     * @param <T>     类的泛型
     * @return .
     * @throws JAXBException
     */
    public static <T> T unMarshall(InputStream is, Class<T> clazz, ValidationEventHandler handler) {
        String key = clazz.getName();
        JAXBContext jaxbContext = jaxbContextMap.get(key);
        try {
            if (jaxbContext == null) {
                jaxbContext = JAXBContext.newInstance(clazz);
                jaxbContextMap.put(clazz.getName(), jaxbContext);
            }
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            if (handler != null) {
                jaxbUnmarshaller.setEventHandler(handler);
            }
            jaxbUnmarshaller.setListener(new JaxbUnmarshallerListener());
            return (T) (jaxbUnmarshaller.unmarshal(is));
        }
        catch (JAXBException e) {
            LogUtils.error("unMarshal XML error", e);
        }
        return null;
    }

}
