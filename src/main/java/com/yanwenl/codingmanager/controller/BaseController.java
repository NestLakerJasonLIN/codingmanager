package com.yanwenl.codingmanager.controller;

import org.springframework.beans.factory.annotation.Value;

public abstract class BaseController {
    @Value("${info.app.name}")
    String appName;
}
