package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bahodurova on 1/9/2018.
 */
@Entity
@Table(name = "schedules")
public class Schedule extends AbstractEntity {

    public Schedule() {
    }

    public Schedule(Boolean sunday, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Set<LocalDate> weekends) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekends = weekends;
    }

    @Column(name = "sunday")
    private Boolean sunday;

    @Column(name = "monday")
    private Boolean monday;

    @Column(name = "tuesday")
    private Boolean tuesday;

    @Column(name = "wednesday")
    private Boolean wednesday;

    @Column(name = "thursday")
    private Boolean thursday;

    @Column(name = "friday")
    private Boolean friday;

    @Column(name = "saturday")
    private Boolean saturday;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name = "endTime")
    private LocalTime endTime;

    @ElementCollection
    @CollectionTable(name="weekends", joinColumns=@JoinColumn(name="schedule_id"))
    @Column(name="weekends")
    private Set<LocalDate> weekends = new HashSet<>();

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Set<LocalDate> getWeekends() {
        return weekends;
    }

    public void setWeekends(Set<LocalDate> weekends) {
        this.weekends = weekends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Schedule schedule = (Schedule) o;

        if (sunday != null ? !sunday.equals(schedule.sunday) : schedule.sunday != null) return false;
        if (monday != null ? !monday.equals(schedule.monday) : schedule.monday != null) return false;
        if (tuesday != null ? !tuesday.equals(schedule.tuesday) : schedule.tuesday != null) return false;
        if (wednesday != null ? !wednesday.equals(schedule.wednesday) : schedule.wednesday != null) return false;
        if (thursday != null ? !thursday.equals(schedule.thursday) : schedule.thursday != null) return false;
        if (friday != null ? !friday.equals(schedule.friday) : schedule.friday != null) return false;
        if (saturday != null ? !saturday.equals(schedule.saturday) : schedule.saturday != null) return false;
        if (startDate != null ? !startDate.equals(schedule.startDate) : schedule.startDate != null) return false;
        if (endDate != null ? !endDate.equals(schedule.endDate) : schedule.endDate != null) return false;
        if (startTime != null ? !startTime.equals(schedule.startTime) : schedule.startTime != null) return false;
        if (endTime != null ? !endTime.equals(schedule.endTime) : schedule.endTime != null) return false;
        return weekends != null ? weekends.equals(schedule.weekends) : schedule.weekends == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sunday != null ? sunday.hashCode() : 0);
        result = 31 * result + (monday != null ? monday.hashCode() : 0);
        result = 31 * result + (tuesday != null ? tuesday.hashCode() : 0);
        result = 31 * result + (wednesday != null ? wednesday.hashCode() : 0);
        result = 31 * result + (thursday != null ? thursday.hashCode() : 0);
        result = 31 * result + (friday != null ? friday.hashCode() : 0);
        result = 31 * result + (saturday != null ? saturday.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (weekends != null ? weekends.hashCode() : 0);
        return result;
    }
}