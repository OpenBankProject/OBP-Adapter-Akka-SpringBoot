package com.openbankproject.akka.springboot.adapter.actor;


import akka.actor.UntypedActor;

public class ResultActor extends UntypedActor {


    @Override
    public void onReceive(Object message) {
        System.out.println("接收到：message = [" + message + "]");
    }
}