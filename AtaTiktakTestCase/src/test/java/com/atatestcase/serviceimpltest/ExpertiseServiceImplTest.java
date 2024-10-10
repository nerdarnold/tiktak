package com.atatestcase.serviceimpltest;

import com.atatestcase.business.dto.ExpertiseDto;
import com.atatestcase.business.dto.QuestionDto;
import com.atatestcase.business.serviceimpl.ExpertiseServiceImpl;
import com.atatestcase.entity.Expertise;
import com.atatestcase.entity.Question;
import com.atatestcase.repository.ExpertiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpertiseServiceImplTest {

    private ExpertiseServiceImpl expertiseService;
    private ExpertiseRepository expertiseRepository;

    @BeforeEach
    void setUp() {
        expertiseRepository = mock(ExpertiseRepository.class);
        expertiseService = new ExpertiseServiceImpl(expertiseRepository);
    }

    @Test
    void testGetExpertiseByCarId_WhenExpertiseExists() {
        String carId = "CAR123";
        Expertise expertise = new Expertise();
        expertise.setCarId(carId);

        Question question1 = new Question();
        question1.setQuestionText("Motor durumu nasıl?");
        question1.setAnswer("Evet, var");
        question1.setPhotoUrls(List.of("http://example.com/photo1.jpg"));
        question1.setExplanation("Motor kontrol edildi, sorun yok.");

        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        expertise.setQuestions(questions);

        Mockito.when(expertiseRepository.findFirstByCarIdOrderByIdDesc(carId)).thenReturn(Optional.of(expertise));

        ExpertiseDto result = expertiseService.getExpertiseByCarId(carId);

        assertNotNull(result);
        assertEquals(carId, result.getCarId());
        assertEquals(1, result.getQuestions().size());
        assertEquals("Evet, var", result.getQuestions().get(0).getAnswer());
        assertEquals("http://example.com/photo1.jpg", result.getQuestions().get(0).getPhotoUrls().get(0));
    }

    @Test
    void testGetExpertiseByCarId_WhenNoExpertiseExists() {
        String carId = "CAR123";
        Mockito.when(expertiseRepository.findFirstByCarIdOrderByIdDesc(carId)).thenReturn(Optional.empty());

        ExpertiseDto result = expertiseService.getExpertiseByCarId(carId);

        assertNull(result);
    }

    @Test
    void testCreateExpertise() {
        ExpertiseDto expertiseDto = new ExpertiseDto();
        expertiseDto.setCarId("CAR123");

        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionText("Fren durumu nasıl?");
        questionDto.setAnswer("Hayır, yok");

        expertiseDto.setQuestions(List.of(questionDto));

        Expertise expertise = new Expertise();
        expertise.setCarId(expertiseDto.getCarId());
        expertise.setQuestions(new ArrayList<>());

        Mockito.when(expertiseRepository.save(any(Expertise.class))).thenReturn(expertise);

        ExpertiseDto result = expertiseService.createExpertise(expertiseDto);

        assertNotNull(result);
        assertEquals("CAR123", result.getCarId());
        verify(expertiseRepository, times(1)).save(any(Expertise.class));
    }

}