package com.atatestcase.entity;

import com.atatestcase.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    private String answer; // "Evet, var" veya "Hayır, yok"

    @ElementCollection
    private List<String> photoUrls; // Fotoğraf URL'leri

    private String explanation; // Eğer "Evet, var" ise açıklama
}