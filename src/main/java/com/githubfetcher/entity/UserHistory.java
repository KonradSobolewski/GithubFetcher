package com.githubfetcher.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Map;

@Entity
@Table(name = "user_history")
@Data
@NoArgsConstructor
public class UserHistory{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long user_id;

    private Long change_by;

    private OffsetDateTime date;

    @Column(name = "after")
    @Type(type = "JsonbType")
    private Map<String, Object> after;


    @Builder
    public UserHistory(Long user_id, Long change_by, OffsetDateTime date, Map<String, Object> after) {
        this.user_id = user_id;
        this.change_by = change_by;
        this.date = date;
        this.after = after;
    }
}
