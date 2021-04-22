package com.beltram.sleuthgreenwichreproducer;

import java.util.List;

public class SpanWrapper {

    private List<String> spans;

    public String add(String span) {
        spans.add(span);
        return span;
    }

    public List<String> getSpans() {
        return spans;
    }

    public SpanWrapper setSpans(List<String> spans) {
        this.spans = spans;
        return this;
    }
}
