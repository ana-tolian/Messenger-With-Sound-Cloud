package com.example.site.data;

public interface Repository<T> {
    T save(T t);
    T update(T t);
}
