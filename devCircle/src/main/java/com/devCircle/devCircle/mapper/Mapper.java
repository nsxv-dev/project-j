package com.devCircle.devCircle.mapper;

public interface Mapper<A, B> {
    A mapFrom(B b);

    B mapTo(A a);
}
