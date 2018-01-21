package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user_groups")
public class UserGroup extends AbstractEntity implements Group<User> {

    public UserGroup() {
    }

    public UserGroup(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "user_group_user",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> members = new HashSet<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserGroup userGroup = (UserGroup) o;

        return name != null ? name.equals(userGroup.name) : userGroup.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}