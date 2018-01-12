package com.a31r.sport.core.model;

import java.util.Set;

/**
 * Created by bahodurova on 1/7/2018.
 */
public interface Group<T> {
    Set<T> getMembers();
    String getName();
    void addMember(T member);
}
