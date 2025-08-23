package com.solid.principle.lsp.example2.bad;

public class Penguin extends Bird{

    @Override
    public void fly() {
        throw new UnsupportedOperationException();
    }
}
