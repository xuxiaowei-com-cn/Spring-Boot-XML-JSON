package cn.com.xuxiaowei.util;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * XML 与 JSON 转换工具类
 * <p>
 * <!-- https://mvnrepository.com/artifact/dom4j/dom4j -->
 * <dependency>
 * <groupId>dom4j</groupId>
 * <artifactId>dom4j</artifactId>
 * <version>1.6.1</version>
 * </dependency>
 * <p>
 * <!--<dependency>
 * <groupId>net.sf.json-lib</groupId>
 * <artifactId>json-lib</artifactId>
 * <version>2.2.2</version>
 * <classifier>jdk15</classifier>
 * </dependency>-->
 * <p>
 * <!-- https://mvnrepository.com/artifact/net.sf.json-lib/json-lib -->
 * <dependency>
 * <groupId>net.sf.json-lib</groupId>
 * <artifactId>json-lib</artifactId>
 * <version>2.4</version>
 * <classifier>jdk15</classifier>
 * </dependency>
 * <p>
 * <!-- https://mvnrepository.com/artifact/nu.xom/com.springsource.nu.xom -->
 * <dependency>
 * <groupId>nu.xom</groupId>
 * <artifactId>com.springsource.nu.xom</artifactId>
 * <version>1.2.5</version>
 * </dependency>
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class JsonXmlUtils {

    /**
     * XML 转 JSON
     *
     * @param xml XML String
     * @return 返回 String 类型的 JSON
     */
    public static String xmlToJson(String xml) {

        XMLSerializer xmlSerializer = new XMLSerializer();

        JSON read = xmlSerializer.read(xml);

        return read.toString();
    }

    /**
     * XML 转 JSON
     *
     * @param xml      XML String
     * @param elements 去除的父节点
     * @return 返回 String 类型的 JSON
     * @throws DocumentException XML 转换失败
     */
    public static String xmlToJsonElements(String xml, String... elements) throws DocumentException {

        if (elements.length == 0) {

            return xmlToJson(xml);

        } else {

            Document document = DocumentHelper.parseText(xml);

            Element element = document.getRootElement();

            for (String e : elements) {
                element = element.element(e);
            }

            String elementXml = element.asXML();

            return xmlToJson(elementXml);
        }
    }

    /**
     * JSON 转 XML
     *
     * @param json             JSON String
     * @param typeHintsEnabled 是否开启类型说明，默认开启
     * @return 返回 String 类型的 XML
     */
    public static String jsonToXml(String json, boolean typeHintsEnabled) {

        XMLSerializer xmlSerializer = new XMLSerializer();

        xmlSerializer.setTypeHintsEnabled(typeHintsEnabled);

        JSONObject jsonObject = JSONObject.fromObject(json);

        return xmlSerializer.write(jsonObject);
    }

    /**
     * JSON 转 XML(暂未完成)
     *
     * @param json             JSON String
     * @param typeHintsEnabled 是否开启类型说明，默认开启
     * @param rootNames        要添加的父节点
     * @return 返回 String 类型的 XML
     */
    public static String jsonToXmlRootNames(String json, boolean typeHintsEnabled, String... rootNames) {

        if (rootNames.length == 0) {

            return jsonToXml(json, typeHintsEnabled);

        } else {

            XMLSerializer xmlSerializer = new XMLSerializer();

            xmlSerializer.setTypeHintsEnabled(typeHintsEnabled);

            xmlSerializer.setRootName("a");

            xmlSerializer.setNamespace("a-p", "http://www.xuxiaowei.com.com");


            for (String rootName : rootNames) {

            }

            JSONObject jsonObject = JSONObject.fromObject(json);

            return xmlSerializer.write(jsonObject);
        }
    }

    public static void main(String[] args) throws DocumentException {
        System.out.println("JSON 转 XML：\t" + jsonToXmlTest());
        System.out.println("JSON 转 XML2：\t" + jsonToXmlRootNamesTest());
        System.out.println("XML 转 JSON：\t" + xmlToJsonTest());
        System.out.println("XML 转 JSON2：\t" + xmlToJsonElementsTest());
    }

    /**
     * 测试 JSON 转 XML
     */
    private static String jsonToXmlTest() {
        UserTest user = new UserTest();
        user.setId(1);
        user.setUsername("xxw");
        user.setPassword("123");
        JSONObject jsonObject = JSONObject.fromObject(user);
        String string = jsonObject.toString();
        return jsonToXml(string, false);
    }

    /**
     * 测试 JSON 转 XML
     */
    private static String jsonToXmlRootNamesTest() {
        UserTest user = new UserTest();
        user.setId(1);
        user.setUsername("xxw");
        user.setPassword("123");
        JSONObject jsonObject = JSONObject.fromObject(user);
        String string = jsonObject.toString();
        return jsonToXmlRootNames(string, false, "C", "B", "A");
    }

    /**
     * 测试 XML 转 JSON
     */
    private static String xmlToJsonTest() {
        return xmlToJson(jsonToXmlTest());
    }

    /**
     * 测试 XML 转 JSON
     */
    private static String xmlToJsonElementsTest() throws DocumentException {
        String xml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soap:Body>\n" +
                "      <ns2:selectByIdResponse xmlns:ns2=\"http://service.xuxiaowei.com.cn/\">\n" +
                "         <return>\n" +
                "            <id>1</id>\n" +
                "            <password>123</password>\n" +
                "            <username>xxw</username>\n" +
                "         </return>\n" +
                "      </ns2:selectByIdResponse>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";

        return xmlToJsonElements(xml, "Body", "selectByIdResponse", "return");
    }

    /**
     * 测试 XML 与 JSON 互转的类
     *
     * @author xuxiaowei
     */
    public static class UserTest {

        private int id;

        private String username;

        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

}
