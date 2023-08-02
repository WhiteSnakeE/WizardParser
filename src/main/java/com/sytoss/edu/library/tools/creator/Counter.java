package com.sytoss.edu.library.tools.creator;

import org.springframework.stereotype.Component;

@Component
public class Counter {

    int a = 1;

    public int count(){
        a++;
        return a;
    }
}
