    package com.example.QuestionsGenerator.Notification;

    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;

    @Target(ElementType.METHOD)  // Target methods only
    @Retention(RetentionPolicy.RUNTIME)  // Keep annotation at runtime
    public @interface SendNotification {
        NotificationType value() default NotificationType.EMAIL;
    }
