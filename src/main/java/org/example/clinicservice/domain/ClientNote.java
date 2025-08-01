package org.example.clinicservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "client_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientNote {

    @Id
    @Column(name = "guid", nullable = false, unique = true)
    private String guid;  // external system uses UUID as String

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "client_guid", nullable = false)
    private String clientGuid;

    @Column(name = "logged_user")
    private String loggedUser;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "modified_date_time")
    private LocalDateTime modifiedDateTime;
}
