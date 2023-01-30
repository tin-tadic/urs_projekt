package com.example.urs.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Data
@NoArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Instant validUntil;

    private String owner;

    private String participants;

    public Code(String code, Instant validUntil, String owner) {
        this.code = code;
        this.validUntil = validUntil;
        this.owner = owner;
    }

    public ArrayList<String> getListOfParticipants() {
        if (this.participants == null) {
            return new ArrayList<>();
        };
        return new ArrayList<>(Arrays.asList(this.participants.split("\\|")));
    }
}
