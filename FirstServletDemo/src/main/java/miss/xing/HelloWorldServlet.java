
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

public class CustomRestTemplateExample {A

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate(new CustomClientHttpRequestFactory());

        // Set the request headers (optional)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer your_token_here");

        // Set the request body (if required)
        String requestBody = "{ \"key\": \"value\" }";

        // Create the HttpEntity object with the headers and body
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

        public CustomClientHttpRequest(URI uri, HttpMethod httpMethod) {
            super(uri);
            this.httpMethod = httpMethod;
        }

        @Override
        public HttpMethod getMethod() {
            return this.httpMethod;
        }
    }
}
