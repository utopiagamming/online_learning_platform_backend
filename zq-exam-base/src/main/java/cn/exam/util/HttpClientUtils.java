package cn.exam.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * http请求类
 *
 */
public class HttpClientUtils {

    private static Log log = LogFactory.getLog(HttpClientUtils.class);

    //编码
    protected static final String CHARSET = "utf-8";
    //json数据类型
    protected static final String JSON_CONTENT_TYPE = "application/json";
    //文本数据类型（字符串）
    protected static final String TEXT_CONTENT_TYPE = "text";
    //http成功状态码
    protected static final int SUCCESS_STATUS_CODE = 200;

    private static final int REQUEST_TIMEOUT = 120 * 1000;// 设置请求超时120秒钟
    private static final int SO_TIMEOUT = 180 * 1000; // 设置等待数据超时时间180秒钟

//	/**
//	 * 发送json格式的二进制流
//	 * @param url
//	 * @param jsonParam
//	 * @return
//	 * @throws Exception
//	 */
//	public static String postWithJSON(String url, String jsonParam) throws Exception {
//		HttpPost httpPost = new HttpPost(url);
//		CloseableHttpClient client = HttpClients.createDefault();
//		String respContent = null;
//		StringEntity entity = new StringEntity(jsonParam, CHARSET);//解决中文乱码问题
//		entity.setContentEncoding(CHARSET);
//		entity.setContentType(JSON_CONTENT_TYPE);
//		httpPost.setEntity(entity);
//		HttpResponse resp = client.execute(httpPost);
//		if(resp.getStatusLine().getStatusCode() == SUCCESS_STATUS_CODE) {
//			HttpEntity he = resp.getEntity();
//			respContent = EntityUtils.toString(he, CHARSET);
//		}
//		return respContent;
//	}

    /**
     * 发送字符串的二进制流
     *
     * @param url
     * @param stringParam
     * @return
     * @throws Exception
     */
    public static String postWithString(String url, String stringParam) throws Exception {

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;
        StringEntity entity = new StringEntity(stringParam, CHARSET);//解决中文乱码问题
        entity.setContentEncoding(CHARSET);
        entity.setContentType(TEXT_CONTENT_TYPE);
        httpPost.setEntity(entity);
        HttpResponse resp = client.execute(httpPost);
        if (resp.getStatusLine().getStatusCode() == SUCCESS_STATUS_CODE) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, CHARSET);
        }
        return respContent;
    }


    /**
     * 发送字符串的二进制流
     *
     * @param url
     * @param stringParam
     * @return
     * @throws Exception
     */
    public static String postWithString(String url, String stringParam, String token) throws Exception {

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;
        StringEntity entity = new StringEntity(stringParam, CHARSET);//解决中文乱码问题
        entity.setContentEncoding(CHARSET);
//        entity.setContentType(TEXT_CONTENT_TYPE);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        if (token != null) {
                httpPost.addHeader("token", token);
        }
        HttpResponse resp = client.execute(httpPost);
        if (resp.getStatusLine().getStatusCode() == SUCCESS_STATUS_CODE) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, CHARSET);
        }
        return respContent;
    }

	/**
	 * 上传文件
	 * @param url
	 * @param file
	 * @param params
	 * @return
	 */
	public static String httpClientUploadFile(String url, File file, Map<String, String> params, String FileName) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(FileName, new FileInputStream(file), ContentType.MULTIPART_FORM_DATA, file.getName());// 文件流
            if (params != null) {
				Set<String> keySet = params.keySet();
                ContentType contentType = ContentType.create("text/plain",Charset.forName("UTF-8"));
				for (String key : keySet) {
//					builder.addTextBody(key, params.get(key));// 类似浏览器表单提交，对应input的name和value
                    builder.addTextBody(key, params.get(key), contentType);
				}
			}
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 上传文件
     *
     * @param url
     * @param params
     * @return
     */
    public static String httpClientUploadFile(String url, InputStream stream, String filename, Map<String, String> params, String paramFileName) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(paramFileName, stream, ContentType.MULTIPART_FORM_DATA, filename);//.addBinaryBody(FileName, new FileInputStream(file), ContentType.MULTIPART_FORM_DATA, file.getName());// 文件流
            if (params != null) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    builder.addTextBody(key, params.get(key));// 类似浏览器表单提交，对应input的name和value
                }
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

	/**
	 * post发送对象
	 *
	 * @param url
	 * @param params
	 * @returnhttpClientUploadFile
	 */
	public static byte[] post1(String url, Map<String, String> params) {

		HttpClient client = null;
		PostMethod postMethod = null;
		byte[] result = null;
		try {
			client = new HttpClient();
			HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
			// 设置连接的超时时间
			managerParams.setConnectionTimeout(REQUEST_TIMEOUT);
			// 设置读取数据的超时时间
			managerParams.setSoTimeout(SO_TIMEOUT);
			postMethod = new PostMethod(url);
			// 解决中文乱码问题
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			if (params != null) {
				Set<String> keySet = params.keySet();
				for (String key : keySet) {
					postMethod.addParameter(key, params.get(key));
				}
			}
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
//				result = postMethod.getResponseBody();
				InputStream input = postMethod.getResponseBodyAsStream();
				if(input != null){
					result = input2byte(input);
				}
			}
		} catch (HttpException e) {
			log.error("HttpClient-PostMethod-HttpException", e);
		} catch (IOException e) {
			log.error("HttpClient-PostMethod-IOException", e);
		} catch (Exception e) {
			log.error("HttpClient-PostMethod-Exception", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return result;
	}

//	public static ByteArrayOutputStream post2ByteArrayOutputStream(String url, Map<String, String> params) {
//
//		HttpClient client = null;
//		PostMethod postMethod = null;
//		ByteArrayOutputStream result = null;
//		try {
//			client = new HttpClient();
//			HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
//			// 设置连接的超时时间
//			managerParams.setConnectionTimeout(REQUEST_TIMEOUT);
//			// 设置读取数据的超时时间
//			managerParams.setSoTimeout(SO_TIMEOUT);
//			postMethod = new PostMethod(url);
//			// 解决中文乱码问题
//			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//			if (params != null) {
//				Set<String> keySet = params.keySet();
//				for (String key : keySet) {
//					postMethod.addParameter(key, params.get(key));
//				}
//			}
//			int statusCode = client.executeMethod(postMethod);
//			if (statusCode == HttpStatus.SC_OK) {
//				InputStream input = postMethod.getResponseBodyAsStream();
//				if(input != null){
//					result = input2ByteArrayOutputStream(input);
//				}
//			}
//		} catch (HttpException e) {
//			log.error("HttpClient-PostMethod-HttpException", e);
//		} catch (IOException e) {
//			log.error("HttpClient-PostMethod-IOException", e);
//		} catch (Exception e) {
//			log.error("HttpClient-PostMethod-Exception", e);
//		} finally {
//			if (postMethod != null) {
//				postMethod.releaseConnection();
//			}
//		}
//		return result;
//	}

    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = input2ByteArrayOutputStream(inStream);
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static final ByteArrayOutputStream input2ByteArrayOutputStream(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream;
    }

    /**
     * post发送对象
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, String> params) {

        HttpClient client = null;
        PostMethod postMethod = null;
        String result = null;
        try {
            client = new HttpClient();
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            // 设置连接的超时时间
            managerParams.setConnectionTimeout(REQUEST_TIMEOUT);
            // 设置读取数据的超时时间
            managerParams.setSoTimeout(SO_TIMEOUT);
            postMethod = new PostMethod(url);
            //解决中文乱码问题
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            if (params != null) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    postMethod.addParameter(key, params.get(key));
                }
            }
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
//            	result = new String(postMethod.getResponseBody(), "utf-8");
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                result = new String(stringBuffer);
                if (inputStream != null)
                    inputStream.close();
            }
        } catch (HttpException e) {
            log.error("HttpClient-PostMethod-HttpException", e);
        } catch (IOException e) {
            log.error("HttpClient-PostMethod-IOException", e);
        } catch (Exception e) {
            log.error("HttpClient-PostMethod-Exception", e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return result;
    }

//    /**
//     * post发送对象,带请求头
//     *
//     * @param url
//     * @param params
//     * @return
//     */
//    public static String post(String url, Map<String, String> params, List<Header> headers) {
//
//        HttpClient client = null;
//        PostMethod postMethod = null;
//        String result = null;
//        try {
//            client = new HttpClient();
//            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
//            // 设置连接的超时时间
//            managerParams.setConnectionTimeout(REQUEST_TIMEOUT);
//            // 设置读取数据的超时时间
//            managerParams.setSoTimeout(SO_TIMEOUT);
//            postMethod = new PostMethod(url);
//            // 解决中文乱码问题
//            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//            //设置请求头
//            if( headers != null ){
//                for(Header header : headers){
//                    postMethod.setRequestHeader(header);
//                }
//            }
//            //设置请求参数
//            if (params != null) {
//                Set<String> keySet = params.keySet();
//                for (String key : keySet) {
//                    postMethod.addParameter(key, params.get(key));
//                }
//            }
//            //请求数据
//            int statusCode = client.executeMethod(postMethod);
//            if (statusCode == HttpStatus.SC_OK) {
//                result = new String(postMethod.getResponseBody(), "utf-8");
//            }
//        } catch (HttpException e) {
//            log.error("HttpClient-PostMethod-HttpException", e);
//        } catch (IOException e) {
//            log.error("HttpClient-PostMethod-IOException", e);
//        } catch (Exception e) {
//            log.error("HttpClient-PostMethod-Exception", e);
//        } finally {
//            if (postMethod != null) {
//                postMethod.releaseConnection();
//            }
//        }
//        return result;
//    }

}
