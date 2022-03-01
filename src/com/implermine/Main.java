package com.implermine;

import java.lang.reflect.*;

public class Main {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Class reflectClass = ServiceImpl.class;

        String className = reflectClass.getName();

        System.out.println("className = " + className);

        int modifiers = reflectClass.getModifiers();

        System.out.println("modifiers = " + modifiers);

        // isAbstract, isFinal, isInterface, isPrivate, isProtected
        // isStatic, isStrict, isSynchronized, isVolatile

        System.out.println("isModifier public? = " + Modifier.isPublic(modifiers));
        System.out.println("isModifier private? = " + Modifier.isPrivate(modifiers));

        Class[] interfaces = reflectClass.getInterfaces();
        for (Class anInterface : interfaces) {
            System.out.println("anInterface.getName() = " + anInterface.getName());
        }

        Class superclass = reflectClass.getSuperclass();
        System.out.println("superclass.getName() = " + superclass.getName());

        Method[] methods = reflectClass.getDeclaredMethods();

        System.out.println("===========================================");

        for (Method method : methods) {
            System.out.println();
            System.out.println("method.getName() = " + method.getName());
            if (method.getName().startsWith("set")) {
                System.out.println("Setter Method");
            } else if (method.getName().startsWith("get")) {
                System.out.println("Getter Method");
            }
            System.out.println("return Type: " + method.getReturnType());

            Class[] parameterTypes = method.getParameterTypes();
            System.out.println("Parameters");
            for (Class parameterType : parameterTypes) {
                System.out.println(parameterType.getName());
            }
        }
        // 파라미터로 constructor를 구분하는 모습
        Constructor constructor1 = reflectClass.getConstructor(new Class[]{Repository.class});
        Object obj1 = reflectClass.getConstructor(int.class, String.class).newInstance(12, "Random String");
        Constructor constructor2 = reflectClass.getConstructor(new Class[]{int.class, String.class});

        Class[] constructParameters = constructor1.getParameterTypes();
        System.out.println("====================================================");
        System.out.println("constructParameters");
        System.out.println("=====================================================");

        for (Class constructParameter : constructParameters) {
            System.out.println(constructParameter.getName());
        }

        ServiceImpl newServiceImpl = null;
        Repository repository = null;

        // 생성자를 찾아 해당 생성자를 사용하는 모습
        System.out.println("=======================================");
        System.out.println("생성자를 찾아 해당 생성자를 사용하는 모습");
        newServiceImpl = (ServiceImpl) constructor1.newInstance(repository);

        newServiceImpl.setIdCode("333");

        newServiceImpl.setHeight("hello");

        System.out.println("name = " + newServiceImpl.getHeight());

        System.out.println("======================================");
        System.out.println("Field 관련");

        Field privateStringName = null;

        ServiceImpl service = new ServiceImpl(repository);

        String idCodeString = "idCode";
        privateStringName = ServiceImpl.class.getDeclaredField(idCodeString);

        // access modifier 수정
        privateStringName.setAccessible(true);

        // 어떤 필드인지는 알았음, 근데 그 필드를 어떤 obj에서 뽑아낼건데? 라서 paramter가 obj
        String valueOfName = (String) privateStringName.get(service);

        // access가 뚫린 모습
        System.out.println("Private Field value = " + valueOfName);

        String methodName = "getPrivate";

        // getMethod와 getDeclaredMethod가 차이가 있다. 아무래도 getDeclaredMethod는 private까지 다 불러오나?
        // 그렇다, 그리고 상속받은 메서드들은 안보여준다. object에게 상속받은 wait 같은 메서드가 없다.
        Method method = ServiceImpl.class.getDeclaredMethod(methodName);
        method.setAccessible(true);

        // access modifier를 뚫고 실행한 모습

        Class<?> returnType = method.getReturnType();

        Object invoke = method.invoke(service);

        Boolean bool = returnType.cast(invoke) instanceof String;
        System.out.println(returnType.cast(invoke).getClass());

        // 이게 트루여야 하는데
        System.out.println(bool);

        System.out.println("invoke = " + invoke);

        //
        System.out.println("===========================");
        //파라미터가 있는 메서드 호출
        // Integer.TYPE은 primitive를 지칭
        Class[] methodParameterTypes = {Integer.TYPE, String.class};

        Object[] params = {new Integer(10), new String("Randdddd")};

        //찾을 때 메서드명이랑 파라미터 타입들도 지정해야 됌
        Method method2 = ServiceImpl.class.getDeclaredMethod("getOtherPrivate", methodParameterTypes);

        method2.setAccessible(true);

        Object invoke1 = (String)method2.invoke(service, params);

        System.out.println("invoke1 = " + invoke1);
    }
}
