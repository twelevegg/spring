package com.twelvegg.aicc.mydatabase.call.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "violence_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ViolenceEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_id")
    private Call call;

    private Integer violenceCount;
}
