package app.pinjamruang.reservation.model;

import app.pinjamruang.room.model.Room;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "startDate")
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "endDate")
    private LocalDateTime endDate;

    @NotNull
    @Column(name = "attendees")
    private Integer attendees;

    @Column(name = "agenda")
    private String agenda;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Reservation() {}

    public Reservation(@NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate, @NotNull Integer attendees, String agenda, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendees = attendees;
        this.agenda = agenda;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Integer getAttendees() {
        return attendees;
    }

    public String getAgenda() {
        return agenda;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public void setAttendees(Integer attendees) {
        this.attendees = attendees;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
