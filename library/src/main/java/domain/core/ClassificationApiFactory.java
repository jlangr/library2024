package domain.core;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.ClassificationService;

public class ClassificationApiFactory {
    private static final ClassificationApi DefaultService = new ClassificationService();
    private static ClassificationApi service = DefaultService;

    private ClassificationApiFactory() {}

    public static void setService(ClassificationApi service) {
        ClassificationApiFactory.service = service;
    }

    public static ClassificationApi getService() {
        return service;
    }
}
