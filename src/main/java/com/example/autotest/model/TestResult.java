package com.example.autotest.model;
public record TestResult(String name, boolean passed, long durationMs, String message) {}