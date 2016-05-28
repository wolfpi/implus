package com.baidu.imc.impl.im.protocol;

public enum ServiceNameEnum {

    NULL(""), IM_PLUS_MSG("IMPlusMsg"), IM_PLUS_FILE("IMPlusFile"), IM_PLUS_CHAT_SETTING("IMPlusChatSetting");

    private String name;

    ServiceNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ServiceNameEnum parse(String name) {
        ServiceNameEnum[] serviceNames = ServiceNameEnum.values();
        if (null != serviceNames) {
            for (ServiceNameEnum serviceName : serviceNames) {
                if (serviceName.getName().equals(name)) {
                    return serviceName;
                }
            }
        }
        return NULL;
    }
}
