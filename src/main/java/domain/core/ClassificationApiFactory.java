package domain.core;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.ClassificationService;
import com.loc.material.api.Http;
import com.loc.material.api.IsbnClient;

public class ClassificationApiFactory {
    private static final ClassificationApi DefaultService = new ClassificationService(new IsbnClient(new Http()));
    private static ClassificationApi service = DefaultService;

    private ClassificationApiFactory() {}

    public static void setService(ClassificationApi service) {
        ClassificationApiFactory.service = service;
    }

    public static ClassificationApi getService() {
        return service;
    }
}
