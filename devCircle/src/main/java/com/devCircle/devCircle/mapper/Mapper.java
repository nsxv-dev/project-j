package com.devCircle.devCircle.mapper;

public interface Mapper<A, B> {
    A toEntity(B b);

    B toDto(A a);
}
