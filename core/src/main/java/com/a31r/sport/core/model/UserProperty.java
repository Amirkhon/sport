package com.a31r.sport.core.model;

import javax.persistence.*;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Table(name = "user_properties")
public class UserProperty extends AbstractEntity {

    public UserProperty() {
    }

    public UserProperty(User user, UserPropertyType propertyType, String value) {
        this.user = user;
        this.propertyType = propertyType;
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "property_type_id", nullable = false)
    private UserPropertyType propertyType;

    @Column(name = "value", nullable = false)
    private String value;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserPropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(UserPropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserProperty that = (UserProperty) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (propertyType != null ? !propertyType.equals(that.propertyType) : that.propertyType != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (propertyType != null ? propertyType.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}