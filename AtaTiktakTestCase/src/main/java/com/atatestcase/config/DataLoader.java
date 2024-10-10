package com.atatestcase.config;

import com.atatestcase.entity.Expertise;
import com.atatestcase.entity.Question;
import com.atatestcase.repository.ExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ExpertiseRepository expertiseRepository;

    @Autowired
    public DataLoader(ExpertiseRepository expertiseRepository) {
        this.expertiseRepository = expertiseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        // Veritabanına örnek verileri ekleme
        Expertise expertise1 = new Expertise();
        expertise1.setCarId("CAR123");
        List<Question> questions1 = new ArrayList<>();

        Question question1 = new Question();
        question1.setQuestionText("Motor durumu nasıl?");
        question1.setAnswer("Evet, var");
        question1.setPhotoUrls(List.of("http://example.com/photo1.jpg"));
        question1.setExplanation("Motor kontrol edildi, sorun yok.");
        questions1.add(question1);

        expertise1.setQuestions(questions1);

        Expertise expertise2 = new Expertise();
        expertise2.setCarId("CAR456");
        List<Question> questions2 = new ArrayList<>();

        Question question2 = new Question();
        question2.setQuestionText("Fren durumu nasıl?");
        question2.setAnswer("Hayır, yok");
        question2.setPhotoUrls(List.of("http://example.com/photo2.jpg"));
        question2.setExplanation("Fren sistemi kontrol edildi, sorun yok.");
        questions2.add(question2);

        expertise2.setQuestions(questions2);

        // Verileri veritabanına kaydet
        expertiseRepository.save(expertise1);
        expertiseRepository.save(expertise2);
    }
}
