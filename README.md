# Spring Sleuth 3.0.2 with spring-data reproducer

This samples an issue appearing in Sleuth 3.0.2 (3.0.1 is fine).  
When you use a reactive repository, next Publisher looses tracing context (traceId, spanId).

## to reproduce

```bash
./gradlew test

# you should see
SpanTest > find many spans with mongo() FAILED
    java.lang.AssertionError at SpanTest.kt:48
```