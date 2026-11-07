package com.bigbasket.generic;


import java.time.LocalDateTime;

public class JavaUtility {

    public String getSystemTime() {
        return LocalDateTime.now().toString().replace(":", "-");
    }
}
