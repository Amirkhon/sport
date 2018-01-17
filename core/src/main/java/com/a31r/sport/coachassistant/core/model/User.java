package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User extends AbstractEntity {

    public User() {}

    public User(String name, String familyName, String patronymic) {
        this.name = name;
        this.familyName = familyName;
        this.patronymic = patronymic;
    }

    @Column(name = "username", length = 32)
    private String username;

    @Column(name = "password", length = 16)
    private String password;

    @Column(name = "name", length = 32)
    private String name;

    @Column(name = "family_name", length = 32)
    private String familyName;

    @Column(name = "patronymic", length = 32)
    private String patronymic;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender")
    private Boolean gender;

    @Lob
    @Column(name = "photo", columnDefinition="mediumblob")
    private byte[] photo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProperty> properties = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    private Set<UserGroup> groups = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public List<UserProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<UserProperty> properties) {
        this.properties = properties;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<UserGroup> groups) {
        this.groups = groups;
    }

    public void addProperty(UserProperty property) {
        this.properties.add(property);
        if (property.getUser() != this) {
            property.setUser(this);
        }
    }

    public void removeProperty(UserProperty property) {
        this.properties.remove(property);
    }

    public void addGroup(UserGroup group) {
        this.groups.add(group);
        if (!group.getMembers().contains(this)) {
            group.getMembers().add(this);
        }
    }

    public void removeGroup(UserGroup group) {
        this.groups.remove(group);
        if (group.getMembers().contains(this)) {
            group.getMembers().remove(this);
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", familyName, name, patronymic, " (" +  getBirthdayString() + ")");
    }

    private String getBirthdayString() {
        if (birthday != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return dateTimeFormatter.format(birthday);
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (familyName != null ? !familyName.equals(user.familyName) : user.familyName != null) return false;
        if (patronymic != null ? !patronymic.equals(user.patronymic) : user.patronymic != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        return Arrays.equals(photo, user.photo);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}