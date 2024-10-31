package util;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestUtil {
    private RestUtil() {}

    public static RestTemplate createRestTemplate() {
        RestTemplate template = new RestTemplate();

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setPrettyPrint(false);
        template.getMessageConverters()
                .removeIf(m -> MappingJackson2HttpMessageConverter.class.isAssignableFrom(m.getClass()));
        template.getMessageConverters().add(messageConverter);

        return template;
    }
}
