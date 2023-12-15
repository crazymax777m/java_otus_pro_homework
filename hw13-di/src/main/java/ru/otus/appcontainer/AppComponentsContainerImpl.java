package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        try {
            Object configObject = configClass.getConstructor().newInstance();

            Method[] methods = configClass.getDeclaredMethods();
            Method[] sortedMethods = getSortedMethods(methods);

            for (Method method : sortedMethods) {
                Object classObject = getClassObject(configObject, method);

                appComponents.add(classObject);
                appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), classObject);
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception);
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {

        for (Object classObject : appComponents)
        {
            if (classObject.getClass().equals(componentClass)
                    || Arrays.stream(classObject.getClass().getInterfaces()).toList().contains(componentClass))
                return (C)classObject;
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C)appComponentsByName.get(componentName);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object getClassObject(Object configObject, Method method) throws IllegalAccessException, InvocationTargetException {
        Object classObject;
        if (method.getParameterTypes().length == 0) {
            classObject = method.invoke(configObject);
        }
        else {
            List<Object> methodParameters = new ArrayList<>();
            for (Class<?> clazz : method.getParameterTypes()) {
                methodParameters.add(getAppComponent(clazz));
            }
            classObject = method.invoke(configObject, methodParameters.toArray());
        }
        return classObject;
    }

    private Method[] getSortedMethods(Method[] methods) {
        Method[] sortedMethods = Arrays.stream(methods).filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(new Comparator<>() {
                    @Override
                    public int compare(Method o1, Method o2) {
                        return Integer.compare(o1.getAnnotation(AppComponent.class).order(), o2.getAnnotation(AppComponent.class).order());
                    }
                }).toArray(Method[]::new);
        return sortedMethods;
    }
}
