package com.example.autotest;

import io.javalin.Javalin;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        }).start(7000);

        app.post("/api/test/open-course", ctx -> {
            ctx.json(runTest("testOpenCourse"));
        });

        app.post("/api/test/forum", ctx -> {
            ctx.json(runTest("testCreateForumThread"));
        });

        app.post("/api/test/lamp", ctx -> {
            ctx.json(runTest("testGiveLampReaction"));
        });

        System.out.println("Server jalan di http://localhost:7000");
    }

    private static Map<String, Object> runTest(String methodName) {
        Map<String, Object> result = new HashMap<>();
        long start = System.currentTimeMillis();

        try {
            LauncherDiscoveryRequest request = request()
                    .selectors(selectMethod(
                            com.example.autotest.tests.LmsTest.class, methodName))
                    .build();

            Launcher launcher = LauncherFactory.create();
            SummaryGeneratingListener listener = new SummaryGeneratingListener();
            launcher.registerTestExecutionListeners(listener);
            launcher.execute(request);

            TestExecutionSummary summary = listener.getSummary();
            long duration = System.currentTimeMillis() - start;

            boolean passed = summary.getTotalFailureCount() == 0 && summary.getTestsSucceededCount() > 0;

            result.put("test", methodName);
            result.put("passed", passed);
            result.put("durationMs", duration);

            if (!passed && summary.getTotalFailureCount() > 0) {
                StringWriter sw = new StringWriter();
                summary.printFailuresTo(new PrintWriter(sw));
                result.put("message", sw.toString());
            } else {
                result.put("message", "OK");
            }

        } catch (Exception e) {
            result.put("test", methodName);
            result.put("passed", false);
            result.put("durationMs", System.currentTimeMillis() - start);
            result.put("message", "Error: " + e.getMessage());
        }

        return result;
    }
}