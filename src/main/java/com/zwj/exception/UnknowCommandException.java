package com.zwj.exception;

/**
 * @author: zwj
 * @Date: 12/6/18
 * @Time: 3:07 PM
 * @description:
 */
public class UnknowCommandException extends Exception {

    private final String message = "无法识别的指令!";

    public UnknowCommandException() {
    }


}
