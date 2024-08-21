
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import y
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Define the URL of the resource
        String url = "https://your-api-endpoint.com/resource";

        // Make the GET request with the requestEntity
        String response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();

        System.out.println(response);
    }

    // Custom SimpleClientHttpRequestFactory
    static class CustomClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

        @Override
        protected ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
            if (httpMethod == HttpMethod.GET) {
                return new CustomClientHttpRequest(uri, httpMethod);
            }
            return super.createRequest(uri, httpMethod);
        }
    }

    // Custom ClientHttpRequest to handle GET requests with a body
    static class CustomClientHttpRequest extends org.springframework.http.client.SimpleClientHttpRequest {

        private final HttpMethod httpMethod;

    
}

===================================================================================================



    import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class RestTemplateExample {

    public static void main(String[] args) throws Exception {
        // Create an SSLContext that trusts all certificates
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial((chain, authType) -> true) // Trust all certificates
                .build();

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                NoopHostnameVerifier.INSTANCE
        );

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .setDefaultRequestConfig(RequestConfig.custom().build())
                .build();

        // Create HttpComponentsClientHttpRequestFactory
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        // Create RestTemplate with the custom request factory
        RestTemplate restTemplate = new RestTemplate(factory);

        // Make a GET request
        String url = "https://your-api-endpoint.com/resource";
        String response = restTemplate.getForObject(url, String.class);

        System.out.println(response);
    }
}

