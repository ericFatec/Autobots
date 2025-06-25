package com.autobots.app.entidades.snapshots;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TelefoneSnapshot {
    private String ddd;
    private String numero;
}
