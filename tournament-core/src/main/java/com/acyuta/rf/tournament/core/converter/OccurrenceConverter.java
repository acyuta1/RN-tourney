package com.acyuta.rf.tournament.core.converter;

import com.acyuta.rf.tournament.core.model.Occurrence;
import com.acyuta.rf.tournament.core.service.OccurrenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OccurrenceConverter implements Converter<String, Occurrence> {

    private final OccurrenceService occurrenceService;

    @Override
    public Occurrence convert(String id) {
        return occurrenceService.findById(Long.parseLong(id));
    }
}
