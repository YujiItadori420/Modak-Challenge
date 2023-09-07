package com.modak.challenge.model;

import com.modak.challenge.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private String message;
    private LocalDateTime localDateTimeSended;
    @ManyToOne
    @JoinColumn(name = "email") // Nombre de la columna que hace referencia al usuario en la tabla de notificaciones
    private User receiver;
}
