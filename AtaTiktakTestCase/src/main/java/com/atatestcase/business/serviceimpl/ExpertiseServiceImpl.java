package com.atatestcase.business.serviceimpl;

import com.atatestcase.business.dto.ExpertiseDto;
import com.atatestcase.business.dto.QuestionDto;
import com.atatestcase.business.service.ExpertiseService;
import com.atatestcase.entity.Expertise;
import com.atatestcase.entity.Question;
import com.atatestcase.repository.ExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpertiseServiceImpl implements ExpertiseService {

    private final ExpertiseRepository expertiseRepository;

    @Autowired
    public ExpertiseServiceImpl(ExpertiseRepository expertiseRepository) {
        this.expertiseRepository = expertiseRepository;
    }

    @Override
    public ExpertiseDto getExpertiseByCarId(String carId) {
        Optional<Expertise> optionalExpertise = expertiseRepository.findFirstByCarIdOrderByIdDesc(carId);

        if (optionalExpertise.isPresent()) {
            Expertise expertise = optionalExpertise.get();

            // "Evet, var" olan soruların fotoğraf URL'lerini ekle
            List<QuestionDto> questions = expertise.getQuestions().stream()
                    .map(this::mapQuestionToDto)
                    .collect(Collectors.toList());

            ExpertiseDto dto = new ExpertiseDto();
            dto.setCarId(expertise.getCarId());
            dto.setQuestions(questions);

            return dto;
        }

        return null;
    }

    @Override
    public ExpertiseDto createExpertise(ExpertiseDto expertiseDto) {
        Expertise expertise = mapToEntity(expertiseDto);

        if (expertise.getQuestions() == null) {
            expertise.setQuestions(new ArrayList<>());
        }

        Expertise savedExpertise = expertiseRepository.save(expertise);
        return mapToDto(savedExpertise);
    }

    private ExpertiseDto mapToDto(Expertise expertise) {
        ExpertiseDto dto = new ExpertiseDto();
        dto.setCarId(expertise.getCarId());

        List<QuestionDto> questionDtos = (expertise.getQuestions() != null)
                ? expertise.getQuestions().stream().map(this::mapQuestionToDto).collect(Collectors.toList())
                : new ArrayList<>();

        dto.setQuestions(questionDtos);
        return dto;
    }

    private QuestionDto mapQuestionToDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setQuestionText(question.getQuestionText());
        dto.setAnswer(question.getAnswer());

        // Eğer "Evet, var" ise fotoğrafları ekle
        if ("Evet, var".equalsIgnoreCase(question.getAnswer())) {
            dto.setPhotoUrls(question.getPhotoUrls());
            dto.setExplanation(question.getExplanation());
        }

        return dto;
    }

    private Expertise mapToEntity(ExpertiseDto expertiseDto) {
        Expertise expertise = new Expertise();
        expertise.setCarId(expertiseDto.getCarId());

        expertise.setQuestions(expertiseDto.getQuestions().stream()
                .map(this::mapDtoToQuestion)
                .collect(Collectors.toList()));

        return expertise;
    }

    private Question mapDtoToQuestion(QuestionDto questionDto) {
        Question question = new Question();
        question.setQuestionText(questionDto.getQuestionText());
        question.setAnswer(questionDto.getAnswer());

        // Eğer "Evet, var" ise fotoğrafları ekle
        if ("Evet, var".equalsIgnoreCase(questionDto.getAnswer())) {
            question.setPhotoUrls(questionDto.getPhotoUrls());
            question.setExplanation(questionDto.getExplanation());
        }

        return question;
    }
}