package domain.core;

import com.loc.material.api.*;

public class ClassificationApiFactory {
    private static final Http http = new Http();
    private static final ClassificationApi DefaultService = new ClassificationService(new IsbnClient(http), new AuthorClient(http));
    private static ClassificationApi service = DefaultService;

    private ClassificationApiFactory() {}

    public static void setService(ClassificationApi service) {
        ClassificationApiFactory.service = service;
    }

    public static ClassificationApi getService() {
        return service;
    }
}
